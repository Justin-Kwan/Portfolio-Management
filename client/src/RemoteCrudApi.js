'use strict';

const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

class RemoteCrudApi {

  constructor() {
    this.SYNC_REQUEST = false;
  }

  /**
   *  calls api to check if user exists
   *  does not pass credentials (token in cookie)
   */
  fetchUserStatus(userStatusUrl) {
    const httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', userStatusUrl, this.SYNC_REQUEST);
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
