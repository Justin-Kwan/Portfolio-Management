package UserPortfolioManagement;
import java.util.ArrayList;

public class User {

  protected String  username;
  protected String  userId;
  protected String  authToken;
  protected String  jsonRequest;
  protected double  portfolioValueUsd = 0;
  protected Boolean doesUserExist;
  protected ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User(String authToken, String username, String userId, Boolean doesUserExist, String jsonRequest) {
    this.setAuthToken(authToken);
    this.setUsername(username);
    this.setUserId(userId);
    this.setStatus(doesUserExist);
    this.setJsonRequest(jsonRequest);
  }

  protected void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  protected void setUsername(String username) {
    this.username = username;
  }

  protected void setUserId(String userId) {
    this.userId = userId;
  }

  protected void setStatus(Boolean doesUserExist) {
    this.doesUserExist = doesUserExist;
  }

  protected void setJsonRequest(String jsonRequest) {
    this.jsonRequest = jsonRequest;
  }

  public void addCoin(Coin coin) {
    (this.coins).add(coin);
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getUsername() {
    return username;
  }

  public String getUserId() {
    return userId;
  }

  public Boolean getStatus() {
    return doesUserExist;
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

  public void calculateCoinHoldingValues() {

    for(Coin currentCoin: coins) {
      currentCoin.fetchAndSetLatestPrice();
      currentCoin.calculateAndSetHoldingValue();
    }

  }

  public void calculatePortfolioValue() {
    this.portfolioValueUsd = 0;

    for(Coin currentCoin: coins) {
      double currentCoinHoldingValue = currentCoin.getHoldingValueUsd();
      this.portfolioValueUsd += currentCoinHoldingValue;
		}

  }

}
