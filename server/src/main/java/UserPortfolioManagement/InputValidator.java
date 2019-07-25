package UserPortfolioManagement;

public class InputValidator {

  public boolean handleSecurityTokenValidation(String securityToken) {
    boolean SECURITY_TOKEN_VALID = true;
    boolean SECURITY_TOKEN_INVALID = false;

    boolean isSecurityTokenEmpty = this.checkInputEmpty(securityToken);

    if(isSecurityTokenEmpty) {
      return SECURITY_TOKEN_INVALID;
    }
    return SECURITY_TOKEN_VALID;


  }



  public boolean checkInputEmpty(String input) {
    boolean isInputEmpty = (input == null || input.isEmpty());
    return isInputEmpty;
  }





}
