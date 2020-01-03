package UserPortfolioManagement;
import java.util.ArrayList;

public class User {

  protected String  userId;
  protected String  authToken;
  protected String  requestJson;
  protected double  portfolioValueUsd = 0;
  protected Boolean doesUserExist;
  protected ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User(String authToken, String userId, Boolean doesUserExist, String requestJson) {
    this.setAuthToken(authToken);
    this.setUserId(userId);
    this.setStatus(doesUserExist);
    this.setJsonRequest(requestJson);
  }

  protected void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  protected void setUserId(String userId) {
    this.userId = userId;
  }

  protected void setStatus(Boolean doesUserExist) {
    this.doesUserExist = doesUserExist;
  }

  protected void setJsonRequest(String requestJson) {
    this.requestJson = requestJson;
  }

  public void setCoins(ArrayList<Coin> coins) {
    this.coins = coins;
  }

  public String getAuthToken() {
    return authToken;
  }

  public String getUserId() {
    return userId;
  }

  public Boolean getStatus() {
    return doesUserExist;
  }

  public String getJsonRequest() {
    return requestJson;
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
