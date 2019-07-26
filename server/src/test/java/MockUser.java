import UserPortfolioManagement.User;

public class MockUser extends User {

  // ctor
  public MockUser(String username, String userId, String securityToken, double portfolioValueUsd) {
    super("");
    this.updateInfo(username, userId);
    this.updateSecurityToken(securityToken);
    this.portfolioValueUsd = portfolioValueUsd;
  }





}
