package UserPortfolioManagement;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;

/**
 *  factory class responsible for creating the coin objects in the user's coin
 *  collection
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class CoinFactory {

  DatabaseAccessor DBA = new DatabaseAccessor();
  Gson gson = new Gson();

  public void createUserCoinCollection(User user) {
    ArrayList<Coin> dbCoins      = new ArrayList<Coin>();
    ArrayList<Coin> requestCoins = new ArrayList<Coin>();
    ArrayList<Coin> mergedCoins  = new ArrayList<Coin>();

    boolean doesUserExist = user.getStatus();

    if(doesUserExist) dbCoins = getCoinsFromLocation(user, "DATABASE");
    requestCoins = getCoinsFromLocation(user, "JSON_REQUEST");

    mergedCoins = mergeCoins(dbCoins, requestCoins);
    loadCoinsToUser(user, mergedCoins);
  }

  private ArrayList<Coin> getCoinsFromLocation(User user, String coinLocation) {
    ArrayList<Coin> coins = new ArrayList<Coin>();
    JSONArray jsonCoins   = new JSONArray();

    if(coinLocation == "DATABASE") {
      DBA.createConnection();
      jsonCoins = DBA.selectUserCoins(user);
    }
    else if(coinLocation == "JSON_REQUEST") {
      JSONObject jsonCoinsObj = new JSONObject(user.getJsonRequest());
      jsonCoins = jsonCoinsObj.getJSONArray("coins");
    }

    for(int i = 0; i < jsonCoins.length(); i++) {
      Coin coin = gson.fromJson(jsonCoins.get(i).toString(), Coin.class);
      coins.add(coin);
    }

    return coins;
  }

  private ArrayList<Coin> mergeCoins(ArrayList<Coin> dbCoins, ArrayList<Coin> requestCoins) {

    for(Coin currentRequestCoin: requestCoins) {
      // assume each request coin is unique until proven otherwise
      boolean isCoinUnique = true;

      for(Coin currentDbCoin: dbCoins) {

        if(currentRequestCoin.getTicker().equals(currentDbCoin.getTicker())) {
          currentDbCoin.incrementAmount(currentRequestCoin.getAmount());
          isCoinUnique = false;
          break;
        }

      }
      if(isCoinUnique) dbCoins.add(currentRequestCoin);
    }
    // all coins merged into dbCoins
    return dbCoins;
  }

  private void loadCoinsToUser(User user, ArrayList<Coin> mergedCoins) {
    for(Coin currentCoin: mergedCoins) {
      user.addCoin(currentCoin);
    }
  }

}
