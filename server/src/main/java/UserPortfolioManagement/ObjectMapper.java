package UserPortfolioManagement;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ObjectMapper {

  public User mapUserObjForApp(JSONObject userJson) {
    String userId = userJson.getString("user_id");

    JSONArray coinsJson = userJson.getJSONArray("coins");

    ArrayList<Coin> coins = mapCoinsObjForApp(coinsJson);

    User user = new User("", userId, null, "");
    user.setCoins(coins);

    return user;
  }

  protected ArrayList<Coin> mapCoinsObjForApp(JSONArray coinsJson) {
    ArrayList<Coin> coins = new ArrayList<Coin>();

    for(int i = 0; i < coinsJson.length(); i++) {
      String coinTicker;
      double coinAmount;

      coinTicker = coinsJson.getJSONObject(i).getString("coin_ticker");
      coinAmount = coinsJson.getJSONObject(i).getDouble("coin_amount");

      Coin coin = new Coin(coinTicker, coinAmount);
      coins.add(coin);
    }

    return coins;
  }

  // TEST!
  public String[] mapTokenServerResponseObjForApp(JSONObject authServerResponse) {

  }

}
