
'use strict';

const RemoteCrudApi      = require('./RemoteCrudApi.js');
const express            = require('express');
const app                = express();
const cors               = require('cors')
const path               = require('path');
const cookieParser       = require('cookie-parser');

const LOCAL_HOST         = '127.0.0.1';
const PORT               = 8000;

app.use(cors());

// static page paths
const updatePortfolioPagePath = path.join(__dirname, './pages/update-portfolio/update-portfolio.html');
const getPortfolioPagePath = path.join(__dirname, './pages/get-portfolio/get-portfolio.html');

// folder paths
const updatePortfolioFolderPath = express.static(path.join(__dirname, './pages/update-portfolio'));
const getPortfolioFolderPath = express.static(path.join(__dirname, './pages/get-portfolio'));
const fontsFolderPath = express.static(path.join(__dirname, './assets/pulp-display-font'));
const imagesFolderPath = express.static(path.join(__dirname, './assets/images'));

app.use(cookieParser());
app.use(updatePortfolioFolderPath);
app.use(getPortfolioFolderPath);
app.use(fontsFolderPath);
app.use(imagesFolderPath);

const remoteCrudApi = new RemoteCrudApi();

function onGetPortfolio(req, res) {

  const authToken = req.cookies['auth_token'];
  // const isRequestAuthorized = tokenChecker.checkAuthToken(authToken);

  // if(isRequestAuthorized == false)
  //   res.send("403 Unauthorized Access");
  //
  // const doesUserExist = handleUserStatusCheck(authToken);
  //
  // if(doesUserExist == 'false')
  //   res.redirect('http://127.0.0.1:8000/createPortfolio');
  // else if(doesUserExist == 'true')
    res.sendFile(getPortfolioPagePath);
  // else
  //   res.send("500 Internal Error, Oops (¯―¯٥)");
}

function onUpdatePortfolio(req, res) {
  const authToken = req.cookies['auth_token'];

  // Todo: call shared_services or a token checking lib
  // const isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
  //
  // if(isRequestAuthorized == false)
  //   res.send("403 Unauthorized Access");

  res.sendFile(updatePortfolioPagePath);
}

function handleUserStatusCheck(authToken) {
  //const userId = tokenChecker.getAuthTokenUserId(authToken);
  const userStatusUrl = remoteCrudApi.generateUserStatusUrl(userId);
  const doesUserExist = remoteCrudApi.fetchUserStatus(userStatusUrl);
  return doesUserExist;
}

// routes
app.get('/getPortfolio', onGetPortfolio);
app.get('/updatePortfolio', onUpdatePortfolio);

app.listen(PORT, function() {
  console.log('Frontend server started at ' + LOCAL_HOST + ':' + PORT + '...');
});
