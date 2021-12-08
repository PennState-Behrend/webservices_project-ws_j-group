var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    project1 = {title: 'My First Project', shortDescription: 'quick', lastUpdated: 'never'}
    project2 = {title: 'My Second Project', shortDescription: 'quick', lastUpdated: 'never'}
    res.render('projects', { pageTitle: 'projects', username: 'Jake', userImage: "/images/logo.png", projects: [project1, project2]});
});

module.exports = router;
