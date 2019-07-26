package UserPortfolioManagement;

public class RequestHandler {

  ResultCodes resultCodes = new ResultCodes();
  InputValidator inputValidator = new InputValidator();
  TokenChecker tokenChecker = new TokenChecker();
  DatabaseAccessor DBA = new DatabaseAccessor();


  public String handleAddingCoins(String securityToken, String userCoinRequest) {

    boolean isSecurityTokenFormatValid = inputValidator.handleSecurityTokenValidation(securityToken);
    boolean isRequestAuthorized = tokenChecker.checkSecurityToken(securityToken);

    if(isSecurityTokenFormatValid == false || isRequestAuthorized == false)
      return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    User user = new User(securityToken);
    



    // set user info and request




    // update user info

    // validate request
    // check for existing coins in DB via for loop in factory
    // create coin(s) in CoinFactory and load into User, current coin list
    // loop thru current coins and add new ones as necessary
    // update DB
    // return JSON of user's coins to client

    return "Good";

  }

  public void handleGettingCoins() {



  }

  public void handleUpdatingCoins() {





  }

  public void handleDeletingCoins() {



  }

  // public void handleCheckUserExists(String securityToken) {
  //   boolean isSecurityTokenFormatValid = inputValidator.handleSecurityTokenValidation(securityToken);
  //
  //   if(isSecurityTokenFormatValid == false)
  //     return resultCodes.ERROR_REQUEST_UNAUTHORIZED;
  //
  //   User user = new User(securityToken);
  //   DBA.checkUserExists(user);
  //
  // }






}
