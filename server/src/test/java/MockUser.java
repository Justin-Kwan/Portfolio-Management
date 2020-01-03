import UserPortfolioManagement.User;

public class MockUser extends User {

  // ctor
  public MockUser(String authToken, String userId, Boolean doesUserExist, String requestJson, double portfolioValueUsd) {
    super(authToken, userId, doesUserExist, requestJson);
    this.portfolioValueUsd = portfolioValueUsd;
  }

}
