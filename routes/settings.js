var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('settings', { username: 'Jake', userImage: "/images/logo2.svg" });
});

module.exports = router;