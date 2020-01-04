import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import manifold.ext.api.Jailbreak;
import UserPortfolioManagement.ObjectMapper;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;
import UserPortfolioManagement.DatabaseAccessor;


public class ObjectMapperTest {

  ObjectMapper objectMapper = new ObjectMapper();
  DatabaseAccessor DBA = new DatabaseAccessor();


  private DatabaseAccessor beforeTest() {
    DatabaseAccessor DBA = new DatabaseAccessor();
    try {
      DBA.createConnection();
      DBA.clearDatabase();
    }
    catch(Exception error) {
      System.out.println(error);
    }
    return DBA;
  }

  private void afterTest(DatabaseAccessor DBA) {
    DBA.clearDatabase();
    DBA.closeConnection();
  }


  /**
   * indirect test
   */
  @Test
  public void test_mapUserObjForApp() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    ArrayList<Coin> coins;
    ArrayList<Coin> selectedCoins;

    user = new User("auth_token", "user_id", false, "request_json");
    Coin coin1 = new Coin("BTC", 1);
    Coin coin2 = new Coin("LTC", 2.34);
    Coin coin3 = new Coin("ETH", 0.001);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    user = DBA.selectUser(user.getUserId());
    selectedCoins = user.getCoins();

    assertEquals("user_id", user.getUserId());
    assertEquals(3, coins.size(), 1e-8);
    assertEquals("BTC", coins.get(0).getTicker());
    assertEquals(1, coins.get(0).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(1).getTicker());
    assertEquals(2.34, coins.get(1).getAmount(), 1e-8);
    assertEquals("ETH", coins.get(2).getTicker());
    assertEquals(0.001, coins.get(2).getAmount(), 1e-8);


    user = new User("auth_token", "user_id", false, "request_json");
    coin1 = new Coin("NEO", 0);
    coin2 = new Coin("LTC", -1);
    coin3 = new Coin("ETH", -1.023);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    user = DBA.selectUser(user.getUserId());
    selectedCoins = user.getCoins();

    assertEquals("user_id", user.getUserId());
    assertEquals(3, coins.size(), 1e-8);
    assertEquals("NEO", coins.get(0).getTicker());
    assertEquals(0, coins.get(0).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(1).getTicker());
    assertEquals(-1, coins.get(1).getAmount(), 1e-8);
    assertEquals("ETH", coins.get(2).getTicker());
    assertEquals(-1.023, coins.get(2).getAmount(), 1e-8);

    this.afterTest(DBA);
  }

  @Test
  public void test_mapCoinsObjForApp() {
    String coinsStr = "[{coin_ticker:\"BTC\", coin_amount:1},{coin_ticker:\"LTC\", coin_amount:2.34},{coin_ticker:\"ETH\", coin_amount:0.001}]";
    JSONArray coinsJson = new JSONArray(coinsStr);
    ArrayList<Coin> coins = objectMapper.jailbreak().mapCoinsObjForApp(coinsJson);

    assertEquals(3, coins.size(), 1e-8);
    assertEquals("BTC", coins.get(0).getTicker());
    assertEquals(1, coins.get(0).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(1).getTicker());
    assertEquals(2.34, coins.get(1).getAmount(), 1e-8);
    assertEquals("ETH", coins.get(2).getTicker());
    assertEquals(0.001, coins.get(2).getAmount(), 1e-8);


    coinsStr = "[{coin_ticker:\"NEO\", coin_amount:0},{coin_ticker:\"LTC\", coin_amount:-1},{coin_ticker:\"ETH\", coin_amount:-1.023}]";
    coinsJson = new JSONArray(coinsStr);
    coins = objectMapper.jailbreak().mapCoinsObjForApp(coinsJson);

    assertEquals(3, coins.size(), 1e-8);
    assertEquals("NEO", coins.get(0).getTicker());
    assertEquals(0, coins.get(0).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(1).getTicker());
    assertEquals(-1, coins.get(1).getAmount(), 1e-8);
    assertEquals("ETH", coins.get(2).getTicker());
    assertEquals(-1.023, coins.get(2).getAmount(), 1e-8);
  }

  @Test
  public void test_mapTokenServerResponseObjForApp() {
    String response = "{ \"is user authorized\": true, \"user id\":\"user_id_1\", \"response code\":200}";
    JSONObject responseJson = new JSONObject(response);
    Object[] authTokenPayload = objectMapper.mapTokenServerResponseObjForApp(responseJson);
    assertEquals(true, authTokenPayload[0]);
    assertEquals("user_id_1", authTokenPayload[1]);


    response = "{ \"is user authorized\": false, \"user id\":\"user_id_2\", \"response code\":200}";
    responseJson = new JSONObject(response);
    authTokenPayload = objectMapper.mapTokenServerResponseObjForApp(responseJson);
    assertEquals(false, authTokenPayload[0]);
    assertEquals("user_id_2", authTokenPayload[1]);
  }


}
