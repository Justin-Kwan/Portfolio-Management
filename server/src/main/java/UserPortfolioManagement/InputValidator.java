
class InputValidator {

  public boolean handleSecurityTokenValidation(String userSecurityToken) {

    boolean USER_SECURITY_TOKEN_FORM_INVALID = false;
    boolean isSecurityTokenEmpty = this.checkSecurityTokenEmpty(userSecurityToken);

    if(isSecurityTokenEmpty) {
      return USER_SECURITY_TOKEN_FORM_INVALID;
    }

  }

  public boolean checkSecurityTokenEmpty(String userSecurityToken) {
    boolean isSecurityTokenEmpty = userSecurityToken.isEmpty() || userSecurityToken == null;
    return isSecurityTokenEmpty;
  }







}
