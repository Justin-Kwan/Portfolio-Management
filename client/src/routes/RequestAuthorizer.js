'use strict';

const jwt = require('jsonwebtoken');


class RequestAuthorizer {

  constructor() {
    this.REQUEST_AUTHORIZED = true;
    this.REQUEST_UNAUTHORIZED = false;
  }

  handleRequestAuthorization(request) {
    const securityToken = this.getSecurityTokenFromCookie(request);

    if(securityToken == this.REQUEST_UNAUTHORIZED) {
      return this.REQUEST_UNAUTHORIZED;
    }

    const isRequestAuthorized = this.aauthorizeSecurityToken(securityToken);

    if(isRequestAuthorized == this.REQUEST_UNAUTHORIZED) {
      return this.REQUEST_UNAUTHORIZED;
    }

    return this.REQUEST_AUTHORIZED;

  }

  getSecurityTokenFromCookie() {

    try {
      const securityToken = request.cookies['security_token'];
      return securityToken;
    }
    catch(error) {
      return this.REQUEST_UNAUTHORIZED;
    }

  }

  authorizeSecurityToken() {

    try {
      var decodedSecurityToken = jwt.verify(securityToken, 'wrong-secret');
      return this.REQUEST_AUTHORIZED;
    }
    catch(error) {
      console.log("TOKEN ERROR" + error);
      return this.REQUEST_UNAUTHORIZED;
    }

  }

}

module.exports = RequestAuthorizer;
