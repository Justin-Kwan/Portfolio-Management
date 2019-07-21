// package main.java.UserPortfolioManagement;
import static org.junit.Assert.assertEquals;
import org.junit.Test;



public class UserTest {

  @Test
  public void test_updateUsername() {
    User user = new User("Username1", "12u123", "securityToken123*9");
    String username = user.getUsername();
    assertEquals(username, "Username1");
  }



}
