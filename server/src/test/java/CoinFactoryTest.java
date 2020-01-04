import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import manifold.ext.api.Jailbreak;
import PortfolioManagement.CoinFactory;
import PortfolioManagement.DatabaseAccessor;
import PortfolioManagement.User;
import PortfolioManagement.Coin;

public class CoinFactoryTest {

  // jailbreak used to void private function state for tests

  CoinFactory coinFactory = new CoinFactory();

  final int FIRST_COIN  = 0;
  final int SECOND_COIN = 1;
  final int THIRD_COIN  = 2;
  final int FOURTH_COIN = 3;
  final int FIFTH_COIN  = 4;
  final int SIXTH_COIN  = 5;

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
  public void test_getCoinsFromLocation() {
    DatabaseAccessor DBA = this.beforeTest();
    ArrayList<Coin>  coins;
    User             user;

    String mockJsonRequest1 = "{\"coins\":[{\"coin_ticker\":\"EOS\",\"coin_amount\":1223},{\"coin_ticker\":\"BAS\",\"coin_amount\":3.42}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coin_ticker\":\"BAT\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.42},{\"coin_ticker\":\"LTC\",\"coin_amount\":35.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coin_ticker\":\"XRP\",\"coin_amount\":12.23}]}";

    /**
     *  database location tests
     */
    user = new User("authtoken1", "userid1", null, "jsonrequest1");
    Coin coin1 = new Coin("BTC", 23); // holding value usd
    Coin coin2 = new Coin("ETH", 0.5);
    Coin coin3 = new Coin("ZTC", 9812.3);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    coins = coinFactory.jailbreak().getCoinsFromLocation(user, "DATABASE");

    assertEquals("BTC", coins.get(FIRST_COIN).getTicker());
    assertEquals(23, coins.get(FIRST_COIN).getAmount(), 1e-8);

    assertEquals("ETH", coins.get(SECOND_COIN).getTicker());
    assertEquals(0.5, coins.get(SECOND_COIN).getAmount(), 1e-8);

    assertEquals("ZTC", coins.get(THIRD_COIN).getTicker());
    assertEquals(9812.3, coins.get(THIRD_COIN).getAmount(), 1e-8);



    user = new User("authtoken2", "userid2", null, "jsonrequest1");
    coin1 = new Coin("NEO", 24); // holding value usd
    coin2 = new Coin("XRP", 0.6);
    coin3 = new Coin("BTP", 9813.3);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.jailbreak().coins = coins;

    DBA.insertNewUser(user);
    coins = coinFactory.jailbreak().getCoinsFromLocation(user, "DATABASE");

    assertEquals("NEO", coins.get(FIRST_COIN).getTicker());
    assertEquals(24, coins.get(FIRST_COIN).getAmount(), 1e-8);

    assertEquals("XRP", coins.get(SECOND_COIN).getTicker());
    assertEquals(0.6, coins.get(SECOND_COIN).getAmount(), 1e-8);

    assertEquals("BTP", coins.get(THIRD_COIN).getTicker());
    assertEquals(9813.3, coins.get(THIRD_COIN).getAmount(), 1e-8);


    /**
     *  json request location tests
     */
    user = new User("authtoken3", "userid3", null, mockJsonRequest1);
    coins = coinFactory.jailbreak().getCoinsFromLocation(user, "JSON_REQUEST");
    assertEquals(2, coins.size());
    assertEquals("EOS", coins.get(FIRST_COIN).getTicker());
    assertEquals(1223,coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BAS", coins.get(SECOND_COIN).getTicker());
    assertEquals(3.42, coins.get(SECOND_COIN).getAmount(), 1e-8);

    user = new User("authtoken4", "userid4", null, mockJsonRequest2);
    coins = coinFactory.jailbreak().getCoinsFromLocation(user, "JSON_REQUEST");
    assertEquals(3, coins.size());
    assertEquals("BAT", coins.get(FIRST_COIN).getTicker());
    assertEquals(123, coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("NEO", coins.get(SECOND_COIN).getTicker());
    assertEquals(23.42, coins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("LTC", coins.get(THIRD_COIN).getTicker());
    assertEquals(35.49123, coins.get(THIRD_COIN).getAmount(), 1e-8);

    user = new User("authtoken5", "userid5", null, mockJsonRequest3);
    coins = coinFactory.jailbreak().getCoinsFromLocation(user, "JSON_REQUEST");
    assertEquals(1, coins.size());
    assertEquals("XRP", coins.get(FIRST_COIN).getTicker());
    assertEquals(12.23, coins.get(FIRST_COIN).getAmount(), 1e-8);

    this.afterTest(DBA);
  }

  @Test
  public void test_mergeCoins() {
    DatabaseAccessor DBA          = this.beforeTest();
    ArrayList<Coin>  dbCoins      = new ArrayList<Coin>();
    ArrayList<Coin>  requestCoins = new ArrayList<Coin>();
    ArrayList<Coin>  mergedCoins  = new ArrayList<Coin>();
    Coin             coin4;
    Coin             coin5;
    User user;

    /**
     *  merge coin test with no duplicate coins
     */

    // mock coins for database coin list
    Coin coin1 = new Coin("HIVE", 23);
    Coin coin2 = new Coin("DMG", 0.26);
    Coin coin3 = new Coin("TRON", 98113.3);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // mock coins for json request coin list
    coin1 = new Coin("EOS", 29);
    coin2 = new Coin("IOTA", 0.29);
    coin3 = new Coin("QTUM", 98913.3);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(6, mergedCoins.size());
    assertEquals("HIVE", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(23, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("DMG", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.26, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("TRON", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(98113.3, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("EOS", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(29, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);
    assertEquals("IOTA", mergedCoins.get(FIFTH_COIN).getTicker());
    assertEquals(0.29, mergedCoins.get(FIFTH_COIN).getAmount(), 1e-8);
    assertEquals("QTUM", mergedCoins.get(SIXTH_COIN).getTicker());
    assertEquals(98913.3, mergedCoins.get(SIXTH_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with duplicate coins
     */

    // mock coins for database coin list
    coin1 = new Coin("HIVE", 23);
    coin2 = new Coin("DMG", 0.26);
    coin3 = new Coin("TRON", 98113.3);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // mock coins for json request coin list
    coin1 = new Coin("EOS", 29);
    coin2 = new Coin("HIVE", 0.29);
    coin3 = new Coin("QTUM", 98913.3);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(5, mergedCoins.size());
    assertEquals("HIVE", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(23.29, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("DMG", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.26, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("TRON", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(98113.3, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("EOS", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(29, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);
    assertEquals("QTUM", mergedCoins.get(FIFTH_COIN).getTicker());
    assertEquals(98913.3, mergedCoins.get(FIFTH_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with duplicate coins
     */

    // mock coins for database coin list
    coin1 = new Coin("HIVE", 20);
    coin2 = new Coin("DMG", 0.26);
    coin3 = new Coin("TRON", 98113.3);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // mock coins for json request coin list
    coin1 = new Coin("HIVE", 59);
    coin2 = new Coin("HIVE", 8.4123);
    coin3 = new Coin("QTUM", 98913.3);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(4, mergedCoins.size());
    assertEquals("HIVE", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(87.4123, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("DMG", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.26, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("TRON", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(98113.3, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("QTUM", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(98913.3, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);


    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with all duplicate coins in request
     */

    // mock coins for database coin list
    coin1 = new Coin("HIVE", 20);
    coin2 = new Coin("DMG", 0.26);
    coin3 = new Coin("TRON", 98113.9);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // mock coins for json request coin list
    coin1 = new Coin("BTC", 79);
    coin2 = new Coin("BTC", 8.4723);
    coin3 = new Coin("BTC", 97913.3);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(4, mergedCoins.size());
    assertEquals("HIVE", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(20, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("DMG", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.26, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("TRON", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(98113.9, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("BTC", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(98000.7723, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with all duplicate coins in request and no database coins
     */

    // mock coins for json request coin list
    coin1 = new Coin("LTC", 78);
    coin2 = new Coin("LTC", 8.476);
    coin3 = new Coin("LTC", 47913.3);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(1, mergedCoins.size());
    assertEquals("LTC", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(47999.776, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with no coins in request
     */

    // mock coins for database coin list
    coin1 = new Coin("HIVE", 20);
    coin2 = new Coin("DMG", 0.26);
    coin3 = new Coin("TRON", 98113.9);
    coin4 = new Coin("NEO", 1);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3, coin4));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(4, mergedCoins.size());
    assertEquals("HIVE", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(20, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("DMG", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.26, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("TRON", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(98113.9, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("NEO", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(1, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with no coins in database
     */

    // mock coins for json request coin list
    coin1 = new Coin("LTC", 99);
    coin2 = new Coin("BTC", 9.99);
    coin3 = new Coin("ETH", 999.999);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(3, mergedCoins.size());
    assertEquals("LTC", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(99, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BTC", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(9.99, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("ETH", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(999.999, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with duplicate coins
     */

    // mock coins for database coin list
    coin1 = new Coin("LTC", 93);
    coin2 = new Coin("BTC", 9.39);
    coin3 = new Coin("ETH", 993.999);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // mock coins for json request coin list
    coin1 = new Coin("XRP", 20);
    coin2 = new Coin("LTC", 0.26);
    coin3 = new Coin("LTC", 98113.9);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(4, mergedCoins.size());
    assertEquals("LTC", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(98207.16, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BTC", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(9.39, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("ETH", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(993.999, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("XRP", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(20, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with duplicate coins
     */

    // mock coins for database coin list
    coin1 = new Coin("STM", 20);
    coin2 = new Coin("HYI", 0.26);
    coin3 = new Coin("ETH", 58113.9);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // mock coins for json request coin list
    coin1 = new Coin("LTC", 93);
    coin2 = new Coin("BTC", 9.39);
    coin3 = new Coin("ETH", 997.999);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(5, mergedCoins.size());
    assertEquals("STM", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(20, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("HYI", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.26, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("ETH", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(59111.899, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("LTC", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(93, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);
    assertEquals("BTC", mergedCoins.get(FIFTH_COIN).getTicker());
    assertEquals(9.39, mergedCoins.get(FIFTH_COIN).getAmount(), 1e-8);

    dbCoins.clear();
    requestCoins.clear();
    mergedCoins.clear();

    /**
     *  merge coin test with duplicate coins
     */

    // mock coins for database coin list
    coin1 = new Coin("TRON", 63);
    coin2 = new Coin("XRP", 63.1);
    coin3 = new Coin("EON", 56.7532);
    dbCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // mock coins for json request coin list
    coin1 = new Coin("XRP", 12.23);
    coin2 = new Coin("XRP", 13.23);
    coin3 = new Coin("BTC", 132.23);
    coin4 = new Coin("EON", 142.23);
    coin5 = new Coin("LTC", 132.24);
    requestCoins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3, coin4, coin5));

    mergedCoins = coinFactory.jailbreak().mergeCoins(dbCoins, requestCoins);

    assertEquals(5, mergedCoins.size());
    assertEquals("TRON", mergedCoins.get(FIRST_COIN).getTicker());
    assertEquals(63, mergedCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("XRP", mergedCoins.get(SECOND_COIN).getTicker());
    assertEquals(88.56, mergedCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("EON", mergedCoins.get(THIRD_COIN).getTicker());
    assertEquals(198.9832, mergedCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("BTC", mergedCoins.get(FOURTH_COIN).getTicker());
    assertEquals(132.23, mergedCoins.get(FOURTH_COIN).getAmount(), 1e-8);
    assertEquals("LTC", mergedCoins.get(FIFTH_COIN).getTicker());
    assertEquals(132.24, mergedCoins.get(FIFTH_COIN).getAmount(), 1e-8);
  }

  @Test
  public void test_createUserCoinCollection() {
    DatabaseAccessor DBA = this.beforeTest();
    ArrayList<Coin> coins = new ArrayList<Coin>();
    ArrayList<Coin> pulledUserCoins = new ArrayList<Coin>();
    User userInsertion;
    User userClient;
    Coin coin;

    String mockJsonRequest1 = "{\"coins\":[{\"coin_ticker\":\"BAT\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.42},{\"coin_ticker\":\"LTC\",\"coin_amount\":35.49123}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coin_ticker\":\"XRP\",\"coin_amount\":12.23},{\"coin_ticker\":\"XRP\",\"coin_amount\":13.23},{\"coin_ticker\":\"BTC\",\"coin_amount\":132.23},{\"coin_ticker\":\"EON\",\"coin_amount\":142.23},{\"coin_ticker\":\"LTC\",\"coin_amount\":132.23}]}";

    userInsertion = new User("authtoken1", "userid1", null, "");
    Coin coin1 = new Coin("TRON", 63);
    Coin coin2 = new Coin("XRP", 63.1);
    Coin coin3 = new Coin("EON", 56.7532);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    userInsertion.setCoins(coins);
    DBA.insertNewUser(userInsertion);

    userClient = new User("authtoken1", "userid1", true, mockJsonRequest1);
    pulledUserCoins = coinFactory.createUserCoinCollection(userClient); // true means user exists

    assertEquals(6, pulledUserCoins.size());
    assertEquals("TRON", pulledUserCoins.get(FIRST_COIN).getTicker());
    assertEquals(63, pulledUserCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("XRP", pulledUserCoins.get(SECOND_COIN).getTicker());
    assertEquals(63.1, pulledUserCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("EON", pulledUserCoins.get(THIRD_COIN).getTicker());
    assertEquals(56.7532, pulledUserCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("BAT", pulledUserCoins.get(FOURTH_COIN).getTicker());
    assertEquals(123, pulledUserCoins.get(FOURTH_COIN).getAmount(), 1e-8);
    assertEquals("NEO", pulledUserCoins.get(FIFTH_COIN).getTicker());
    assertEquals(23.42, pulledUserCoins.get(FIFTH_COIN).getAmount(), 1e-8);
    assertEquals("LTC", pulledUserCoins.get(SIXTH_COIN).getTicker());
    assertEquals(35.49123, pulledUserCoins.get(SIXTH_COIN).getAmount(), 1e-8);


    coins.clear();
    pulledUserCoins.clear();

    userInsertion = new User("authtoken2", "userid2", null, "");
    coin1 = new Coin("TRON", 63);
    coin2 = new Coin("XRP", 63.1);
    coin3 = new Coin("EON", 56.7532);
    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    userInsertion.setCoins(coins);
    DBA.insertNewUser(userInsertion);

    userClient = new User("authtoken2", "userid2", true, mockJsonRequest2);
    pulledUserCoins = coinFactory.createUserCoinCollection(userClient); // true means user exists

    assertEquals(5, pulledUserCoins.size());
    assertEquals("TRON", pulledUserCoins.get(FIRST_COIN).getTicker());
    assertEquals(63, pulledUserCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("XRP", pulledUserCoins.get(SECOND_COIN).getTicker());
    assertEquals(88.56, pulledUserCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("EON", pulledUserCoins.get(THIRD_COIN).getTicker());
    assertEquals(198.9832, pulledUserCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("BTC", pulledUserCoins.get(FOURTH_COIN).getTicker());
    assertEquals(132.23, pulledUserCoins.get(FOURTH_COIN).getAmount(), 1e-8);
    assertEquals("LTC", pulledUserCoins.get(FIFTH_COIN).getTicker());
    assertEquals(132.23, pulledUserCoins.get(FIFTH_COIN).getAmount(), 1e-8);

    this.afterTest(DBA);
  }


}
