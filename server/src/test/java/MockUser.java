import UserPortfolioManagement.User;

public class MockUser extends User {

  // ctor
  public MockUser(String username, String userId, String authToken, double portfolioValueUsd) {
    super("");
    this.setInfo(username, userId);
    this.setAuthToken(authToken);
    this.portfolioValueUsd = portfolioValueUsd;
  }





}
