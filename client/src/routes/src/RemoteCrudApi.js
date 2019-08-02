'use strict';

const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

class RemoteCrudApi {

  constructor() {
    this.SYNC_REQUEST = false;
  }

  fetchUserStatus(userStatusUrl) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', userStatusUrl, this.SYNC_REQUEST); // false for synchronous request
    httpRequest.send(null);
    const doesUserExist = httpRequest.responseText;
    return doesUserExist;
  }

  generateUserStatusUrl(userId) {
    const userStatusUrl = 'http://127.0.0.1:8001/checkUserExists/?userId=' + encodeURIComponent(userId);
    return userStatusUrl;
  }

}

module.exports = RemoteCrudApi;
