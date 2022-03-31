var express = require('express');
var router = express.Router();
var soap = require('soap');
var argon2 = require('argon2');
var LocalStrategy = require('passport-local').Strategy;
var passport = require('passport');
const fs = require("fs");
const path = require("path");
const url = 'http://localhost:8080/userservice?wsdl'

passport.use('signup', new LocalStrategy({passReqToCallback: true}, function(req, username, password, done) {
    if(req.body.username === '')
        return done(null, false, {message: 'Username Field is Empty!'});
    if(req.body.email === '')
        return done(null, false, {message: 'Email Field is Empty!'});
    if(req.body.password === '')
        return done(null, false, {message: 'Password Field is Empty!'});
    if(req.body.password !== req.body.password2)
        return done(null, false, {message: 'Passwords do not Match!'});

    let salt = Math.floor(Math.random() * 99999999);
    try {
        argon2.hash(req.body.password + salt).then(result => {
            let pass = {passHash: result, saltHash: salt};
            let args = {username: req.body.username, email: req.body.email, password: pass};
            soap.createClient(url, function(err, client) {
                client.addUser(args, function(err, result) {
                    let val = result.return;
                    let json = JSON.stringify({username: req.body.username});
                    console.log("Before");
                    fs.mkdir(path.join(__dirname, '../public/users/' + val + '/'), function(err, result) {
                        fs.writeFile(path.join(__dirname, '../public/users/' + val + '/userSettings.json'), json, 'utf8', function(err, result) {
                            console.log("After");
                            fs.mkdir(path.join(__dirname, '../public/users/' + val + '/projects/'), function(err, result) {
                                if(val == -1)
                                    return done(null, false, {message: 'Username Already Exists!'});
                                if(val == -2)
                                    return done(null, false, {message: 'Email Already Exists!'});
                                return done(null, val);
                            });
                        });
                    })
                })
            })
        });
    } catch (e) {
        console.log(e);
    }
}));

/* GET home page. */
router.get('/', function(req, res, next) {
    if(req.user !== undefined) {
        console.log(req.user);
        res.redirect('/projects');
    }
    else
        res.render('signup', {error: req.flash().error});
});

router.post('/', passport.authenticate('signup', {
    successRedirect: '/projects',
    failureRedirect: '/signup',
    session: true,
    failureFlash: true
}));

module.exports = router;