package PortfolioManagement;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ObjectMapper {

	public User mapUserObjForApp(JSONObject userJson) {
		String userId = userJson.getString("user_id");

		JSONArray coinsJson = userJson.getJSONArray("coins");
		ArrayList < Coin > coins = mapCoinsObjForApp(coinsJson);

		User user = new User("", userId, null, "");
		user.setCoins(coins);

		return user;
	}

	public ArrayList < Coin > mapCoinsObjForApp(JSONArray coinsJson) {
		ArrayList < Coin > coins = new ArrayList < Coin > ();

		for (int i = 0; i < coinsJson.length(); i++) {
			String coinTicker;
			double coinAmount;

			coinTicker = coinsJson.getJSONObject(i).getString("coin_ticker");
			coinAmount = coinsJson.getJSONObject(i).getDouble("coin_amount");

			Coin coin = new Coin(coinTicker, coinAmount);
			coins.add(coin);
		}

		return coins;
	}

	public Object[] mapTokenServerResponseObjForApp(JSONObject responseJson) {
		boolean isUserAuthorized = responseJson.getBoolean("is user authorized");
		String userId = responseJson.getString("user id");
		Object[] authTokenPayload = {
			isUserAuthorized,
			userId
		};
		return authTokenPayload;
	}

}
