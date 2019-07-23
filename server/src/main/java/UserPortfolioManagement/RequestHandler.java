package UserPortfolioManagement;

public class RequestHandler {

  ResultCodes resultCodes = new ResultCodes();
  InputValidator inputValidator = new InputValidator();


  public void handleAddingCoin(String userSecurityToken) {

    boolean isSecurityTokenFormatValid = inputValidator.handleSecurityTokenValidation(userSecurityToken);

    if(isSecurityTokenFormatValid == false)
      return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    User user = new User(userSecurityToken);
    boolean isRequestAuthorized = user.authorizeRequest();

    if(isRequestAuthorized == false)
      return resultCodes.ERROR_REQUEST_UNAUTHORIZED;






    // update user info

    // validate request
    // check for existing coins in DB via for loop in factory
    // create coin(s) in CoinFactory and load into User, current coin list
    // loop thru current coins and add new ones as necessary
    // update DB
    // return JSON of user's coins to client

  }

  public void handleUpdatingPortfolio() {





  }

  public void handleDeletingCoin() {



  }






}
