package UserPortfolioManagement;
import java.util.ArrayList;

public class User {

  protected String username;
  protected String userId;
  protected String authToken;
  protected double portfolioValueUsd = 0;
  protected ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User(String authToken) {
    this.setAuthToken(authToken);
  }

  protected void setUsername(String username) {
    this.username = username;
  }

  protected void setUserId(String userId) {
    this.userId = userId;
  }

  /* test!! */
  public void setInfo(String username, String userId) {
    this.setUsername(username);
    this.setUserId(userId);
  }

  protected void setAuthToken(String authToken) {
    this.authToken = authToken;
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

  public String getAuthToken() {
    return authToken;
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
