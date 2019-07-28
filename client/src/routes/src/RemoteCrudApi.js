'use strict';

const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

class RemoteCrudApi {

  fetchUserStatus(userStatusEndpoint) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', userStatusEndpoint, false); // false for synchronous request
    httpRequest.send(null);
    const doesUserExist = httpRequest.responseText;
    return doesUserExist;
  }

  generateUserStatusEndpoint(userId) {
    const userStatusEndpoint = 'http://127.0.0.1:8001/checkUserExists/?userId=' + encodeURIComponent(userId);
    return userStatusEndpoint;
  }

}

module.exports = RemoteCrudApi;
