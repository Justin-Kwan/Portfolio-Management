package UserPortfolioManagement;

public class RequestHandler {

  ResultCodes      resultCodes    = new ResultCodes();
  InputValidator   inputValidator = new InputValidator();
  TokenChecker     tokenChecker   = new TokenChecker();
  CoinFactory      coinFactory    = new CoinFactory();
  DatabaseAccessor DBA            = new DatabaseAccessor();


  public String handleAddCoins(String authToken, String userCoinRequest) {

    boolean isAuthTokenFormatValid = inputValidator.handleAuthTokenValidation(authToken);
    boolean isRequestAuthorized = tokenChecker.checkAuthToken(authToken);

    if(!isAuthTokenFormatValid || !isRequestAuthorized)
      return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    String[] userInfoPayload = tokenChecker.getAuthTokenInfo(authToken);
    User user = new User(authToken);
    user.setInfo(userInfoPayload[0], userInfoPayload[1]);

    boolean doesUserExist = DBA.checkUserExists(userInfoPayload[1]);

    coinFactory.createCoinCollection(user, doesUserExist);


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
