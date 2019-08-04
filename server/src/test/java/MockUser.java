import UserPortfolioManagement.User;

public class MockUser extends User {

  // ctor
  public MockUser(String username, String userId, String authToken, String jsonRequest, double portfolioValueUsd) {
    super("");
    this.setInfo(username, userId, jsonRequest);
    this.setAuthToken(authToken);
    this.portfolioValueUsd = portfolioValueUsd;
  }

}
