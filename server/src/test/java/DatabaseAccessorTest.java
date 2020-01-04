import java.util.Arrays;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class DatabaseAccessorTest {

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
    boolean doesUserExist;
    ArrayList<Coin> coins = new ArrayList<Coin>();

    user = new User("authtoken1", "userid1", null, "jsonrequest1");
    Coin coin1 = new Coin("BTC", 123);
    Coin coin2 = new Coin("NANO", 121);
    Coin coin3 = new Coin("LTC", 19);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user.getUserId());
    assertEquals(true, doesUserExist);

    user = new User("authtoken2", "userid2", null, "jsonrequest2");
    coin1 = new Coin("BTP", 123);
    coins = new ArrayList<>(Arrays.asList(coin1));
    user.jailbreak().coins = coins;
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user.getUserId());
    assertEquals(true, doesUserExist);

    this.afterTest(DBA);
  }

  @Test
  public void test_updateUser() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;
    ArrayList<Coin> coins;
    boolean doesUserExist;

    user = new User("auth_token1", "userid1", false, "jsonrequest1");
    Coin coin1 = new Coin("BTC", 3.132);
    Coin coin2 = new Coin("ETH", 3.131);
    Coin coin3 = new Coin("BPT", 3.532);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);

    user = new User("new_authtoken", "userid1", false, "new_jsonrequest");
    coin1 = new Coin("LTC", 3.192);
    coin2 = new Coin("BTH", 3.191);
    coin3 = new Coin("PPT", 9.532);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.updateUser(user);

    coins = DBA.selectUserCoins(user);

    assertEquals(3, coins.size());
    assertEquals("LTC", coins.get(FIRST_COIN).getTicker());
    assertEquals(3.192, coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BTH", coins.get(SECOND_COIN).getTicker());
    assertEquals(3.191, coins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("PPT", coins.get(THIRD_COIN).getTicker());
    assertEquals(9.532, coins.get(THIRD_COIN).getAmount(), 1e-8);



    user = new User("authtoken2", "userid2", false, "jsonrequest");
    coin1 = new Coin("HTC", 3.932);
    coin2 = new Coin("HTH", 3.191);
    coin3 = new Coin("HPT", 3.932);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);

    user = new User("new_authtoken", "userid1", false, "new_jsonrequest");
    coin1 = new Coin("EON", 32.292);
    coin2 = new Coin("TRON", 32.291);
    coin3 = new Coin("PPI", 92532);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.updateUser(user);

    coins = DBA.selectUserCoins(user);
    assertEquals(3, coins.size());
    assertEquals("EON", coins.get(FIRST_COIN).getTicker());
    assertEquals(32.292, coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("TRON", coins.get(SECOND_COIN).getTicker());
    assertEquals(32.291, coins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("PPI", coins.get(THIRD_COIN).getTicker());
    assertEquals(92532, coins.get(THIRD_COIN).getAmount(), 1e-8);

    this.afterTest(DBA);
  }

  @Test
  public void test_selectUser() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    ArrayList<Coin> coins;

    // first user selection test
    user = new User("auth_token1", "userid12", false, "jsonrequest1");
    Coin coin1 = new Coin("BTC", 0.231);
    Coin coin2 = new Coin("LTC", 0.531);
    Coin coin3 = new Coin("ETH", 0.931);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    user = DBA.selectUser("userid12");

    assertEquals("userid12", user.getUserId());
    assertEquals(3, user.getCoins().size());

    coins = user.getCoins();
    assertEquals("BTC", coins.get(FIRST_COIN).getTicker());
    assertEquals(0.231, coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(SECOND_COIN).getTicker());
    assertEquals(0.531, coins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("ETH", coins.get(THIRD_COIN).getTicker());
    assertEquals(0.931, coins.get(THIRD_COIN).getAmount(), 1e-8);


    // second user selection test
    user = new User("authtoken2", "userid13", true, "jsonrequest2");
    coin1 = new Coin("NEO", 0.223211);
    coins = new ArrayList<>(Arrays.asList(coin1));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    user = DBA.selectUser("userid13");

    coins = user.getCoins();
    assertEquals("userid13", user.getUserId());
    assertEquals(1, user.getCoins().size());
    assertEquals("NEO", coins.get(FIRST_COIN).getTicker());
    assertEquals(0.223211, coins.get(FIRST_COIN).getAmount(), 1e-8);

    this.afterTest(DBA);
  }

  @Test
  public void test_selectUserCoins() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    ArrayList<Coin> coins;

    user = new User("authtoken___1", "userid___1", null, "jsonrequest___1");
    Coin coin1 = new Coin("BTC", 0.34);
    Coin coin2 = new Coin("ETH", 0.35);
    Coin coin3 = new Coin("LTC", 0.36);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    coins = DBA.selectUserCoins(user);

    assertEquals(3, coins.size());
    assertEquals("BTC", coins.get(FIRST_COIN).getTicker());
    assertEquals(0.34, coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("ETH", coins.get(SECOND_COIN).getTicker());
    assertEquals(0.35, coins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(THIRD_COIN).getTicker());
    assertEquals(0.36, coins.get(THIRD_COIN).getAmount(), 1e-8);

    user = new User("authtoken___2", "userid___2", null, "jsonrequest___2");
    coin1 = new Coin("NEO", 0.20331231231);
    coin2 = new Coin("STRAT", 123.2);
    coin3 = new Coin("LTC", 0.36);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    coins = DBA.selectUserCoins(user);

    assertEquals(3, coins.size());
    assertEquals("NEO", coins.get(FIRST_COIN).getTicker());
    assertEquals(0.20331231231, coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("STRAT", coins.get(SECOND_COIN).getTicker());
    assertEquals(123.2, coins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(THIRD_COIN).getTicker());
    assertEquals(0.36, coins.get(THIRD_COIN).getAmount(), 1e-8);

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

    user = new User("authtoken_123", "id#123*", null, "jsonrequest___123");
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists("id#123*");
    assertEquals(true, doesUserExist);

    user = new User("authtoken_821", "id#901%", null, "jsonrequest___123");
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
