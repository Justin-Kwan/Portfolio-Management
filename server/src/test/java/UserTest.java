import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class UserTest {

  @Test
  public void test_setUsername() {
    User user = new User("");

    user.setInfo("Username1", "", "");
    assertEquals("Username1", user.getUsername());

    user.setInfo("Username2", "", "");
    assertEquals("Username2", user.getUsername());

    user.setInfo("Username3", "", "");
    assertEquals("Username3", user.getUsername());
  }

  @Test
  public void test_setUserId() {
    User user = new User("");

    user.setInfo("", "UserId1", "");
    assertEquals("UserId1", user.getUserId());

    user.setInfo("", "UserId2", "");
    assertEquals("UserId2", user.getUserId());

    user.setInfo("", "UserId3", "");
    assertEquals("UserId3", user.getUserId());
  }

  @Test
  public void test_setAuthToken() {
    User user;

    user = new User("authToken_1*");
    assertEquals("authToken_1*", user.getAuthToken());

    user = new User("authToken_2*");
    assertEquals("authToken_2*", user.getAuthToken());

    user = new User("authToken_3*");
    assertEquals("authToken_3*", user.getAuthToken());
  }

  @Test
  public void test_setJsonRequest() {
    User user;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coinTicker\":\"LTC\",\"coinAmount\":1223}]}";

    user = new User("");
    user.setInfo("", "", mockJsonRequest1);
    assertEquals(mockJsonRequest1, user.getJsonRequest());

    user = new User("");
    user.setInfo("", "", mockJsonRequest2);
    assertEquals(mockJsonRequest2, user.getJsonRequest());

    user = new User("");
    user.setInfo("", "", mockJsonRequest3);
    assertEquals(mockJsonRequest3, user.getJsonRequest());
  }

  @Test
  public void test_setInfo() {
    User user = new User("");

    user.setInfo("Username1", "userId1", "jsonrequest1");
    assertEquals("Username1", user.getUsername());
    assertEquals("userId1", user.getUserId());
    assertEquals("jsonrequest1", user.getJsonRequest());

    user.setInfo("Username2", "userId2", "jsonrequest2");
    assertEquals("Username2", user.getUsername());
    assertEquals("userId2", user.getUserId());
    assertEquals("jsonrequest2", user.getJsonRequest());

    user.setInfo("Username3", "userId3", "jsonrequest3");
    assertEquals("Username3", user.getUsername());
    assertEquals("userId3", user.getUserId());
    assertEquals("jsonrequest3", user.getJsonRequest());
  }

  @Test
  public void test_getUsername() {
    User user = new User("");

    user.setInfo("User123", "", "");
    assertEquals("User123", user.getUsername());

    user.setInfo("User234", "", "");
    assertEquals("User234", user.getUsername());

    user.setInfo("User345", "", "");
    assertEquals("User345", user.getUsername());
  }

  @Test
  public void test_getUserId() {
    User user = new User("");

    user.setInfo("", "#123$", "");
    assertEquals("#123$", user.getUserId());

    user.setInfo("", "098_ef", "");
    assertEquals("098_ef", user.getUserId());

    user.setInfo("", "  ", "");
    assertEquals("  ", user.getUserId());
  }

  @Test
  public void test_getAuthToken() {
    User user;

    user = new User("09876 *&_9");
    assertEquals("09876 *&_9", user.getAuthToken());

    user = new User("_____");
    assertEquals("_____", user.getAuthToken());

    user = new User("");
    assertEquals("", user.getAuthToken());
  }

  @Test
  public void test_getJsonRequest() {
    User user;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"EOS\",\"coinAmount\":1223},{\"coinTicker\":\"BAS\",\"coinAmount\":3.42}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.42},{\"coinTicker\":\"LTC\",\"coinAmount\":35.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coinTicker\":\"XRP\",\"coinAmount\":12.23}]}";

    user = new User("");
    user.setInfo("", "", mockJsonRequest1);
    assertEquals(mockJsonRequest1, user.getJsonRequest());

    user = new User("");
    user.setInfo("", "", mockJsonRequest2);
    assertEquals(mockJsonRequest2, user.getJsonRequest());

    user = new User("");
    user.setInfo("", "", mockJsonRequest3);
    assertEquals(mockJsonRequest3, user.getJsonRequest());
  }

  @Test
  public void test_addCoin() {
    User user = new User("");
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

    user = new User("");
    for(int i = 0; i < 7; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 24.5679);
      user.addCoin(mockCoin);
    }
    user.calculatePortfolioValue();
    assertEquals(171.9753, user.getPortfolioValueUsd(), 1e-8);

    user = new User("");
    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 41029.23122213);
      user.addCoin(mockCoin);
    }
    user.calculatePortfolioValue();
    assertEquals(123087.69366639, user.getPortfolioValueUsd(), 1e-8);

    user = new User("");
    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 2);
      user.addCoin(mockCoin);
    }
    user.calculatePortfolioValue();
    assertEquals(6, user.getPortfolioValueUsd(), 1e-8);
  }

  @Test
  public void test_getPortfolioValueUsd() {
    User user = new User("");
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
