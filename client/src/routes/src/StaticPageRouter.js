/**
 *  router for serving static html pages for the user portfolio
 */

'use strict';

const RequestAuthorizer     = require('./TokenChecker.js');
const express               = require('express');
const app                   = express();
const path                  = require('path');
const cookieParser          = require('cookie-parser');

const LOCAL_HOST_IP_ADDRESS = '127.0.0.1';
const PORT_NUMBER           = 8000;

// static page paths
let createPortfolioPagePath = path.join(__dirname, '../../pages/CreatePortfolio.html');
let getPortfolioPagePath = path.join(__dirname, '../../pages/GetPortfolio.html');

// folder paths
let clientFolderPath = express.static(path.join(__dirname, '../../'));
let assetsFolderPath = express.static(path.join(__dirname, '../../assets'));

app.use(cookieParser());
app.use(clientFolderPath);
app.use(assetsFolderPath);

const requestAuthorizer = new RequestAuthorizer();

/**
 *  Should have another route for /getPortfolio and calls UserPortfolioManagement
 *  to see if user exists or is new, if not redirect to /create portfolio, otherwise
 *  display portfolio
 *
 *  get bounce you to create if you're new and create bounce you to get if you exist
 */

 app.get('/getPortfolio', function(request, response) {
   // call server to see if user exists


 });

app.get('/createPortfolio', function(request, response) {
  const securityToken = request.cookies['security_token'];
  const isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);

  if(isRequestAuthorized) {
    response.sendFile(createPortfolioPagePath);
  }
  response.send("403 Unauthorized Access Denied");
});

app.listen(PORT_NUMBER, function() {
  console.log('Frontend server started at ' + LOCAL_HOST_IP_ADDRESS + ':' + PORT_NUMBER + '...');
});
