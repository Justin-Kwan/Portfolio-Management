/**
 *  router for serving static html pages for the user portfolio
 */

'use strict';

const TokenChecker       = require('./TokenChecker.js');
const RemoteCrudApi      = require('./RemoteCrudApi.js');
const express            = require('express');
const app                = express();
const path               = require('path');
const cookieParser       = require('cookie-parser');

const LOCAL_HOST         = '127.0.0.1';
const PORT               = 8000;

// static page paths
const createPortfolioPagePath = path.join(__dirname, '../../pages/CreatePortfolio.html');
const getPortfolioPagePath = path.join(__dirname, '../../pages/GetPortfolio.html');

// folder paths
const clientFolderPath = express.static(path.join(__dirname, '../../'));
const assetsFolderPath = express.static(path.join(__dirname, '../../assets'));

app.use(cookieParser());
app.use(clientFolderPath);
app.use(assetsFolderPath);

const tokenChecker  = new TokenChecker();
const remoteCrudApi = new RemoteCrudApi();

function onGetPortfolio(request, response) {
  const authToken = request.cookies['auth_token'];
  const isRequestAuthorized = tokenChecker.checkAuthToken(authToken);

  if(isRequestAuthorized == false)
    response.send("403 Unauthorized Access");

  const doesUserExist = handleUserStatusCheck(authToken);

  if(doesUserExist == 'false')
    response.redirect('http://127.0.0.1:8000/createPortfolio');
  else if(doesUserExist == 'true')
    response.sendFile(getPortfolioPagePath);
  else
    response.send("500 Internal Error, Oops (¯―¯٥)");
}

function onCreatePortfolio(request, response) {
  const authToken = request.cookies['auth_token'];
  const isRequestAuthorized = tokenChecker.checkAuthToken(authToken);

  if(isRequestAuthorized == false)
    response.send("403 Unauthorized Access");

  response.sendFile(createPortfolioPagePath);
}

function handleUserStatusCheck(authToken) {
  const userId = tokenChecker.getAuthTokenUserId(authToken);
  const userStatusUrl = remoteCrudApi.generateUserStatusUrl(userId);
  const doesUserExist = remoteCrudApi.fetchUserStatus(userStatusUrl);
  return doesUserExist;
}

// routes
app.get('/getPortfolio', onGetPortfolio);
app.get('/createPortfolio', onCreatePortfolio);

app.listen(PORT, function() {
  console.log('Frontend server started at ' + LOCAL_HOST + ':' + PORT + '...');
});
