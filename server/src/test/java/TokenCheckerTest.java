import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import com.auth0.jwt.interfaces.DecodedJWT;
import UserPortfolioManagement.TokenChecker;


public class TokenCheckerTest {

  TokenChecker tokenChecker = new TokenChecker();
  String authToken;

  @Test
  public void test_checkAuthToken() {
    boolean isRequestAuthorized;

    // good token, username: randomuser123, id: ff4406fc-67b2-4f86-b2dd-4f9b35c64202
    authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(true, isRequestAuthorized);

    // good token, useername: user109, id: e8e16b6f-cd81-4136-9d54-4c292469c5ee
    authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU";
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(true, isRequestAuthorized);

    // bad token, wrong secret key
    authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imp1c3Rpbmt3YW4xMjMiLCJ1c2VyIGlkIjoiNzFkODczN2YtZGMwNy00NzkxLWJlNTktMmM1NDAxNDhkMmFkIn0.ym0qRcy0yyu30xB8a_9MydVe4DIn_x0nlbepMiOId-E";
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    authToken = "ey";
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    authToken = " ";
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    authToken = "^";
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    authToken = null;
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(false, isRequestAuthorized);

    // bad token
    authToken = "";
    isRequestAuthorized = tokenChecker.checkAuthToken(authToken);
    assertEquals(false, isRequestAuthorized);
  }

  @Test
  public void test_decodeAuthToken() {
    DecodedJWT decodedAuthToken;

    try {
      // good token, username: randomuser123, id: ff4406fc-67b2-4f86-b2dd-4f9b35c64202
      authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";
      decodedAuthToken = tokenChecker.decodeAuthToken(authToken);
    }catch(Exception error) {
      decodedAuthToken = null;
    }
    assertNotNull(decodedAuthToken);

    try {
      // good token, useername: user109, id: e8e16b6f-cd81-4136-9d54-4c292469c5ee
      authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU";
      decodedAuthToken = tokenChecker.decodeAuthToken(authToken);
    }catch(Exception error) {
      decodedAuthToken = null;
    }
    assertNotNull(decodedAuthToken);

    try {
      // bad token
      authToken = "yJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU";
      decodedAuthToken = tokenChecker.decodeAuthToken(authToken);
    }catch(Exception error) {
      decodedAuthToken = null;
    }
    assertNull(decodedAuthToken);

    try {
      // bad token
      authToken = null;
      decodedAuthToken = tokenChecker.decodeAuthToken(authToken);
    }catch(Exception error) {
      decodedAuthToken = null;
    }
    assertNull(decodedAuthToken);
  }

}
