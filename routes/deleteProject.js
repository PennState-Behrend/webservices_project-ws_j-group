var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('deleteProject', {pageTitle: 'Delete Project', username: 'Jake', userImage: "/images/logo2.svg" });
});

router.post('/', function(req, res, next) {

});

module.exports = router;