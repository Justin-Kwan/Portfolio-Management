const expect = require('chai').expect;
const assert = require('assert');
const RemoteCrudApi = require('../src/RemoteCrudApi.js');

const remoteCrudApi = new RemoteCrudApi();

describe('Generate User Status Endpoint Function Tests', function() {

  it('should return formatted endpoint url', function() {
    let result = remoteCrudApi.generateUserStatusEndpoint('12345*');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=12345*');
  });

  it('should return formatted endpoint url', function() {
    let result = remoteCrudApi.generateUserStatusEndpoint('U21-342');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=U21-342');
  });

  it('should return formatted endpoint url', function() {
    let result = remoteCrudApi.generateUserStatusEndpoint(' ');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=%20'); // space is encoded as %20
  });

  it('should return formatted endpoint url', function() {
    let result = remoteCrudApi.generateUserStatusEndpoint('%%');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=%25%25'); // % is encoded as %25
  });

  it('should return formatted endpoint url', function() {
    let result = remoteCrudApi.generateUserStatusEndpoint('');
    expect(result).to.equal('http://127.0.0.1:8001/checkUserExists/?userId=');
  });

});
