package UserPortfolioManagement;

public class InputValidator {

  public boolean handleAuthTokenValidation(String authToken) {
    boolean AUTH_TOKEN_VALID = true;
    boolean AUTH_TOKEN_INVALID = false;

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
