import java.util.Arrays;
import java.util.ArrayList;
import org.skyscreamer.jsonassert.JSONAssert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;

public class DatabaseAccessorTest {

  Gson gson = new Gson();

  final int FIRST_COIN  = 0;
  final int SECOND_COIN = 1;
  final int THIRD_COIN  = 2;

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

  @Test
  public void test_insertNewUser() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;
    boolean doesUserExist;

    user = new MockUser("authtoken1", "username1", "userid1", null, "jsonrequest1", 543.32);
    coin = new MockCoin("BTC", 123, 234, 567.2);
    user.addCoin(coin);
    coin = new MockCoin("NANO", 121, 235, 41);
    user.addCoin(coin);
    coin = new MockCoin("LTC", 19, 24, 51);
    user.addCoin(coin);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user.getUserId());
    assertEquals(true, doesUserExist);

    user = new MockUser("authtoken2", "username2", "userid2", null, "jsonrequest2", 543.32);
    coin = new MockCoin("BTP", 123, 234, 567.2);
    user.addCoin(coin);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user.getUserId());
    assertEquals(true, doesUserExist);

    afterTest(DBA);
  }

  @Test
  public void test_updateUser() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;
    JSONArray coinsJsonArray;
    User newUser;
    boolean doesUserExist;

    user = new MockUser("authtoken1", "username1", "userid1", false, "jsonrequest", 543.32);

    coin = new MockCoin("BTC", 3.132, 334, 8.231);
    user.addCoin(coin);
    coin = new MockCoin("ETH", 3.131, 331, 8.211);
    user.addCoin(coin);
    coin = new MockCoin("BPT", 3.532, 354, 8.235);
    user.addCoin(coin);

    DBA.insertNewUser(user);

    newUser = new MockUser("new_authtoken", "username1", "userid1", false, "new_jsonrequest", 543.32);

    coin = new MockCoin("LTC", 3.192, 934, 8.291);
    newUser.addCoin(coin);
    coin = new MockCoin("BTH", 3.191, 931, 8.291);
    newUser.addCoin(coin);
    coin = new MockCoin("PPT", 9.532, 954, 8.295);
    newUser.addCoin(coin);

    DBA.updateUser(newUser);
    doesUserExist = DBA.checkUserExists(newUser.getUserId());
    assertEquals(true, doesUserExist);

    coinsJsonArray = DBA.selectUserCoins(newUser);
    JSONAssert.assertEquals("{coinTicker:\"LTC\", coinAmount:3.192, latestCoinPrice:934, coinHoldingValueUsd:8.291}", coinsJsonArray.get(FIRST_COIN).toString(), true);
    JSONAssert.assertEquals("{coinTicker:\"BTH\", coinAmount:3.191, latestCoinPrice:931, coinHoldingValueUsd:8.291}", coinsJsonArray.get(SECOND_COIN).toString(), true);
    JSONAssert.assertEquals("{coinTicker:\"PPT\", coinAmount:9.532, latestCoinPrice:954, coinHoldingValueUsd:8.295}", coinsJsonArray.get(THIRD_COIN).toString(), true);
    assertEquals(3, coinsJsonArray.length());



    user = new MockUser("authtoken2", "username2", "userid2", false, "jsonrequest", 543.32);

    coin = new MockCoin("HTC", 3.932, 9934, 8.9991);
    user.addCoin(coin);
    coin = new MockCoin("HTH", 3.191, 391, 8.911);
    user.addCoin(coin);
    coin = new MockCoin("HPT", 3.932, 954, 8.239);
    user.addCoin(coin);

    DBA.insertNewUser(user);

    newUser = new MockUser("new_authtoken", "username1", "userid1", false, "new_jsonrequest", 543.32);

    coin = new MockCoin("EON", 32.292, 92234, 8.22291);
    newUser.addCoin(coin);
    coin = new MockCoin("TRON", 32.291, 92231, 228.291);
    newUser.addCoin(coin);
    coin = new MockCoin("PPI", 92532, 92254, 8.22295);
    newUser.addCoin(coin);

    DBA.updateUser(newUser);
    doesUserExist = DBA.checkUserExists(newUser.getUserId());
    assertEquals(true, doesUserExist);

    coinsJsonArray = DBA.selectUserCoins(newUser);
    JSONAssert.assertEquals("{coinTicker:\"EON\", coinAmount:32.292, latestCoinPrice:92234, coinHoldingValueUsd:8.22291}", coinsJsonArray.get(FIRST_COIN).toString(), true);
    JSONAssert.assertEquals("{coinTicker:\"TRON\", coinAmount:32.291, latestCoinPrice:92231, coinHoldingValueUsd:228.291}", coinsJsonArray.get(SECOND_COIN).toString(), true);
    JSONAssert.assertEquals("{coinTicker:\"PPI\", coinAmount:92532, latestCoinPrice:92254, coinHoldingValueUsd:8.22295}", coinsJsonArray.get(THIRD_COIN).toString(), true);
    assertEquals(3, coinsJsonArray.length());

    afterTest(DBA);
  }

  @Test
  public void test_selectUser() {
    DatabaseAccessor DBA = this.beforeTest();
    User mockUserInsertion;
    User mockUserClient;
    User user;
    JSONObject jsonUserObj;
    Coin mockCoin;
    ArrayList<Coin> userCoins;

    // first user selection test
    mockUserInsertion = new MockUser("authtoken1", "username1", "userid12", false, "jsonrequest1", 543.32);
    mockCoin = new MockCoin("BTC", 0.231, 23, 1);
    mockUserInsertion.addCoin(mockCoin);
    mockCoin = new MockCoin("LTC", 0.531, 53, 5);
    mockUserInsertion.addCoin(mockCoin);
    mockCoin = new MockCoin("ETH", 0.931, 93, 9);
    mockUserInsertion.addCoin(mockCoin);
    DBA.insertNewUser(mockUserInsertion);

    jsonUserObj = DBA.selectUser("userid12");
    user = gson.fromJson(jsonUserObj.toString(), User.class);

    assertEquals("authtoken1", user.getAuthToken());
    assertEquals("username1", user.getUsername());
    assertEquals("userid12", user.getUserId());
    assertEquals(false, user.getStatus());
    assertEquals("jsonrequest1", user.getJsonRequest());
    assertEquals(543.32, user.getPortfolioValueUsd(), 1e-8);
    assertEquals(3, user.getCoins().size());

    userCoins = user.getCoins();
    // first coin assertions
    assertEquals("BTC", userCoins.get(FIRST_COIN).getTicker());
    assertEquals(0.231, userCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals(23, userCoins.get(FIRST_COIN).getLatestPrice(), 1e-8);
    assertEquals(1, userCoins.get(FIRST_COIN).getHoldingValueUsd(), 1e-8);
    // second coin assertions
    assertEquals("LTC", userCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.531, userCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals(53, userCoins.get(SECOND_COIN).getLatestPrice(), 1e-8);
    assertEquals(5, userCoins.get(SECOND_COIN).getHoldingValueUsd(), 1e-8);
    // third coin assertions
    assertEquals("ETH", userCoins.get(THIRD_COIN).getTicker());
    assertEquals(0.931, userCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals(93, userCoins.get(THIRD_COIN).getLatestPrice(), 1e-8);
    assertEquals(9, userCoins.get(THIRD_COIN).getHoldingValueUsd(), 1e-8);


    // second user selection test
    mockUserInsertion = new MockUser("authtoken2", "username2", "userid13", true, "jsonrequest2", 544.3777312);
    mockCoin = new MockCoin("NEO", 0.223211, 21.323, 14);
    mockUserInsertion.addCoin(mockCoin);
    DBA.insertNewUser(mockUserInsertion);

    jsonUserObj = DBA.selectUser("userid13");
    user = gson.fromJson(jsonUserObj.toString(), User.class);

    assertEquals("authtoken2", user.getAuthToken());
    assertEquals("username2", user.getUsername());
    assertEquals("userid13", user.getUserId());
    assertEquals(true, user.getStatus());
    assertEquals("jsonrequest2", user.getJsonRequest());
    assertEquals(544.3777312, user.getPortfolioValueUsd(), 1e-8);
    assertEquals(1, user.getCoins().size());

    userCoins = user.getCoins();
    // first coin assertions
    assertEquals("NEO", userCoins.get(FIRST_COIN).getTicker());
    assertEquals(0.223211, userCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals(21.323, userCoins.get(FIRST_COIN).getLatestPrice(), 1e-8);
    assertEquals(14, userCoins.get(FIRST_COIN).getHoldingValueUsd(), 1e-8);

    this.afterTest(DBA);
  }

  @Test
  public void test_selectUserCoins() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;
    JSONArray coinsJsonArray;

    user = new MockUser("authtoken___1", "username___1", "userid___1", null, "jsonrequest___1", 543.32);
    coin = new MockCoin("BTC", 0.34, 234, 50);
    user.addCoin(coin);
    coin = new MockCoin("ETH", 0.35, 235, 51);
    user.addCoin(coin);
    coin = new MockCoin("LTC", 0.36, 236, 52);
    user.addCoin(coin);
    DBA.insertNewUser(user);
    coinsJsonArray = DBA.selectUserCoins(user);
    JSONAssert.assertEquals("{coinTicker:\"BTC\", coinAmount:0.34, latestCoinPrice:234, coinHoldingValueUsd:50}", coinsJsonArray.get(FIRST_COIN).toString(), true);
    JSONAssert.assertEquals("{coinTicker:\"ETH\", coinAmount:0.35, latestCoinPrice:235, coinHoldingValueUsd:51}", coinsJsonArray.get(SECOND_COIN).toString(), true);
    JSONAssert.assertEquals("{coinTicker:\"LTC\", coinAmount:0.36, latestCoinPrice:236, coinHoldingValueUsd:52}", coinsJsonArray.get(THIRD_COIN).toString(), true);
    assertEquals(3, coinsJsonArray.length());

    user = new MockUser("authtoken___2", "username___2", "userid___2", null, "jsonrequest___2", 543.33);
    coin = new MockCoin("NEO", 0.20331231231, 8891, 20);
    user.addCoin(coin);
    coin = new MockCoin("STRAT", 123.2, 231, 89);
    user.addCoin(coin);
    DBA.insertNewUser(user);
    coinsJsonArray = DBA.selectUserCoins(user);
    JSONAssert.assertEquals("{coinTicker:\"NEO\", coinAmount:0.20331231231, latestCoinPrice:8891, coinHoldingValueUsd:20}", coinsJsonArray.get(FIRST_COIN).toString(), true);
    JSONAssert.assertEquals("{coinTicker:\"STRAT\", coinAmount:123.2, latestCoinPrice:231, coinHoldingValueUsd:89}", coinsJsonArray.get(SECOND_COIN).toString(), true);
    assertEquals(2, coinsJsonArray.length());

    this.afterTest(DBA);
  }

  @Test
  public void test_checkUserExists() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    boolean doesUserExist;

    // test with empty db
    doesUserExist = DBA.checkUserExists("id12345");
    assertEquals(false, doesUserExist);

    user = new MockUser("authtoken_123", "Robert123", "id#123*", null, "jsonrequest___123", 231321);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists("id#123*");
    assertEquals(true, doesUserExist);

    user = new MockUser("authtoken_821", "John91", "id#901%", null, "jsonrequest___123", 20999);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists("id#901%");
    assertEquals(true, doesUserExist);

    doesUserExist = DBA.checkUserExists("id#7281");
    assertEquals(false, doesUserExist);

    doesUserExist = DBA.checkUserExists("");
    assertEquals(false, doesUserExist);

    afterTest(DBA);
  }


}
