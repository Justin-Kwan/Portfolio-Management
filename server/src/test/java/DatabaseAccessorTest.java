import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class DatabaseAccessorTest {

  @Test
  public void test_insertNewUser() {
    DatabaseAccessor DBA = new DatabaseAccessor();
    try {
      DBA.createConnection();
    }
    catch(Exception error) {
      System.out.println(error);
    }

    User user1 = new MockUser("username1", "userid1", "securitytoken1", 543.32);

    DBA.insertNewUser(user1);



  }


}
