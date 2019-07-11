// only use "this" for var reassignment


public class User {


  private String username;
  private String userId;
  private String securityToken;
  private ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User() {

    this.updateUsername();
    this.updateUserId();
    this.updateSecurityToken();
    this.updateCoins();

  }

  private void updateUsername(String username) {
    this.username = username;
  }

  private void updateUserId(String userId) {
    this.userId = userId;
  }

  private void updateSecurityToken(String securityToken) {
    this.securityToken = securityToken;
  }

  public String getUsername() {
    return username;
  }

  public String getUserId() {
    return userId;
  }

  public ArrayList getCoins() {
    return coins;
  }

  public boolean authenticateRequest() {

    // JWT auth logic here

  }





  // private void incrementPortfolioValue(double coinHoldingValue) {
  //   portfolioValue += coinHoldingValue;
  // }

  // public double calculatePortfolioValue() {
  //
  // }


}
