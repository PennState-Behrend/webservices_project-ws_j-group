var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
    res.render('signup', {});
});

router.post('/', function(req, res, next) {
   res.render('signup', {userName: '', email: '', passWord: ''});
});

module.exports = router;