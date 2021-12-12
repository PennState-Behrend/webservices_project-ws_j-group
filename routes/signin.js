var express = require('express');
var soap = require('soap')
var argon2 = require('argon2');
var LocalStrategy = require('passport-local').Strategy;
var passport = require('passport');
const url = 'http://localhost:8080/userservice?wsdl'
var router = express.Router();

passport.use('signin', new LocalStrategy({passReqToCallback: true, usernameField: 'email'}, function(req, username, password, done) {
    if(req.body.email === '')
        return done(null, false, {message: 'Email Field is Empty!'});
    if(req.body.password === '')
        return done(null, false, {message: 'Password Field is Empty!'});

    let args = {email: req.body.email};
    soap.createClient(url, function(err, client) {
        client.getUserID(args, function(err, result) {
            let val = result.return;
            if(val.id == -1)
                return done(null, false, {message: 'Account with Email Does Not Exist!'});

            argon2.verify(val.passHash, req.body.password + val.saltHash).then(result => {
                if(!result)
                    return done(null, false, {message: 'Incorrect Password Entered!'});
                return done(null, val.id);
            })
        })
    })
}));

/* GET home page. */
router.get('/', function(req, res, next) {
    if(req.user !== undefined) {
        console.log(req.user);
        res.redirect('/projects');
    }
    else
        res.render('signin', {error: req.flash().error});
});

router.post('/', passport.authenticate('signin', {
    successRedirect: '/projects',
    failureRedirect: '/signin',
    session: true,
    failureFlash: true
}));

module.exports = router;