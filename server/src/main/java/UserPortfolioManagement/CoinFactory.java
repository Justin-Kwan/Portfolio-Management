package UserPortfolioManagement;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *  factory class responsible for creating the coin objects in the user's coin
 *  collection
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class CoinFactory {

  private static DatabaseAccessor DBA = new DatabaseAccessor();
  private static ObjectMapper objectMapper = new ObjectMapper();

  public ArrayList<Coin> createUserCoinCollection(User user) {
    ArrayList<Coin> dbCoins      = new ArrayList<Coin>();
    ArrayList<Coin> requestCoins = new ArrayList<Coin>();
    ArrayList<Coin> mergedCoins  = new ArrayList<Coin>();

    boolean doesUserExist = user.getStatus();

    if(doesUserExist)
      dbCoins = this.getCoinsFromLocation(user, "DATABASE");

    requestCoins = this.getCoinsFromLocation(user, "JSON_REQUEST");

    mergedCoins = this.mergeCoins(dbCoins, requestCoins);
    return mergedCoins;
  }

  private ArrayList<Coin> getCoinsFromLocation(User user, String coinLocation) {
    ArrayList<Coin> coins = new ArrayList<Coin>();

    System.out.println("*************COINS JSON***********: " + user.getJsonRequest());
    System.out.println("*************COIN LOCATION*****"  + coinLocation);

    if(coinLocation == "DATABASE") {
      DBA.createConnection();
      coins = DBA.selectUserCoins(user);
    }
    else if(coinLocation == "JSON_REQUEST") {
      JSONObject coinsJsonObj = new JSONObject(user.getJsonRequest());
      JSONArray coinsJson = coinsJsonObj.getJSONArray("coins");
      coins = objectMapper.mapCoinsObjForApp(coinsJson);
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
    return dbCoins;
  }

}
