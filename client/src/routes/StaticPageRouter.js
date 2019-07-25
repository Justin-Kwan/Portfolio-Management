/**
 *  router for serving static html pages for the user portfolio
 */

'use strict';

const RequestAuthorizer = require('./RequestAuthorizer.js');
const express           = require('express');
const app               = express();
const path              = require('path');
var cookieParser = require('cookie-parser');

const LOCAL_HOST_IP_ADDRESS = '127.0.0.1';
const PORT_NUMBER = 8000;

const requestAuthorizer = new RequestAuthorizer();

let createPortfolioPagePath = path.join(__dirname, '../pages/CreatePortfolio.html');
let clientFolderPath = express.static(path.join(__dirname, '../client'));
let assetsFolderPath = express.static(path.join(__dirname, '../assets'));

app.use(cookieParser());
app.use(clientFolderPath);
app.use(assetsFolderPath);

app.get('/createPortfolio', function(request, response) {

  console.log("REQUEST RECIEVED");

  console.log("COOKIE: " + request.cookies['security_token']);

  const isRequestAuthorized = requestAuthorizer.handleRequestAuthorization(request);
  if(isRequestAuthorized == false) {
    return "401 Unauthorized Access Denied";
  }
  else {
    res.sendFile(createPortfolioPagePath);
  }
});

app.listen(PORT_NUMBER, function() {
  console.log('Frontend server started at ' + LOCAL_HOST_IP_ADDRESS + ':' + PORT_NUMBER + '...');
});
