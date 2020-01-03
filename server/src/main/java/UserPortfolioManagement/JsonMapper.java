/**
 * utility class that maps User and Coin objects to JSON format for DB storage
 * and responses to client
 */

package UserPortfolioManagement;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonMapper {

  public JSONObject mapUserJsonForDb(User user) {
    ArrayList<Coin> coins = user.getCoins();
    JSONArray coinsJsonMap = mapCoinsJsonForDb(coins);

    JSONObject userJsonMap = new JSONObject();
    userJsonMap.put("user_id", user.getUserId());
    userJsonMap.put("coins", coinsJsonMap);
    return userJsonMap;
  }

  protected JSONArray mapCoinsJsonForDb(ArrayList<Coin> coins) {
    JSONArray coinsJsonMap = new JSONArray();

    for(int i = 0; i < coins.size(); i++) {
      JSONObject coinJson = new JSONObject();
      coinJson.put("coin_ticker", coins.get(i).getTicker());
      coinJson.put("coin_amount", coins.get(i).getAmount());
      coinsJsonMap.put(coinJson);
    }

    return coinsJsonMap;
  }

  // TEST!
  public JSONObject mapResponseJsonForClient(User user, String responseString, Int responseCode, String route) {
    JSONObect responseJson = new JSONObject();

    if(route == "handleAddCoins") {
      responseJson.addProperty("response_string", responseString);
      responseJson.addProperty("response_code", responseCode);
    }
    else if(route == "handleGetCoins") {
      responseJson.addProperty("response_string", responseString);
      responseJson.addProperty("response_code", responseCode);
      responseJson.addProperty("coins", responseCode);
    }
    return responseJson;
  }

  // TEST!
  public JSONObject mapRequestJsonForAuthServer(String authToken) {
    JSONObect requestJson = new JSONObject();
    requestJson.addProperty('crypto_cost_session', authToken);
    return requestJson;
  }





}
