package UserPortfolioManagement;
import java.util.ArrayList;

public class User {

  protected String username;
  protected String userId;
  protected String securityToken;
  protected double portfolioValueUsd = 0;
  protected ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User(String securityToken) {
    this.updateSecurityToken(securityToken);
  }

  protected void updateUsername(String username) {
    this.username = username;
  }

  protected void updateUserId(String userId) {
    this.userId = userId;
  }

  /* test!! */
  public void updateInfo(String username, String userId) {
    this.updateUsername(username);
    this.updateUserId(userId);
  }

  protected void updateSecurityToken(String securityToken) {
    this.securityToken = securityToken;
  }

  public void addCoin(Coin coin) {
    (this.coins).add(coin);
  }

  public String getUsername() {
    return username;
  }

  public String getUserId() {
    return userId;
  }

  public String getSecurityToken() {
    return securityToken;
  }

  public ArrayList getCoins() {
    return coins;
  }

  public double getPortfolioValueUsd() {
    return portfolioValueUsd;
  }

  public void calculatePortfolioValue() {
    this.portfolioValueUsd = 0;

    for(int currentCoin = 0; currentCoin < coins.size(); currentCoin++) {
      double currentCoinHoldingValue = coins.get(currentCoin).getHoldingValueUsd();
      this.portfolioValueUsd += currentCoinHoldingValue;
    }
    
  }

}
