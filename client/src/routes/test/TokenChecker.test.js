const expect = require('chai').expect;
const assert = require('assert');
const TokenChecker = require('../src/TokenChecker.js');

const tokenChecker = new TokenChecker();

describe('Auth Token Authorization Function Tests', function() {

  it('should return true when good token is passed in', function() {
    let result = tokenChecker.checkAuthToken('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs');
    expect(result).to.be.true;
  });

  it('should return true when good token is passed in', function() {
    let result = tokenChecker.checkAuthToken('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU');
    expect(result).to.be.true;
  });

  it('should return false when bad token is passed in', function() {
    let result = tokenChecker.checkAuthToken('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imp1c3Rpbmt3YW4xMjMiLCJ1c2VyIGlkIjoiNzFkODczN2YtZGMwNy00NzkxLWJlNTktMmM1NDAxNDhkMmFkIn0.ym0qRcy0yyu30xB8a_9MydVe4DIn_x0nlbepMiOId-E');
    expect(result).to.be.false;
  });

  it('should return false when bad token is passed in', function() {
    let result = tokenChecker.checkAuthToken('');
    expect(result).to.be.false;
  });

  it('should return false when bad token is passed in', function() {
    let result = tokenChecker.checkAuthToken(' ');
    expect(result).to.be.false;
  });

  it('should return false when bad token is passed in', function() {
    let result = tokenChecker.checkAuthToken('wd');
    expect(result).to.be.false;
  });

  it('should return false when bad token is passed in', function() {
    let result = tokenChecker.checkAuthToken(null);
    expect(result).to.be.false;
  });

  it('should return false when bad token is passed in', function() {
    let result = tokenChecker.checkAuthToken(undefined);
    expect(result).to.be.false;
  });

  it('should return false when bad token is passed in', function() {
    let result = tokenChecker.checkAuthToken(123);
    expect(result).to.be.false;
  });

});
