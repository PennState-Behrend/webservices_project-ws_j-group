var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    req.logout();
    res.redirect('/signin');
});

module.exports = router;