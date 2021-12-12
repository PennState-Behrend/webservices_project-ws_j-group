var express = require('express');
const soap = require("soap");
const fs = require("fs");
const path = require("path");
const argon2 = require("argon2");
const url = 'http://localhost:8080/userservice?wsdl'
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    if(req.user === undefined)
        res.redirect('/signin');
    else {
        let args = {id: req.user};
        soap.createClient(url, function(err, client) {
            client.getUserName(args, function(err, result) {
                let picture = '/images/logo.png';
                let json = JSON.parse(fs.readFileSync(path.join(__dirname, '../public/users/' + req.user + '/userSettings.json')).toString());
                if(fs.existsSync(path.join(__dirname, '../public/users/' + req.user + '/profilePicture.jpg')))
                    picture = '/users/' + req.user + '/profilePicture.jpg';
                else if(fs.existsSync(path.join(__dirname, '../public/users/' + req.user + '/profilePicture.png')))
                    picture = '/users/' + req.user + '/profilePicture.png';
                let val = result.return;
                if(val === '-1') // Should never happen
                    res.redirect('/logout');
                else
                    res.render('settings', { pageTitle: 'settings', username: val, userImage: picture, settings: json, error: req.flash('error')});
            })
        })
    }
});

router.post('/password', function(req, res, next) {
    if(req.body.passwordNew !== req.body.passwordNew2) {
        req.flash('error', 'New Passwords do not Match!');
        res.redirect('/settings');
        return;
    }
    let args = {id: req.user};
    soap.createClient(url, function(err, client) {
        client.getUserByID(args, function (err, result) {
            let val = result.return;
            argon2.verify(val.passHash, req.body.password + val.saltHash).then(result => {
                if(!result) {
                    req.flash('error', 'Incorrect Old Password Entered!');
                    res.redirect('/settings');
                    return;
                }
                let salt = Math.floor(Math.random() * 99999999);
                try {
                    argon2.hash(req.body.passwordNew + salt).then(result => {
                        let pass = {passHash: result, saltHash: salt};
                        let passArgs = {id: req.user, password: pass};
                        soap.createClient(url, function(err, client) {
                            client.updatePassword(passArgs, function(err, result) {
                                res.redirect('/settings');
                            })
                        })
                    })
                } catch(ex) {
                    // IGNORE
                }
            })
        });
    });
})

router.post('/', function(req, res, next) {
    let json = JSON.stringify(req.body);
    let args = {id: req.user, username: req.body.username};
    soap.createClient(url, function(err, client) {
        client.updateUsername(args, function(err, result) {
            if(!result.return) {
                req.flash('error', 'Username Already Exists!');
                res.redirect('/settings');
                return;
            }
            fs.writeFile(path.join(__dirname, '../public/users/' + req.user + '/userSettings.json'), json, 'utf8', function(err, data) {
                res.redirect('/settings');
            });
        })
    })
})

module.exports = router;