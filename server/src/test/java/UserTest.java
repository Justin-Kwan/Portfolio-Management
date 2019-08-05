import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class UserTest {

  @Test
  public void test_setAuthToken() {
    User user;

    user = new User("authToken_1*", "", "", null, "");
    assertEquals("authToken_1*", user.getAuthToken());

    user = new User("authToken_2*", "", "", null, "");
    assertEquals("authToken_2*", user.getAuthToken());

    user = new User("authToken_3*", "", "", null, "");
    assertEquals("authToken_3*", user.getAuthToken());
  }

  @Test
  public void test_setUsername() {
    User user;

    user = new User("", "Username1", "", null, "");
    assertEquals("Username1", user.getUsername());

    user = new User("", "Username2", "", null, "");
    assertEquals("Username2", user.getUsername());

    user = new User("", "Username3", "", null, "");
    assertEquals("Username3", user.getUsername());
  }

  @Test
  public void test_setUserId() {
    User user;

    user = new User("", "", "UserId1", null, "");
    assertEquals("UserId1", user.getUserId());

    user = new User("", "", "UserId2", null, "");
    assertEquals("UserId2", user.getUserId());

    user = new User("", "", "UserId3", null, "");
    assertEquals("UserId3", user.getUserId());
  }

  @Test
  public void test_setStatus() {
    User user;

    user = new User("", "", "", true, "");
    assertEquals(true, user.getStatus());

    user = new User("", "", "", false, "");
    assertEquals(false, user.getStatus());
  }

  @Test
  public void test_setJsonRequest() {
    User user;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coinTicker\":\"LTC\",\"coinAmount\":1223}]}";

    user = new User("", "", "", null, mockJsonRequest1);
    assertEquals(mockJsonRequest1, user.getJsonRequest());

    user = new User("", "", "", null, mockJsonRequest2);
    assertEquals(mockJsonRequest2, user.getJsonRequest());

    user = new User("", "", "", null, mockJsonRequest3);
    assertEquals(mockJsonRequest3, user.getJsonRequest());
  }

  @Test
  public void test_getAuthToken() {
    User user;

    user = new User("09876 *&_9", "", "", null, "");
    assertEquals("09876 *&_9", user.getAuthToken());

    user = new User("_____", "", "", null, "");
    assertEquals("_____", user.getAuthToken());

    user = new User("", "", "", null, "");
    assertEquals("", user.getAuthToken());
  }

  @Test
  public void test_getUsername() {
    User user;

    user = new User("", "User123", "", null, "");
    assertEquals("User123", user.getUsername());

    user = new User("", "User234", "", null, "");
    assertEquals("User234", user.getUsername());

    user = new User("", "User345", "", null, "");
    assertEquals("User345", user.getUsername());
  }

  @Test
  public void test_getUserId() {
    User user;

    user = new User("", "", "#123$", null, "");
    assertEquals("#123$", user.getUserId());

    user = new User("", "", "098_ef", null, "");
    assertEquals("098_ef", user.getUserId());

    user = new User("", "", "  ", null, "");
    assertEquals("  ", user.getUserId());
  }

  @Test
  public void test_getStatus() {
    User user;

    user = new User("", "", "", false, "");
    assertEquals(false, user.getStatus());

    user = new User("", "", "", true, "");
    assertEquals(true, user.getStatus());
  }

  @Test
  public void test_getJsonRequest() {
    User user;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"EOS\",\"coinAmount\":1223},{\"coinTicker\":\"BAS\",\"coinAmount\":3.42}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.42},{\"coinTicker\":\"LTC\",\"coinAmount\":35.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coinTicker\":\"XRP\",\"coinAmount\":12.23}]}";

    user = new User("", "", "", null, mockJsonRequest1);
    assertEquals(mockJsonRequest1, user.getJsonRequest());

    user = new User("", "", "", null, mockJsonRequest2);
    assertEquals(mockJsonRequest2, user.getJsonRequest());

    user = new User("", "", "", null, mockJsonRequest3);
    assertEquals(mockJsonRequest3, user.getJsonRequest());
  }

  @Test
  public void test_addCoin() {
    User user = new User("", "", "", null, "");
    ArrayList<Coin> userCoins;
    double coinHoldingValueUsd;
    Coin mockCoin;

    int FIRST_COIN  = 0;
    int SECOND_COIN = 1;
    int THIRD_COIN  = 2;

    mockCoin = new MockCoin("", 0, 0, 346.5); // holding value usd
    user.addCoin(mockCoin);
    userCoins = user.getCoins();
    coinHoldingValueUsd = userCoins.get(FIRST_COIN).getHoldingValueUsd();
    assertEquals(346.5, coinHoldingValueUsd, 1e-8);

    mockCoin = new MockCoin("", 0, 0, 3.642235324);
    user.addCoin(mockCoin);
    userCoins = user.getCoins();
    coinHoldingValueUsd = userCoins.get(SECOND_COIN).getHoldingValueUsd();
    assertEquals(3.642235324, coinHoldingValueUsd, 1e-8);

    mockCoin = new MockCoin("", 0, 0, 0);
    user.addCoin(mockCoin);
    userCoins = user.getCoins();
    coinHoldingValueUsd = userCoins.get(THIRD_COIN).getHoldingValueUsd();
    assertEquals(0, coinHoldingValueUsd, 1e-8);
  }

  @Test
  public void test_calculatePortfolioValue() {
    User user;

    user = new User("", "", "", null, "");
    for(int i = 0; i < 7; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 24.5679);
      user.addCoin(mockCoin);
    }
    user.calculatePortfolioValue();
    assertEquals(171.9753, user.getPortfolioValueUsd(), 1e-8);

    user = new User("", "", "", null, "");
    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 41029.23122213);
      user.addCoin(mockCoin);
    }
    user.calculatePortfolioValue();
    assertEquals(123087.69366639, user.getPortfolioValueUsd(), 1e-8);

    user = new User("", "", "", null, "");
    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 2);
      user.addCoin(mockCoin);
    }
    user.calculatePortfolioValue();
    assertEquals(6, user.getPortfolioValueUsd(), 1e-8);
  }

  @Test
  public void test_getPortfolioValueUsd() {
    User user = new User("", "", "", null, "");
    Coin mockCoin;

    mockCoin = new MockCoin("", 0, 0, 80.9876);
    user.addCoin(mockCoin);
    user.calculatePortfolioValue();
    assertEquals(80.9876, user.getPortfolioValueUsd(), 1e-8);

    mockCoin = new MockCoin("", 0, 0, 1);
    user.addCoin(mockCoin);
    user.calculatePortfolioValue();
    assertEquals(81.9876, user.getPortfolioValueUsd(), 1e-8);
  }

}
