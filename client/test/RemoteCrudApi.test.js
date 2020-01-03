const expect = require('chai').expect;
const assert = require('assert');
const RemoteCrudApi = require('../src/RemoteCrudApi.js');

const remoteCrudApi = new RemoteCrudApi();

describe('Generate User Status Url Function Tests', function() {

  it('should return formatted user status url', function() {
    let result = remoteCrudApi.generateUserStatusUrl('12345*');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=12345*');
  });

  it('should return formatted user status url', function() {
    let result = remoteCrudApi.generateUserStatusUrl('U21-342');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=U21-342');
  });

  it('should return formatted user status url', function() {
    let result = remoteCrudApi.generateUserStatusUrl(' ');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=%20'); // space is encoded as %20
  });

  it('should return formatted user status url', function() {
    let result = remoteCrudApi.generateUserStatusUrl('%%');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=%25%25'); // % is encoded as %25
  });

  it('should return formatted user status url', function() {
    let result = remoteCrudApi.generateUserStatusUrl('');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=');
  });

});
