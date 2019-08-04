package UserPortfolioManagement;
import java.util.ArrayList;

public class User {

  protected String username;
  protected String userId;
  protected String authToken;
  protected String jsonRequest;
  protected double portfolioValueUsd = 0;
  protected ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User(String authToken) {
    this.setAuthToken(authToken);
  }

  public void setInfo(String username, String userId, String jsonRequest) {
    this.setUsername(username);
    this.setUserId(userId);
    this.setJsonRequest(jsonRequest);
  }

  protected void setUsername(String username) {
    this.username = username;
  }

  protected void setUserId(String userId) {
    this.userId = userId;
  }

  protected void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  protected void setJsonRequest(String jsonRequest) {
    this.jsonRequest = jsonRequest;
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

  public String getJsonRequest() {
    return jsonRequest;
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
