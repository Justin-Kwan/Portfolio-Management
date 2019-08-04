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

  final int        FIRST_COIN  = 0;
  final int        SECOND_COIN = 1;
  final int        THIRD_COIN  = 2;
  final int        FOURTH_COIN = 3;
  final int        FIFTH_COIN  = 4;
  final int        SIXTH_COIN  = 5;

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
    mockUser = new MockUser("username1", "userid1", "authtoken1", "jsonrequest1", 0);

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
    mockUser = new MockUser("username2", "userid2", "authtoken2", "jsonrequest1", 0);

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

    mockUser = new MockUser("username3", "userid3", "authtoken3", mockJsonRequest1, 0);
    requestCoins = coinFactory.jailbreak().getCoinsFromLocation(mockUser, "JSON_REQUEST");
    assertEquals(2, requestCoins.size());
    assertEquals("EOS", requestCoins.get(FIRST_COIN).getTicker());
    assertEquals(1223,requestCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("BAS", requestCoins.get(SECOND_COIN).getTicker());
    assertEquals(3.42, requestCoins.get(SECOND_COIN).getAmount(), 1e-8);

    mockUser = new MockUser("username4", "userid4", "authtoken4", mockJsonRequest2, 0);
    requestCoins = coinFactory.jailbreak().getCoinsFromLocation(mockUser, "JSON_REQUEST");
    assertEquals(3, requestCoins.size());
    assertEquals("BAT", requestCoins.get(FIRST_COIN).getTicker());
    assertEquals(123, requestCoins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals("NEO", requestCoins.get(SECOND_COIN).getTicker());
    assertEquals(23.42, requestCoins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals("LTC", requestCoins.get(THIRD_COIN).getTicker());
    assertEquals(35.49123, requestCoins.get(THIRD_COIN).getAmount(), 1e-8);

    mockUser = new MockUser("username5", "userid5", "authtoken5", mockJsonRequest3, 0);
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

    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    System.out.println("NEW TEST!");
    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    System.out.println("NEW TEST!");
    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    System.out.println("NEW TEST!");
    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    System.out.println("NEW TEST!");
    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    System.out.println("NEW TEST!");
    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    System.out.println("NEW TEST!");
    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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

    System.out.println("NEW TEST!");
    for(int i = 0; i < mergedCoins.size(); i++) {
      System.out.println("EACH MERGED COIN: " + mergedCoins.get(i).getTicker());
    }

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


  }


}
