import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.InputValidator;

public class InputValidatorTest {

  private InputValidator inputValidator = new InputValidator();
  private boolean isAuthTokenEmpty;
  private boolean isAuthTokenValid;

  @Test
  public void test_checkInputEmpty() {
    isAuthTokenEmpty = inputValidator.checkInputEmpty("authToken_1*");
    assertEquals(false, isAuthTokenEmpty);

    isAuthTokenEmpty = inputValidator.checkInputEmpty(" ");
    assertEquals(false, isAuthTokenEmpty);

    isAuthTokenEmpty = inputValidator.checkInputEmpty("");
    assertEquals(true, isAuthTokenEmpty);

    isAuthTokenEmpty = inputValidator.checkInputEmpty(null);
    assertEquals(true, isAuthTokenEmpty);
  }

  @Test
  public void test_handleAuthTokenValidation() {
    isAuthTokenValid = inputValidator.handleAuthTokenValidation("authToken_1*");
    assertEquals(true, isAuthTokenValid);

    isAuthTokenValid = inputValidator.handleAuthTokenValidation("");
    assertEquals(false, isAuthTokenValid);

    isAuthTokenValid = inputValidator.handleAuthTokenValidation(null);
    assertEquals(false, isAuthTokenValid);
  }

}
