import java.util.Arrays;
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
    user = new MockUser("username1", "userid1", "authtoken1", 543.32);
    DBA.insertNewUser(user);


  }

  @Test
  public void test_selectUserCoins() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;

    user = new MockUser("username___1", "userid___1", "authtoken___1", 543.32);

    for(int i = 0; i < 5; i++) {
      Coin coin = new MockCoin("BTC", 0.34, 234, 50);
      user.addCoin(coin);
    }

    DBA.insertNewUser(user);
    JSONArray coinsJsonArray = DBA.selectUserCoins(user);

    for(int i = 0; i < coinsJsonArray.length(); i++) {
      Coin coin = gson.fromJson(coinsJsonArray.get(i).toString(), Coin.class);
      System.out.println("COIN AMOUNT: " + coin.getAmount());
    }

  }

  @Test
  public void test_checkUserExists() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    boolean doesUserExist;

    doesUserExist = DBA.checkUserExists("id12345");
    assertEquals(false, doesUserExist);

    user = new MockUser("Robert123", "id#123*", "authtoken_123", 231321);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists("id#123*");
    assertEquals(true, doesUserExist);

    user = new MockUser("John91", "id#901%", "authtoken_821", 20999);
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
