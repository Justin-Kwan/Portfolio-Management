import static org.junit.Assert.assertEquals;
import org.junit.Test;
import manifold.ext.api.Jailbreak;
import java.util.ArrayList;
import UserPortfolioManagement.CoinFactory;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class CoinFactoryTest {

  CoinFactory coinFactory = new CoinFactory();
  User        mockUser;
  Coin        mockCoin;

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
  public void test_addCoinsToCollectionFromDb() {
    DatabaseAccessor DBA  = this.beforeTest();
    final int FIRST_COIN  = 0;
    final int SECOND_COIN = 1;
    final int THIRD_COIN  = 2;

    mockUser = new MockUser("username", "userid", "authtoken", 0);

    mockCoin = new MockCoin("BTC", 23, 0.5, 66.98); // holding value usd
    mockUser.addCoin(mockCoin);

    mockCoin = new MockCoin("ETH", 0.5, 0.03, 9.00000123);
    mockUser.addCoin(mockCoin);

    mockCoin = new MockCoin("ZTC", 9812.3, -941, 1);
    mockUser.addCoin(mockCoin);

    DBA.insertNewUser(mockUser);
    ArrayList<Coin> coins = coinFactory.jailbreak().addCoinsToCollectionFromDb(mockUser, new ArrayList<Coin>());

    assertEquals("BTC", coins.get(FIRST_COIN).getTicker());
    assertEquals(23, coins.get(FIRST_COIN).getAmount(), 1e-8);
    assertEquals(0.5, coins.get(FIRST_COIN).getLatestPrice(), 1e-8);
    assertEquals(66.98, coins.get(FIRST_COIN).getHoldingValueUsd(), 1e-8);

    assertEquals("ETH", coins.get(SECOND_COIN).getTicker());
    assertEquals(0.5, coins.get(SECOND_COIN).getAmount(), 1e-8);
    assertEquals(0.03, coins.get(SECOND_COIN).getLatestPrice(), 1e-8);
    assertEquals(9.00000123, coins.get(SECOND_COIN).getHoldingValueUsd(), 1e-8);

    assertEquals("ZTC", coins.get(THIRD_COIN).getTicker());
    assertEquals(9812.3, coins.get(THIRD_COIN).getAmount(), 1e-8);
    assertEquals(-941, coins.get(THIRD_COIN).getLatestPrice(), 1e-8);
    assertEquals(1, coins.get(THIRD_COIN).getHoldingValueUsd(), 1e-8);
  }














}
