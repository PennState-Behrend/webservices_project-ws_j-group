var express = require('express');
const soap = require("soap");
const fs = require("fs");
const path = require("path");
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
                let picture = '/images/logo2.svg';
                if(fs.existsSync(path.join(__dirname, '../public/users/' + req.user + '/profilePicture.jpg')))
                    picture = '/users/' + req.user + '/profilePicture.jpg';
                else if(fs.existsSync(path.join(__dirname, '../public/users/' + req.user + '/profilePicture.png')))
                    picture = '/users/' + req.user + '/profilePicture.png';
                let val = result.return;
                if(val === '-1') // Should never happen
                    res.redirect('/logout');
                else
                    res.render('search', { pageTitle: 'search', username: val, userImage: picture, projAmount: 5}); // 5 need to be changed to a variable
            })
        })
    }
});

router.post('/', function(req, res, next) {
    let args = {searchTerm: req.body.searchTerm};
    soap.createClient(url, function(err, client) {
        client.getSearchListOfUsers(args, function(err, result) {
            let newArray = [];
            if(result != null) {
                result.return.forEach(function(entry) {
                    let picture = '/images/logo2.svg';
                    if(fs.existsSync(path.join(__dirname, '../public/users/' + entry.id + '/profilePicture.jpg')))
                        picture = '/users/' + entry.id + '/profilePicture.jpg';
                    else if(fs.existsSync(path.join(__dirname, '../public/users/' + entry.id + '/profilePicture.png')))
                        picture = '/users/' + entry.id + '/profilePicture.png';
                    let files = fs.readdirSync(path.join(__dirname, '../public/users/' + entry.id + '/projects/'))
                    let newArgs = {image: picture, username: entry.username, projectAmt: files.length, projectsURL: '/projects/' + entry.id + '/'};
                    newArray.push(newArgs);
                })
            }
            res.json(newArray);
        });
    })
});

module.exports = router;