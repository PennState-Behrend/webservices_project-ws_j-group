var express = require('express');
var fs = require('fs');
const path = require("path");
const soap = require("soap");
const url = 'http://localhost:8080/userservice?wsdl'
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    if(req.user === undefined)
        res.redirect('/signin');
    else {
        res.redirect('/projects/' + req.user);
    }
});

router.get('/:userID', function(req, res, next) {
    if(req.user === undefined)
        res.redirect('/signin');
    else {
        let userID = req.params.userID;
        let projects = [];
        let files = fs.readdirSync(path.join(__dirname, '../public/users/' + userID + '/projects/'))
        files.forEach(file => {
            let json = JSON.parse(fs.readFileSync(path.join(__dirname, '../public/users/' + userID + '/projects/' + file + '/projectDesc.json')).toString());
            let project = {id: file, title: json.projectTitle, shortDescription: json.projectDescription, lastUpdated: json.dateUpdated, url: '/project/' + userID + '/' + file + '/'};
            projects.push(project);
        })
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
                    res.render('projects', { pageTitle: 'projects', username: val, userImage: picture, projects: projects});
            })
        })
    }
});

router.post('/', function(req, res, next) {
    res.render('projects', { pageTitle: 'projects', username: val, userImage: picture, projects: projects});
});

module.exports = router;
