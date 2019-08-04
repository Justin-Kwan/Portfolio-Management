import java.util.Arrays;
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

    user = new MockUser("username1", "userid1", "authtoken1", "jsonrequest1", 543.32);
    coin = new MockCoin("BTC", 123, 234, 567.2);
    user.addCoin(coin);
    coin = new MockCoin("NANO", 121, 235, 41);
    user.addCoin(coin);
    coin = new MockCoin("LTC", 19, 24, 51);
    user.addCoin(coin);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user.getUserId());
    assertEquals(true, doesUserExist);

    user = new MockUser("username2", "userid2", "authtoken2", "jsonrequest2", 543.32);
    coin = new MockCoin("BTP", 123, 234, 567.2);
    user.addCoin(coin);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user.getUserId());
    assertEquals(true, doesUserExist);

    this.afterTest(DBA);
  }

  @Test
  public void test_selectUserCoins() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;
    JSONArray coinsJsonArray;

    final int FIRST_COIN  = 0;
    final int SECOND_COIN = 1;
    final int THIRD_COIN  = 2;

    user = new MockUser("username___1", "userid___1", "authtoken___1", "jsonrequest___1", 543.32);
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

    user = new MockUser("username___2", "userid___2", "authtoken___2", "jsonrequest___2", 543.33);
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

    user = new MockUser("Robert123", "id#123*", "authtoken_123", "jsonrequest___123", 231321);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists("id#123*");
    assertEquals(true, doesUserExist);

    user = new MockUser("John91", "id#901%", "authtoken_821", "jsonrequest___123", 20999);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists("id#901%");
    assertEquals(true, doesUserExist);

    doesUserExist = DBA.checkUserExists("id#7281");
    assertEquals(false, doesUserExist);

    doesUserExist = DBA.checkUserExists("");
    assertEquals(false, doesUserExist);

    this.afterTest(DBA);
  }


}
