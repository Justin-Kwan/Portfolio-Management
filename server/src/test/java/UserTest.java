import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class UserTest {

  @Test
  public void test_updateUsername() {
    User user1 = new User("Username1", "", "");
    assertEquals("Username1", user1.getUsername());

    User user2 = new User("Username2", "", "");
    assertEquals("Username2", user2.getUsername());

    User user3 = new User("Username3", "", "");
    assertEquals("Username3", user3.getUsername());
  }

  @Test
  public void test_updateUserId() {
    User user1 = new User("", "UserId1", "");
    assertEquals("UserId1", user1.getUserId());

    User user2 = new User("", "UserId2", "");
    assertEquals("UserId2", user2.getUserId());

    User user3 = new User("", "UserId3", "");
    assertEquals("UserId3", user3.getUserId());
  }

  @Test
  public void test_updateSecurityToken() {
    User user1 = new User("", "", "securityToken_1*");
    assertEquals("securityToken_1*", user1.getSecurityToken());

    User user2 = new User("", "", "securityToken_2*");
    assertEquals("securityToken_2*", user2.getSecurityToken());

    User user3 = new User("", "UserId3", "securityToken_3*");
    assertEquals("securityToken_3*", user3.getSecurityToken());
  }

  @Test
  public void test_getUsername() {
    User user1 = new User("User123", "", "");
    assertEquals("User123", user1.getUsername());

    User user2 = new User("User234", "", "");
    assertEquals("User234", user2.getUsername());

    User user3 = new User(" ", "", "");
    assertEquals(" ", user3.getUsername());
  }

  @Test
  public void test_getUserId() {
    User user1 = new User("", "#123$", "");
    assertEquals("#123$", user1.getUserId());

    User user2 = new User("", "098_ef", "");
    assertEquals("098_ef", user2.getUserId());

    User user3 = new User("", "  ", "");
    assertEquals("  ", user3.getUserId());
  }

  @Test
  public void test_getSecurityToken() {
    User user1 = new User("", "", "09876 *&_9");
    assertEquals("09876 *&_9", user1.getSecurityToken());

    User user2 = new User("", "", "_____");
    assertEquals("_____", user2.getSecurityToken());

    User user3 = new User("", "", "");
    assertEquals("", user3.getSecurityToken());
  }

  @Test
  public void test_addCoin() {
    User user1 = new User("", "", "");
    Coin mockCoin1 = new MockCoin(346.5);
    user1.addCoin(mockCoin1);
    ArrayList<Coin> userCoins1 = user1.getCoins();
    double coinHoldingValueUsd1 = userCoins1.get(0).getHoldingValueUsd();
    assertEquals(346.5, coinHoldingValueUsd1, 1e-8);

    User user2 = new User("", "", "");
    Coin mockCoin2 = new MockCoin(3.642235324);
    user2.addCoin(mockCoin2);
    ArrayList<Coin> userCoins2 = user2.getCoins();
    double coinHoldingValueUsd2 = userCoins2.get(0).getHoldingValueUsd();
    assertEquals(3.642235324, coinHoldingValueUsd2, 1e-8);

    User user3 = new User("", "", "");
    Coin mockCoin3 = new MockCoin(0);
    user3.addCoin(mockCoin3);
    ArrayList<Coin> userCoins3 = user3.getCoins();
    double coinHoldingValueUsd3 = userCoins3.get(0).getHoldingValueUsd();
    assertEquals(0, coinHoldingValueUsd3, 1e-8);
  }

  @Test
  public void test_calculatePortfolioValue() {
    User user1 = new User("", "", "");
    //ArrayList<Object> mockCoins = new ArrayList<Object>();

    for(int i = 0; i < 7; i++) {
      Coin mockCoin = new MockCoin(24.5679);
      user1.addCoin(mockCoin);
    }

    user1.calculatePortfolioValue();
    assertEquals(171.9753, user1.getPortfolioValueUsd(), 1e-8);
  }


}
