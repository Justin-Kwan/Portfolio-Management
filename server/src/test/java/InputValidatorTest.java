import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.InputValidator;

public class InputValidatorTest {

  private InputValidator inputValidator = new InputValidator();
  private boolean isSecurityTokenEmpty;
  private boolean isSecurityTokenValid;

  @Test
  public void test_checkInputEmpty() {
    isSecurityTokenEmpty = inputValidator.checkInputEmpty("securityToken_1*");
    assertEquals(false, isSecurityTokenEmpty);

    isSecurityTokenEmpty = inputValidator.checkInputEmpty(" ");
    assertEquals(false, isSecurityTokenEmpty);

    isSecurityTokenEmpty = inputValidator.checkInputEmpty("");
    assertEquals(true, isSecurityTokenEmpty);

    isSecurityTokenEmpty = inputValidator.checkInputEmpty(null);
    assertEquals(true, isSecurityTokenEmpty);
  }

  @Test
  public void test_handleSecurityTokenValidation() {
    isSecurityTokenValid = inputValidator.handleSecurityTokenValidation("securityToken_1*");
    assertEquals(true, isSecurityTokenValid);

    isSecurityTokenValid = inputValidator.handleSecurityTokenValidation("");
    assertEquals(false, isSecurityTokenValid);

    isSecurityTokenValid = inputValidator.handleSecurityTokenValidation(null);
    assertEquals(false, isSecurityTokenValid);
  }

}
