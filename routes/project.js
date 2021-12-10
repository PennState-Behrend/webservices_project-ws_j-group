var express = require('express');
var path = require('path')
var fs = require('fs')
var router = express.Router();

function renderJavascriptFile(file, res) {
  let content = fs.readFileSync(file);
  let lines = content.toString().split(String.fromCharCode(10));
  res.render('javascriptFile', {text: lines});
}

function renderTextFile(file, res) {
  let content = fs.readFileSync(file);
  res.render('textFile', {text: content.toString().split(String.fromCharCode(10))});
}

function renderFolder(file, url, res) {
  let files = fs.readdirSync(file);
  if(url.charAt(url.length - 1) == '/')
    url = url.substring(0, url.length - 1)
  res.render('index', { title: 'Express', folder: url, fileSystem: files});
}

const fileTypeList = {
  'txt': renderTextFile,
  '': renderFolder,
  'js': renderJavascriptFile
};

function FileDispatchFunction(fileType) {
  return fileTypeList[fileType];
}


router.use('/projects/:userID/:projectID/:file(*)', function(req, res, next) { // Files / Folders Inside Project
  let userID = req.params.userID;
  let projectID = req.params.projectID;
  let file = req.params.file;
  let fileParams = file.toString().split('.');
  if(fileParams.length === 2) { // File
    FileDispatchFunction(fileParams[1])(path.join(__dirname, '../public/users/' + userID + '/projects/' + projectID + '/' + file), res);
  } else if(fileParams.length === 1) { // Folder
    FileDispatchFunction('')(path.join(__dirname, '../public/users/' + userID + '/projects/' + projectID + '/' + file), req.originalUrl, res);
  }
});

module.exports = router;
