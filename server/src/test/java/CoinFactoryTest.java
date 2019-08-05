import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.ArrayList;
import manifold.ext.api.Jailbreak;
import UserPortfolioManagement.CoinFactory;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class CoinFactoryTest {

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
    ArrayList<Coin>  dbCoins;
    ArrayList<Coin>  requestCoins;
    User             mockUser;
    Coin             mockCoin;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"EOS\",\"coinAmount\":1223},{\"coinTicker\":\"BAS\",\"coinAmount\":3.42}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.42},{\"coinTicker\":\"LTC\",\"coinAmount\":35.49123}]}";
    String mockJsonRequest3 = "{\"coins\":[{\"coinTicker\":\"XRP\",\"coinAmount\":12.23}]}";

    /**
     *  database location tests
     */

    // test 1
    mockUser = new MockUser("authtoken1", "username1", "userid1", null, "jsonrequest1", 0);

    mockCoin = new MockCoin("BTC", 23, 0.5, 66.98); // holding value usd
    mockUser.addCoin(mockCoin);
    mockCoin = new MockCoin("ETH", 0.5, 0.03, 9.00000123);
    mockUser.addCoin(mockCoin);
    mockCoin = new MockCoin("ZTC", 9812.3, -941, 1);
    mockUser.addCoin(mockCoin);

    DBA.insertNewUser(mockUser);
    dbCoins = coinFactory.jailbreak().getCoinsFromLocation(mockUser, "DATABASE");

    assertEquals("BTC", dbCoins.get(FIRST_COIN).getTicker());
    assertEquals(23, dbCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals(0.5, dbCoins.get(FIRST_COIN).getLatestPrice(), 1e-8);
    assertEquals(66.98, dbCoins.get(FIRST_COIN).getHoldingValueUsd(), 1e-8);

    assertEquals("ETH", dbCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.5, dbCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals(0.03, dbCoins.get(SECOND_COIN).getLatestPrice(), 1e-8);
    assertEquals(9.00000123, dbCoins.get(SECOND_COIN).getHoldingValueUsd(), 1e-8);

    assertEquals("ZTC", dbCoins.get(THIRD_COIN).getTicker());
    assertEquals(9812.3, dbCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals(-941, dbCoins.get(THIRD_COIN).getLatestPrice(), 1e-8);
    assertEquals(1, dbCoins.get(THIRD_COIN).getHoldingValueUsd(), 1e-8);

    // test 2
    mockUser = new MockUser("authtoken2", "username2", "userid2", null, "jsonrequest1", 0);

    mockCoin = new MockCoin("NEO", 24, 0.6, 66.99); // holding value usd
    mockUser.addCoin(mockCoin);
    mockCoin = new MockCoin("XRP", 0.6, 0.05, 9.00200123);
    mockUser.addCoin(mockCoin);
    mockCoin = new MockCoin("BTP", 9813.3, -9121, 3);
    mockUser.addCoin(mockCoin);

    DBA.insertNewUser(mockUser);
    dbCoins = coinFactory.jailbreak().getCoinsFromLocation(mockUser, "DATABASE");

    assertEquals("NEO", dbCoins.get(FIRST_COIN).getTicker());
    assertEquals(24, dbCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals(0.6, dbCoins.get(FIRST_COIN).getLatestPrice(), 1e-8);
    assertEquals(66.99, dbCoins.get(FIRST_COIN).getHoldingValueUsd(), 1e-8);

    assertEquals("XRP", dbCoins.get(SECOND_COIN).getTicker());
    assertEquals(0.6, dbCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals(0.05, dbCoins.get(SECOND_COIN).getLatestPrice(), 1e-8);
    assertEquals(9.00200123, dbCoins.get(SECOND_COIN).getHoldingValueUsd(), 1e-8);

    assertEquals("BTP", dbCoins.get(THIRD_COIN).getTicker());
    assertEquals(9813.3, dbCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals(-9121, dbCoins.get(THIRD_COIN).getLatestPrice(), 1e-8);
    assertEquals(3, dbCoins.get(THIRD_COIN).getHoldingValueUsd(), 1e-8);

    /**
     *  json request location tests
     */

    mockUser = new MockUser("authtoken3", "username3", "userid3", null, mockJsonRequest1, 0);
    requestCoins = coinFactory.jailbreak().getCoinsFromLocation(mockUser, "JSON_REQUEST");
    assertEquals(2, requestCoins.size());
    assertEquals("EOS", requestCoins.get(FIRST_COIN).getTicker());
    assertEquals(1223,requestCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BAS", requestCoins.get(SECOND_COIN).getTicker());
    assertEquals(3.42, requestCoins.get(SECOND_COIN).getAmount(), 1e-8);

    mockUser = new MockUser("authtoken4", "username4", "userid4", null, mockJsonRequest2, 0);
    requestCoins = coinFactory.jailbreak().getCoinsFromLocation(mockUser, "JSON_REQUEST");
    assertEquals(3, requestCoins.size());
    assertEquals("BAT", requestCoins.get(FIRST_COIN).getTicker());
    assertEquals(123, requestCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("NEO", requestCoins.get(SECOND_COIN).getTicker());
    assertEquals(23.42, requestCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("LTC", requestCoins.get(THIRD_COIN).getTicker());
    assertEquals(35.49123, requestCoins.get(THIRD_COIN).getAmount(), 1e-8);

    mockUser = new MockUser("authtoken5", "username5", "userid5", null, mockJsonRequest3, 0);
    requestCoins = coinFactory.jailbreak().getCoinsFromLocation(mockUser, "JSON_REQUEST");
    assertEquals(1, requestCoins.size());
    assertEquals("XRP", requestCoins.get(FIRST_COIN).getTicker());
    assertEquals(12.23, requestCoins.get(FIRST_COIN).getAmount(), 1e-8);

    afterTest(DBA);
  }

  @Test
  public void test_mergeCoins() {
    DatabaseAccessor DBA          = this.beforeTest();
    ArrayList<Coin>  dbCoins      = new ArrayList<Coin>();
    ArrayList<Coin>  requestCoins = new ArrayList<Coin>();
    ArrayList<Coin>  mergedCoins  = new ArrayList<Coin>();
    Coin             mockCoin;

    /**
     *  merge coin test with no duplicate coins
     */

    // mock coins for database coin list
    mockCoin = new Coin("HIVE", 23);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("DMG", 0.26);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("TRON", 98113.3);
    dbCoins.add(mockCoin);

    // mock coins for json request coin list
    mockCoin = new Coin("EOS", 29);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("IOTA", 0.29);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("QTUM", 98913.3);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("HIVE", 23);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("DMG", 0.26);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("TRON", 98113.3);
    dbCoins.add(mockCoin);

    // mock coins for json request coin list
    mockCoin = new Coin("EOS", 29);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("HIVE", 0.29);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("QTUM", 98913.3);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("HIVE", 20);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("DMG", 0.26);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("TRON", 98113.3);
    dbCoins.add(mockCoin);

    // mock coins for json request coin list
    mockCoin = new Coin("HIVE", 59);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("HIVE", 8.4123);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("QTUM", 98913.3);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("HIVE", 20);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("DMG", 0.26);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("TRON", 98113.9);
    dbCoins.add(mockCoin);

    // mock coins for json request coin list
    mockCoin = new Coin("BTC", 79);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("BTC", 8.4723);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("BTC", 97913.3);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("LTC", 78);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("LTC", 8.476);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("LTC", 47913.3);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("HIVE", 20);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("DMG", 0.26);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("TRON", 98113.9);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("NEO", 1);
    dbCoins.add(mockCoin);

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
    mockCoin = new Coin("LTC", 99);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("BTC", 9.99);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("ETH", 999.999);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("LTC", 93);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("BTC", 9.39);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("ETH", 993.999);
    dbCoins.add(mockCoin);

    // mock coins for json request coin list
    mockCoin = new Coin("XRP", 20);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("LTC", 0.26);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("LTC", 98113.9);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("STM", 20);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("HYI", 0.26);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("ETH", 58113.9);
    dbCoins.add(mockCoin);

    // mock coins for json request coin list
    mockCoin = new Coin("LTC", 93);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("BTC", 9.39);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("ETH", 997.999);
    requestCoins.add(mockCoin);

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
    mockCoin = new Coin("TRON", 63);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("XRP", 63.1);
    dbCoins.add(mockCoin);
    mockCoin = new Coin("EON", 56.7532);
    dbCoins.add(mockCoin);

    // mock coins for json request coin list
    mockCoin = new Coin("XRP", 12.23);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("XRP", 13.23);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("BTC", 132.23);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("EON", 142.23);
    requestCoins.add(mockCoin);
    mockCoin = new Coin("LTC", 132.24);
    requestCoins.add(mockCoin);

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
  public void test_loadCoinsToUser() {
    ArrayList<Coin> coins = new ArrayList<Coin>();
    ArrayList<Coin> pulledUserCoins = new ArrayList<Coin>();
    User mockUser;
    Coin mockCoin;

    mockUser = new MockUser("authtoken1", "username1", "userid1", null, "jsonrequest1", 0);
    mockCoin = new Coin("LTC", 93);
    coins.add(mockCoin);
    mockCoin = new Coin("BTC", 33);
    coins.add(mockCoin);
    mockCoin = new Coin("PTC", 53.7532);
    coins.add(mockCoin);
    coinFactory.jailbreak().loadCoinsToUser(mockUser, coins);
    pulledUserCoins = mockUser.getCoins();

    assertEquals(3, pulledUserCoins.size());
    assertEquals("LTC", pulledUserCoins.get(FIRST_COIN).getTicker());
    assertEquals(93, pulledUserCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BTC", pulledUserCoins.get(SECOND_COIN).getTicker());
    assertEquals(33, pulledUserCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("PTC", pulledUserCoins.get(THIRD_COIN).getTicker());
    assertEquals(53.7532, pulledUserCoins.get(THIRD_COIN).getAmount(), 1e-8);

    coins.clear();

    mockUser = new MockUser("authtoken2", "username2", "userid2", null, "jsonrequest2", 0);
    mockCoin = new Coin("NEO", 13);
    coins.add(mockCoin);
    mockCoin = new Coin("BAT", 31);
    coins.add(mockCoin);
    mockCoin = new Coin("XRP", 53.7512);
    coins.add(mockCoin);
    mockCoin = new Coin("NSR", 52.7512);
    coins.add(mockCoin);
    coinFactory.jailbreak().loadCoinsToUser(mockUser, coins);
    pulledUserCoins = mockUser.getCoins();

    assertEquals(4, pulledUserCoins.size());
    assertEquals("NEO", pulledUserCoins.get(FIRST_COIN).getTicker());
    assertEquals(13, pulledUserCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BAT", pulledUserCoins.get(SECOND_COIN).getTicker());
    assertEquals(31, pulledUserCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("XRP", pulledUserCoins.get(THIRD_COIN).getTicker());
    assertEquals(53.7512, pulledUserCoins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals("NSR", pulledUserCoins.get(FOURTH_COIN).getTicker());
    assertEquals(52.7512, pulledUserCoins.get(FOURTH_COIN).getAmount(), 1e-8);
  }

  @Test
  public void test_createUserCoinCollection() {
    DatabaseAccessor DBA = this.beforeTest();
    ArrayList<Coin> coins = new ArrayList<Coin>();
    ArrayList<Coin> pulledUserCoins = new ArrayList<Coin>();
    User mockUserInsertion;
    User mockUserClient;
    Coin mockCoin;

    String mockJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"BAT\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.42},{\"coinTicker\":\"LTC\",\"coinAmount\":35.49123}]}";
    String mockJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"XRP\",\"coinAmount\":12.23},{\"coinTicker\":\"XRP\",\"coinAmount\":13.23},{\"coinTicker\":\"BTC\",\"coinAmount\":132.23},{\"coinTicker\":\"EON\",\"coinAmount\":142.23},{\"coinTicker\":\"LTC\",\"coinAmount\":132.23}]}";

    mockUserInsertion = new MockUser("authtoken1", "username1", "userid1", null, "", 0);
    mockCoin = new Coin("TRON", 63);
    mockUserInsertion.addCoin(mockCoin);
    mockCoin = new Coin("XRP", 63.1);
    mockUserInsertion.addCoin(mockCoin);
    mockCoin = new Coin("EON", 56.7532);
    mockUserInsertion.addCoin(mockCoin);
    DBA.insertNewUser(mockUserInsertion);

    mockUserClient = new MockUser("authtoken1", "username1", "userid1", true, mockJsonRequest1, 0);
    coinFactory.createUserCoinCollection(mockUserClient); // true means user exists
    pulledUserCoins = mockUserClient.getCoins();

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


    mockUserInsertion = new MockUser("authtoken2", "username2", "userid2", null, "", 0);
    mockCoin = new Coin("TRON", 63);
    mockUserInsertion.addCoin(mockCoin);
    mockCoin = new Coin("XRP", 63.1);
    mockUserInsertion.addCoin(mockCoin);
    mockCoin = new Coin("EON", 56.7532);
    mockUserInsertion.addCoin(mockCoin);
    DBA.insertNewUser(mockUserInsertion);

    mockUserClient = new MockUser("authtoken2", "username2", "userid2", true, mockJsonRequest2, 0);
    coinFactory.createUserCoinCollection(mockUserClient); // true means user exists
    pulledUserCoins = mockUserClient.getCoins();

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

    afterTest(DBA);
  }


}
