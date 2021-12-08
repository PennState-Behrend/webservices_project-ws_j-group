var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('search', { username: 'Jake', userImage: "/images/logo.png" });
});

module.exports = router;