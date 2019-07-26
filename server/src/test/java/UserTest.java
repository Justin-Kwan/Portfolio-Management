import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class UserTest {

  @Test
  public void test_updateUsername() {
    User user = new User("");

    user.updateInfo("Username1", "");
    assertEquals("Username1", user.getUsername());

    user.updateInfo("Username2", "");
    assertEquals("Username2", user.getUsername());

    user.updateInfo("Username3", "");
    assertEquals("Username3", user.getUsername());
  }

  @Test
  public void test_updateUserId() {
    User user = new User("");

    user.updateInfo("", "UserId1");
    assertEquals("UserId1", user.getUserId());

    user.updateInfo("", "UserId2");
    assertEquals("UserId2", user.getUserId());

    user.updateInfo("", "UserId3");
    assertEquals("UserId3", user.getUserId());
  }

  @Test
  public void test_updateSecurityToken() {
    User user;

    user = new User("securityToken_1*");
    assertEquals("securityToken_1*", user.getSecurityToken());

    user = new User("securityToken_2*");
    assertEquals("securityToken_2*", user.getSecurityToken());

    user = new User("securityToken_3*");
    assertEquals("securityToken_3*", user.getSecurityToken());
  }

  @Test
  public void test_getUsername() {
    User user = new User("");

    user.updateInfo("User123", "");
    assertEquals("User123", user.getUsername());

    user.updateInfo("User234", "");
    assertEquals("User234", user.getUsername());

    user.updateInfo("User345", "");
    assertEquals("User345", user.getUsername());
  }

  @Test
  public void test_getUserId() {
    User user = new User("");

    user.updateInfo("", "#123$");
    assertEquals("#123$", user.getUserId());

    user.updateInfo("", "098_ef");
    assertEquals("098_ef", user.getUserId());

    user.updateInfo("", "  ");
    assertEquals("  ", user.getUserId());
  }

  @Test
  public void test_getSecurityToken() {
    User user;

    user = new User("09876 *&_9");
    assertEquals("09876 *&_9", user.getSecurityToken());

    user = new User("_____");
    assertEquals("_____", user.getSecurityToken());

    user = new User("");
    assertEquals("", user.getSecurityToken());
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
