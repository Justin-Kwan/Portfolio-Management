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
    ArrayList<Coin> coins = new ArrayList<Coin>();

    if(doesUserExist) {
      coins = addCoinsToCollectionFromDb(user, coins);
    }

    //coins = addCoinsToCollectionFromRequest(coins);

    for(int currentCoin = 0; currentCoin < coins.size(); currentCoin++) {
      user.addCoin(coins.get(currentCoin));
    }

  }

  private ArrayList<Coin> addCoinsToCollectionFromDb(User user, ArrayList<Coin> coins) {
    DBA.createConnection();
    JSONArray coinsJsonArray = DBA.selectUserCoins(user);

    for(int currentJsonCoin = 0; currentJsonCoin < coinsJsonArray.length(); currentJsonCoin++) {

      // convert JSON object to Coin type object
      Coin coin = gson.fromJson(coinsJsonArray.get(currentJsonCoin).toString(), Coin.class);
      coins.add(coin);
    }

    return coins;
  }

  // // here need to check for duplicate coins
  // private ArrayList<Coin> addCoinsToCollectionFromRequest(ArrayList<Coin> coins) {
  //
  // }

  private Coin getNewCoin(String coinTicker, double coinAmount) {
    Coin coin = new Coin(coinTicker, coinAmount);
    return coin;
  }

}
