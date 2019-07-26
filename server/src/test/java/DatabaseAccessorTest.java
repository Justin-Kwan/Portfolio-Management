import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class DatabaseAccessorTest {

  private DatabaseAccessor beforeTest() {
    DatabaseAccessor DBA = new DatabaseAccessor();
    try {
      DBA.createConnection();
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
    user = new MockUser("username1", "userid1", "securitytoken1", 543.32);
    DBA.insertNewUser(user);


  }

  @Test
  public void test_checkUserExists() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    boolean doesUserExist;

    user = new MockUser("Robert123", "id#123*", "securitytoken_123", 231321);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user);
    assertEquals(true, doesUserExist);

    user = new MockUser("John91", "id#901%", "securitytoken_821", 20999);
    DBA.insertNewUser(user);
    doesUserExist = DBA.checkUserExists(user);
    assertEquals(true, doesUserExist);

    user = new MockUser("Jane231", "id#7281", "securitytoken_231", 312412);
    doesUserExist = DBA.checkUserExists(user);
    assertEquals(false, doesUserExist);

    user = new MockUser("", "", "", 0);
    doesUserExist = DBA.checkUserExists(user);
    assertEquals(false, doesUserExist);

    this.afterTest(DBA);
  }


}
