'use strict';

const jwt = require('jsonwebtoken');


class TokenChecker {

  constructor() {
    this.REQUEST_AUTHORIZED = true;
    this.REQUEST_UNAUTHORIZED = false;
  }

  checkAuthToken(authToken) {
    try {
      const decodedAuthToken = jwt.verify(authToken, 'fake_secret_key');
    }catch(error) {
      return this.REQUEST_UNAUTHORIZED;
    }
    return this.REQUEST_AUTHORIZED;
  }

}

module.exports = TokenChecker;
