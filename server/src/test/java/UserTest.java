import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class UserTest {

  @Test
  public void test_updateUsername() {
    User user1 = new User("");
    user1.updateInfo("Username1", "");
    assertEquals("Username1", user1.getUsername());

    User user2 = new User("");
    user2.updateInfo("Username2", "");
    assertEquals("Username2", user2.getUsername());

    User user3 = new User("");
    user3.updateInfo("Username3", "");
    assertEquals("Username3", user3.getUsername());
  }

  @Test
  public void test_updateUserId() {
    User user1 = new User("");
    user1.updateInfo("", "UserId1");
    assertEquals("UserId1", user1.getUserId());

    User user2 = new User("");
    user2.updateInfo("", "UserId2");
    assertEquals("UserId2", user2.getUserId());

    User user3 = new User("");
    user3.updateInfo("", "UserId3");
    assertEquals("UserId3", user3.getUserId());
  }

  @Test
  public void test_updateSecurityToken() {
    User user1 = new User("securityToken_1*");
    assertEquals("securityToken_1*", user1.getSecurityToken());

    User user2 = new User("securityToken_2*");
    assertEquals("securityToken_2*", user2.getSecurityToken());

    User user3 = new User("securityToken_3*");
    assertEquals("securityToken_3*", user3.getSecurityToken());
  }

  @Test
  public void test_getUsername() {
    User user1 = new User("");
    user1.updateInfo("User123", "");
    assertEquals("User123", user1.getUsername());

    User user2 = new User("");
    user2.updateInfo("User234", "");
    assertEquals("User234", user2.getUsername());

    User user3 = new User("");
    user3.updateInfo("User345", "");
    assertEquals("User345", user3.getUsername());
  }

  @Test
  public void test_getUserId() {
    User user1 = new User("");
    user1.updateInfo("", "#123$");
    assertEquals("#123$", user1.getUserId());

    User user2 = new User("");
    user2.updateInfo("", "098_ef");
    assertEquals("098_ef", user2.getUserId());

    User user3 = new User("");
    user3.updateInfo("", "  ");
    assertEquals("  ", user3.getUserId());
  }

  @Test
  public void test_getSecurityToken() {
    User user1 = new User("09876 *&_9");
    assertEquals("09876 *&_9", user1.getSecurityToken());

    User user2 = new User("_____");
    assertEquals("_____", user2.getSecurityToken());

    User user3 = new User("");
    assertEquals("", user3.getSecurityToken());
  }

  @Test
  public void test_addCoin() {
    User user1 = new User("");
    Coin mockCoin1 = new MockCoin("", 0, 0, 346.5); // holding value usd
    user1.addCoin(mockCoin1);
    ArrayList<Coin> userCoins1 = user1.getCoins();
    double coinHoldingValueUsd1 = userCoins1.get(0).getHoldingValueUsd();
    assertEquals(346.5, coinHoldingValueUsd1, 1e-8);

    User user2 = new User("");
    Coin mockCoin2 = new MockCoin("", 0, 0, 3.642235324);
    user2.addCoin(mockCoin2);
    ArrayList<Coin> userCoins2 = user2.getCoins();
    double coinHoldingValueUsd2 = userCoins2.get(0).getHoldingValueUsd();
    assertEquals(3.642235324, coinHoldingValueUsd2, 1e-8);

    User user3 = new User("");
    Coin mockCoin3 = new MockCoin("", 0, 0, 0);
    user3.addCoin(mockCoin3);
    ArrayList<Coin> userCoins3 = user3.getCoins();
    double coinHoldingValueUsd3 = userCoins3.get(0).getHoldingValueUsd();
    assertEquals(0, coinHoldingValueUsd3, 1e-8);
  }

  @Test
  public void test_getPortfolioValueUsd() {
    User user1 = new User("");
    Coin mockCoin1 = new MockCoin("", 0, 0, 80.9876);
    user1.addCoin(mockCoin1);
    user1.calculatePortfolioValue();
    assertEquals(80.9876, user1.getPortfolioValueUsd(), 1e-8);

    User user2 = new User("");
    Coin mockCoin2 = new MockCoin("", 0, 0, 1);
    user2.addCoin(mockCoin2);
    user2.calculatePortfolioValue();
    assertEquals(1, user2.getPortfolioValueUsd(), 1e-8);
  }

  @Test
  public void test_authorizeRequest() {

    // good token, username: randomuser123, id: ff4406fc-67b2-4f86-b2dd-4f9b35c64202
    String user1SecurityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";
    User user1 = new User(user1SecurityToken);
    boolean isRequestAuthorized1 = user1.authorizeRequest();
    assertEquals(true, isRequestAuthorized1);

    // good token, useername: user109, id: e8e16b6f-cd81-4136-9d54-4c292469c5ee
    String user2SecurityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXIxMDkiLCJ1c2VyIGlkIjoiZThlMTZiNmYtY2Q4MS00MTM2LTlkNTQtNGMyOTI0NjljNWVlIn0.Ywz3tXTHf5A5i00VSJAUzLKL0F47N37tFv-UtGP_3gU";
    User user2 = new User(user1SecurityToken);
    boolean isRequestAuthorized2 = user2.authorizeRequest();
    assertEquals(true, isRequestAuthorized2);

    // bad token, wrong secret key
    String user3SecurityToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Imp1c3Rpbmt3YW4xMjMiLCJ1c2VyIGlkIjoiNzFkODczN2YtZGMwNy00NzkxLWJlNTktMmM1NDAxNDhkMmFkIn0.ym0qRcy0yyu30xB8a_9MydVe4DIn_x0nlbepMiOId-E";
    User user3 = new User(user3SecurityToken);
    boolean isRequestAuthorized3 = user3.authorizeRequest();
    assertEquals(false, isRequestAuthorized3);

    // bad token
    String user4SecurityToken = "ey";
    User user4 = new User(user4SecurityToken);
    boolean isRequestAuthorized4 = user4.authorizeRequest();
    assertEquals(false, isRequestAuthorized4);

    // bad token
    String user5SecurityToken = " ";
    User user5 = new User(user5SecurityToken);
    boolean isRequestAuthorized5 = user5.authorizeRequest();
    assertEquals(false, isRequestAuthorized5);

    // bad token
    String user6SecurityToken = "^";
    User user6 = new User(user6SecurityToken);
    boolean isRequestAuthorized6 = user6.authorizeRequest();
    assertEquals(false, isRequestAuthorized6);

    // bad token
    String user7SecurityToken = null;
    User user7 = new User(user7SecurityToken);
    boolean isRequestAuthorized7 = user7.authorizeRequest();
    assertEquals(false, isRequestAuthorized7);

    // bad token
    String user8SecurityToken = "";
    User user8 = new User(user7SecurityToken);
    boolean isRequestAuthorized8 = user8.authorizeRequest();
    assertEquals(false, isRequestAuthorized8);
  }

  @Test
  public void test_calculatePortfolioValue() {
    User user1 = new User("");

    for(int i = 0; i < 7; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 24.5679);
      user1.addCoin(mockCoin);
    }

    user1.calculatePortfolioValue();
    assertEquals(171.9753, user1.getPortfolioValueUsd(), 1e-8);

    User user2 = new User("");

    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 41029.23122213);
      user2.addCoin(mockCoin);
    }

    user2.calculatePortfolioValue();
    assertEquals(123087.69366639, user2.getPortfolioValueUsd(), 1e-8);

    User user3 = new User("");

    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 2);
      user3.addCoin(mockCoin);
    }

    user3.calculatePortfolioValue();
    assertEquals(6, user3.getPortfolioValueUsd(), 1e-8);
  }

}
