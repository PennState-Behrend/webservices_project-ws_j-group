var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var soap = require('soap');
var passport = require('passport');

const url = 'http://localhost:8080/userservice?wsdl';
const pas = {passHash: 'sadfjkhsafdjh', saltHash: 23482734};
const args = {username: 'User', email: 'User@gmail.com', password: pas};

var indexRouter = require('./routes');
var usersRouter = require('./routes/users');

var app = express();

app.use(express.static('static'));

app.use(passport.initialize());

app.get('/signin', (req, res) => { // sending the signin html
  res.sendFile('/static/html/signin.html', {root: __dirname});
});

app.get('/signup', (req, res) => { // sending the signup html
  res.sendFile('/static/html/signup.html', {root: __dirname});
});

app.get('/index', (req, res) => { // sending the index html
  res.sendFile('/static/html/index.html', {root: __dirname});
});

app.get('/search', (req, res) => { // sending the search html
  res.sendFile('/static/html/search.html', {root: __dirname});
});

app.get('/settings', (req, res) => { // sending the settings html
  res.sendFile('/static/html/settings.html', {root: __dirname});
});

passport.use('local', new localStrat(function (username, password, done) {
  var checkUser = username;
  var info = userList.find((element) => {
    if (element.username == checkUser) {
      return element;
    }
  });

  if (info == undefined || info == null) {
    return done(null, false, {message : 'Incorrect Username'});
  }
  if (info.password != password) {
    return done(null, false, {message : 'Incorrect Password'});
  }
  else {
    return done(null, info);
  }
}));

app.post('/signup', (req, res) => {
  res.sendFile(soap.createClient(url, function(err, client) {
    client.addUser(args, function(err, result) {
      console.log(result);
    })
  }))
});

//app.post('/signin', );

app.listen(3001, () => {
  console.log(`Example app listening at http://localhost:3001`)
});


// view engine setup
app.set('view', path.join(__dirname, 'view'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
