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

  public void createUserCoinCollection(User user, boolean doesUserExist) {
    ArrayList<Coin> dbCoins;
    ArrayList<Coin> requestCoins;


    if(doesUserExist) {
      dbCoins = getCoinsFromLocation(user, "DATABASE");
    }

    //requestCoins = getCoinsFromRequest(requestCoins);

    // for(int currentCoin = 0; currentCoin < coins.size(); currentCoin++) {
    //   user.addCoin(coins.get(currentCoin));
    // }

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

    for(int currentJsonCoin = 0; currentJsonCoin < jsonCoins.length(); currentJsonCoin++) {
      Coin coin = gson.fromJson(jsonCoins.get(currentJsonCoin).toString(), Coin.class);
      coins.add(coin);
    }

    return coins;
  }

  private ArrayList<Coin> mergeCoins(ArrayList<Coin> dbCoins, ArrayList<Coin> requestCoins) {

    for(int i = 0; i < requestCoins.size(); i++) {

      // assume each request coin is unique until proven otherwise
      boolean isCoinUnique = true;
      Coin currentRequestCoin = requestCoins.get(i);

      for(int j = 0; j < dbCoins.size(); j++) {

        Coin currentDbCoin = dbCoins.get(j);

        if(currentRequestCoin.getTicker().equals(currentDbCoin.getTicker())) {
          System.out.println(currentRequestCoin.getTicker() + "&" + currentDbCoin.getTicker() + "MATCH!");
          currentDbCoin.incrementAmount(currentRequestCoin.getAmount());
          isCoinUnique = false;
          break;
        }

      }

      if(isCoinUnique) {
        System.out.println("*UNIQUE COIN!*");
        dbCoins.add(currentRequestCoin);
      }

    }
    // all coins merged into dbCoins
    return dbCoins;
  }




}
