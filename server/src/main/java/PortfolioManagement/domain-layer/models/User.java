package PortfolioManagement;
import java.util.ArrayList;

public class User {

	private static String userId;
	private static String authToken;
	private static String requestJson;
	private static double portfolioValueUsd = 0;
	private static Boolean doesUserExist;
	private static ArrayList < Coin > coins = new ArrayList < Coin > ();

	public User(String authToken, String userId, Boolean doesUserExist, String requestJson) {
		this.setAuthToken(authToken);
		this.setUserId(userId);
		this.setStatus(doesUserExist);
		this.setJsonRequest(requestJson);
	}

	private void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	private void setUserId(String userId) {
		this.userId = userId;
	}

	private void setStatus(Boolean doesUserExist) {
		this.doesUserExist = doesUserExist;
	}

	private void setJsonRequest(String requestJson) {
		this.requestJson = requestJson;
	}

	public void setCoins(ArrayList < Coin > coins) {
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
		for (Coin currentCoin: coins) {
			currentCoin.fetchAndSetLatestPrice();
			currentCoin.calculateAndSetHoldingValue();
		}
	}

	public void calculatePortfolioValue() {
		this.portfolioValueUsd = 0;
		for (Coin currentCoin: coins) {
			double currentCoinHoldingValue = currentCoin.getHoldingValueUsd();
			this.portfolioValueUsd += currentCoinHoldingValue;
		}
	}

}
