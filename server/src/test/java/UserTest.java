import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class UserTest {

  @Test
  public void test_setAuthToken() {
    User user;

    user = new User("authToken_1*", "", null, "");
    assertEquals("authToken_1*", user.getAuthToken());

    user = new User("authToken_2*", "", null, "");
    assertEquals("authToken_2*", user.getAuthToken());

    user = new User("authToken_3*", "", null, "");
    assertEquals("authToken_3*", user.getAuthToken());
  }

  @Test
  public void test_setUserId() {
    User user;

    user = new User("", "UserId1", null, "");
    assertEquals("UserId1", user.getUserId());

    user = new User("", "UserId2", null, "");
    assertEquals("UserId2", user.getUserId());

    user = new User("", "UserId3", null, "");
    assertEquals("UserId3", user.getUserId());
  }

  @Test
  public void test_setStatus() {
    User user;

    user = new User("", "", true, "");
    assertEquals(true, user.getStatus());

    user = new User("", "", false, "");
    assertEquals(false, user.getStatus());
  }

  @Test
  public void test_setJsonRequest() {
    User user;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coinTicker\":\"LTC\",\"coinAmount\":1223}]}";

    user = new User("", "", null, mockJsonRequest1);
    assertEquals(mockJsonRequest1, user.getJsonRequest());

    user = new User("", "", null, mockJsonRequest2);
    assertEquals(mockJsonRequest2, user.getJsonRequest());

    user = new User("", "", null, mockJsonRequest3);
    assertEquals(mockJsonRequest3, user.getJsonRequest());
  }

  @Test
  public void test_getAuthToken() {
    User user;

    user = new User("09876 *&_9", "", null, "");
    assertEquals("09876 *&_9", user.getAuthToken());

    user = new User("_____", "", null, "");
    assertEquals("_____", user.getAuthToken());

    user = new User("", "", null, "");
    assertEquals("", user.getAuthToken());
  }

  @Test
  public void test_getUserId() {
    User user;

    user = new User("", "#123$", null, "");
    assertEquals("#123$", user.getUserId());

    user = new User("", "098_ef", null, "");
    assertEquals("098_ef", user.getUserId());

    user = new User("", "  ", null, "");
    assertEquals("  ", user.getUserId());
  }

  @Test
  public void test_getStatus() {
    User user;

    user = new User("", "", false, "");
    assertEquals(false, user.getStatus());

    user = new User("", "", true, "");
    assertEquals(true, user.getStatus());
  }

  @Test
  public void test_getJsonRequest() {
    User user;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"EOS\",\"coinAmount\":1223},{\"coinTicker\":\"BAS\",\"coinAmount\":3.42}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.42},{\"coinTicker\":\"LTC\",\"coinAmount\":35.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coinTicker\":\"XRP\",\"coinAmount\":12.23}]}";

    user = new User("", "", null, mockJsonRequest1);
    assertEquals(mockJsonRequest1, user.getJsonRequest());

    user = new User("", "", null, mockJsonRequest2);
    assertEquals(mockJsonRequest2, user.getJsonRequest());

    user = new User("", "", null, mockJsonRequest3);
    assertEquals(mockJsonRequest3, user.getJsonRequest());
  }

  @Test
  public void test_calculatePortfolioValue() {
    User user;
    ArrayList<Coin> coins = new ArrayList<Coin>();

    user = new User("", "", null, "");
    for(int i = 0; i < 7; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 24.5679);
      coins.add(mockCoin);
    }
    user.setCoins(coins);
    user.calculatePortfolioValue();
    assertEquals(171.9753, user.getPortfolioValueUsd(), 1e-8);

    coins.clear();

    user = new User("", "", null, "");
    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 41029.23122213);
      coins.add(mockCoin);
    }
    user.setCoins(coins);
    user.calculatePortfolioValue();
    assertEquals(123087.69366639, user.getPortfolioValueUsd(), 1e-8);

    coins.clear();

    user = new User("", "", null, "");
    for(int i = 0; i < 3; i++) {
      Coin mockCoin = new MockCoin("", 0, 0, 2);
      coins.add(mockCoin);
    }
    user.setCoins(coins);
    user.calculatePortfolioValue();
    assertEquals(6, user.getPortfolioValueUsd(), 1e-8);
  }

  @Test
  public void test_calculateCoinHoldingValues() {
    User user;
    ArrayList<Coin> coins = new ArrayList<Coin>();

    // test 1
    user = new User("", "", null, "");
    Coin mockCoin1 = new MockCoin("BTC", 2.445, 0, 0);
    Coin mockCoin2 = new MockCoin("ETH", 1, 0, 0);
    Coin mockCoin3 = new MockCoin("LTC", 0.5, 0, 0);
    Coin mockCoin4 = new MockCoin("BAT", 32, 0, 0);
    coins = new ArrayList<>(Arrays.asList(mockCoin1, mockCoin2, mockCoin3, mockCoin4));

    user.setCoins(coins);
    user.calculateCoinHoldingValues();
    coins = user.getCoins();

    for(int i = 0; i < coins.size(); i++) {
      assertNotNull(coins.get(i).getHoldingValueUsd());
    }

    // test 2
    user = new User("", "", null, "");
    mockCoin1 = new MockCoin("NEO", 1.445, 0, 0);
    mockCoin2 = new MockCoin("BAT", 11, 0, 0);
    mockCoin3 = new MockCoin("LTC", 1.5, 0, 0);
    mockCoin4 = new MockCoin("BTC", 31, 0, 0);
    coins = new ArrayList<>(Arrays.asList(mockCoin1, mockCoin2, mockCoin3, mockCoin4));

    user.setCoins(coins);
    user.calculateCoinHoldingValues();
    coins = user.getCoins();

    for(int i = 0; i < coins.size(); i++) {
      assertNotNull(coins.get(i).getHoldingValueUsd());
    }

  }

  @Test
  public void test_getPortfolioValueUsd() {
    User user = new User("", "", null, "");
    Coin mockCoin;
    ArrayList<Coin> coins = new ArrayList<Coin>();

    mockCoin = new MockCoin("", 0, 0, 80.9876);
    coins = new ArrayList<>(Arrays.asList(mockCoin));
    user.setCoins(coins);
    user.calculatePortfolioValue();
    assertEquals(80.9876, user.getPortfolioValueUsd(), 1e-8);

    mockCoin = new MockCoin("", 0, 0, 81.9876);
    coins = new ArrayList<>(Arrays.asList(mockCoin));
    user.setCoins(coins);
    user.calculatePortfolioValue();
    assertEquals(81.9876, user.getPortfolioValueUsd(), 1e-8);
  }

}
