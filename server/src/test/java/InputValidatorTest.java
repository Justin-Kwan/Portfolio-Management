import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.InputValidator;

public class InputValidatorTest {

  @Test
  public void test_checkInputEmpty() {
    InputValidator inputValidator1 = new InputValidator();
    boolean isSecurityTokenEmpty1 = inputValidator1.checkInputEmpty("securityToken_1*");
    assertEquals(false, isSecurityTokenEmpty1);

    InputValidator inputValidator2 = new InputValidator();
    boolean isSecurityTokenEmpty2 = inputValidator2.checkInputEmpty(" ");
    assertEquals(false, isSecurityTokenEmpty2);

    InputValidator inputValidator3 = new InputValidator();
    boolean isSecurityTokenEmpty3 = inputValidator3.checkInputEmpty("");
    assertEquals(true, isSecurityTokenEmpty3);

    InputValidator inputValidator4 = new InputValidator();
    boolean isSecurityTokenEmpty4 = inputValidator4.checkInputEmpty(null);
    assertEquals(true, isSecurityTokenEmpty4);
  }

  @Test
  public void test_handleSecurityTokenValidation() {
    InputValidator inputValidator1 = new InputValidator();
    boolean isSecurityTokenValid1 = inputValidator1.handleSecurityTokenValidation("securityToken_1*");
    assertEquals(true, isSecurityTokenValid1);

    InputValidator inputValidator2 = new InputValidator();
    boolean isSecurityTokenValid2 = inputValidator2.handleSecurityTokenValidation("");
    assertEquals(false, isSecurityTokenValid2);

    InputValidator inputValidator3 = new InputValidator();
    boolean isSecurityTokenValid3 = inputValidator3.handleSecurityTokenValidation(null);
    assertEquals(false, isSecurityTokenValid3);
  }

}
