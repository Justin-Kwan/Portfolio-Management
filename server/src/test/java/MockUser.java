import UserPortfolioManagement.User;

public class MockUser extends User {

  // ctor
  public MockUser(String authToken, String username, String userId, Boolean doesUserExist, String jsonRequest, double portfolioValueUsd) {
    super(authToken, username, userId, doesUserExist, jsonRequest);
    this.portfolioValueUsd = portfolioValueUsd;
  }

}
