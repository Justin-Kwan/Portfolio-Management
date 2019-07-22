// only use "this" for var reassignment
package UserPortfolioManagement;
import java.util.ArrayList;

public class User {


  private String username;
  private String userId;
  private String securityToken;
  private double portfolioValueUsd;
  private ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User(String username, String userId, String securityToken) {
    this.updateUsername(username);
    this.updateUserId(userId);
    this.updateSecurityToken(securityToken);
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

  public void addCoin(Coin coin) {
    (this.coins).add(coin);
    System.out.println("COIN VAR2 " + coins.get(0).getHoldingValueUsd());
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

  /*test!!*/
  public double getPortfolioValueUsd() {
    return portfolioValueUsd;
  }

  // public boolean authenticateRequest() {
  //
  //   // JWT auth logic here
  //
  // }

  /*test!!*/
  public void calculatePortfolioValue() {
    for(int i = 0; i < coins.size(); i++) {
      double currentCoinHoldingValue = coins.get(i).getHoldingValueUsd();
      this.portfolioValueUsd += currentCoinHoldingValue;
    }
  }


}
