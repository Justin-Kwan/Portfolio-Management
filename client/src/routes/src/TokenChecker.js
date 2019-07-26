'use strict';

const jwt = require('jsonwebtoken');


class TokenChecker {

  constructor() {
    this.REQUEST_AUTHORIZED = true;
    this.REQUEST_UNAUTHORIZED = false;
  }

  checkSecurityToken(securityToken) {
    try {
      const decodedSecurityToken = jwt.verify(securityToken, 'fake_secret_key');
      return this.REQUEST_AUTHORIZED;
    }
    catch(error) {
      return this.REQUEST_UNAUTHORIZED;
    }
  }

}

module.exports = TokenChecker;
