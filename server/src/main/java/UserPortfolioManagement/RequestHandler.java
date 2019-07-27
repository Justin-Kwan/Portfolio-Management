package UserPortfolioManagement;

public class RequestHandler {

  ResultCodes resultCodes = new ResultCodes();
  InputValidator inputValidator = new InputValidator();
  TokenChecker tokenChecker = new TokenChecker();
  DatabaseAccessor DBA = new DatabaseAccessor();


  public String handleAddingCoins(String authToken, String userCoinRequest) {

    boolean isAuthTokenFormatValid = inputValidator.handleAuthTokenValidation(authToken);
    boolean isRequestAuthorized = tokenChecker.checkAuthToken(authToken);

    if(isAuthTokenFormatValid == false || isRequestAuthorized == false)
      return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    User user = new User(authToken);




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

  public void handleCheckUserExists(String authToken) {
    
    User user = new User(authToken);
    DBA.checkUserExists(user);

  }






}
