import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import com.auth0.jwt.interfaces.DecodedJWT;
import UserPortfolioManagement.TokenChecker;


public class TokenCheckerTest {

  TokenChecker tokenChecker = new TokenChecker();
  String securityToken;

  @Test
  public void test_checkSecurityToken() {
    boolean isRequestAuthorized;

    // good token, username: randomuser123, id: ff4406fc-67b2-4f86-b2dd-4f9b35c64202
    securityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(true, isRequestAuthorized);

    // good token, useername: user109, id: e8e16b6f-cd81-4136-9d54-4c292469c5ee
    securityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU";
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(true, isRequestAuthorized);

    // bad token, wrong secret key
    securityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imp1c3Rpbmt3YW4xMjMiLCJ1c2VyIGlkIjoiNzFkODczN2YtZGMwNy00NzkxLWJlNTktMmM1NDAxNDhkMmFkIn0.ym0qRcy0yyu30xB8a_9MydVe4DIn_x0nlbepMiOId-E";
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    securityToken = "ey";
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    securityToken = " ";
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    securityToken = "^";
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    securityToken = null;
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    securityToken = "";
    isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);
    assertEquals(false, isRequestAuthorized);
  }

  @Test
  public void test_decodeSecurityToken() {
    DecodedJWT decodedSecurityToken;

    try {
      // good token, username: randomuser123, id: ff4406fc-67b2-4f86-b2dd-4f9b35c64202
      securityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";
      decodedSecurityToken = tokenChecker.decodeSecurityToken(securityToken);
    }catch(Exception error) {
      decodedSecurityToken = null;
    }
    assertNotNull(decodedSecurityToken);

    try {
      // good token, useername: user109, id: e8e16b6f-cd81-4136-9d54-4c292469c5ee
      securityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU";
      decodedSecurityToken = tokenChecker.decodeSecurityToken(securityToken);
    }catch(Exception error) {
      decodedSecurityToken = null;
    }
    assertNotNull(decodedSecurityToken);

    try {
      // bad token
      securityToken = "yJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU";
      decodedSecurityToken = tokenChecker.decodeSecurityToken(securityToken);
    }catch(Exception error) {
      decodedSecurityToken = null;
    }
    assertNull(decodedSecurityToken);

    try {
      // bad token
      securityToken = null;
      decodedSecurityToken = tokenChecker.decodeSecurityToken(securityToken);
    }catch(Exception error) {
      decodedSecurityToken = null;
    }
    assertNull(decodedSecurityToken);
  }

}
