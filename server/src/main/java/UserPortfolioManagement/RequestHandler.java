package UserPortfolioManagement;

public class RequestHandler {

  ResultCodes      resultCodes    = new ResultCodes();
  InputValidator   inputValidator = new InputValidator();
  TokenChecker     tokenChecker   = new TokenChecker();
  DatabaseAccessor DBA            = new DatabaseAccessor();


  public String handleAddCoins(String authToken, String userCoinRequest) {

    boolean isAuthTokenFormatValid = inputValidator.handleAuthTokenValidation(authToken);
    boolean isRequestAuthorized = tokenChecker.checkAuthToken(authToken);

    if(isAuthTokenFormatValid == false || isRequestAuthorized == false)
      return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    User user = new User(authToken);

    return "Good";

  }

  public void handleGetCoins() {



  }

  public void handleUpdateCoins() {





  }

  public void handleDeleteCoins() {



  }

  public boolean handleCheckUserExists(String userId) {
    DBA.createConnection();
    boolean doesUserExist = DBA.checkUserExists(userId);
    DBA.closeConnection();
    return doesUserExist;
  }

}
