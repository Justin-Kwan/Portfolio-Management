package UserPortfolioManagement;

public class InputValidator {

  private final boolean AUTH_TOKEN_VALID = true;
  private final boolean AUTH_TOKEN_INVALID = false;

  public boolean handleAuthTokenValidation(String authToken) {
    boolean isAuthTokenEmpty = this.checkInputEmpty(authToken);

    if(isAuthTokenEmpty) {
      return AUTH_TOKEN_INVALID;
    }
    return AUTH_TOKEN_VALID;
  }

  public boolean checkInputEmpty(String input) {
    boolean isInputEmpty = (input == null || input.isEmpty());
    return isInputEmpty;
  }





}
