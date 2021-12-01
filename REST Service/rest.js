var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

var app = express();

class Book { // book class
    constructor(title, author)
    {
        this.title = title;
        this.author = author;
    }
}

let BList = [new Book("Green Eggs and Ham", "Dr. Seuss"), new Book("Happy Potter", "J.K. Rowling")]; // array of book objects

app.get('/list_book', (req, res) => { // get request for /list_book
    res.send(BList)
});

app.get('/index', (req, res) => { // get request for /index
    res.send("Welcome to Store")
});

app.post('/index', (req, res) => { // post request for /index
    res.send("POST Request Received")
});

app.delete('/index', (req, res) => { // delete request for /index
    res.send("DELETE Request Received")
});

app.listen(3001, () => {
    console.log(`Example app listening at http://localhost:3001`)
});


// view engine setup
app.set('views', path.join(__dirname, 'views'));
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
