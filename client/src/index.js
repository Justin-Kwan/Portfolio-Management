'use strict';

const RemoteTokenApi = require('./RemoteTokenApi.js');
const express = require('express');
const cors = require('cors')
const path = require('path');
const cookieParser = require('cookie-parser');

const LOCAL_HOST = '127.0.0.1';
const PORT = 8000;

const app = express();
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

const remoteTokenApi = new RemoteTokenApi();

async function onGetPortfolio(req, res) {
  const authToken = req.cookies['crypto_cost_session'];

  if(authToken == undefined)
    res.redirect("http://127.0.0.1:5001/login");
  else {
    const isRequestAuthorized = await remoteTokenApi.fetchAuthCheck(authToken);
    if (isRequestAuthorized === false)
      res.redirect("http://127.0.0.1:5001/login");
    else
      res.sendFile(getPortfolioPagePath);
  }
}

async function onUpdatePortfolio(req, res) {
  const authToken = req.cookies['crypto_cost_session'];

  if(authToken == undefined)
    res.redirect("http://127.0.0.1:5001/login");
  else {
    const isRequestAuthorized = await remoteTokenApi.fetchAuthCheck(authToken);
    if (isRequestAuthorized === false)
      res.redirect("http://127.0.0.1:5001/login");
    else
      res.sendFile(updatePortfolioPagePath);
  }
}

app.get('/getPortfolio', onGetPortfolio);
app.get('/updatePortfolio', onUpdatePortfolio);

app.listen(PORT, function() {
  console.log('Portfolio management frontend server started at ' + LOCAL_HOST + ':' + PORT + '...');
});
