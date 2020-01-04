package UserPortfolioManagement;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 *  class responsible for handling interpreted client/user portfolio requests
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class RequestHandler {

  private final static ResultCodes      resultCodes    = new ResultCodes();
  private final static JsonChecker      jsonChecker    = new JsonChecker();
  private final static TokenChecker     tokenChecker   = new TokenChecker();
  private final static CoinFactory      coinFactory    = new CoinFactory();
  private final static DatabaseAccessor DBA            = new DatabaseAccessor();
  private final static RemoteTokenApi   remoteTokenApi = new RemoteTokenApi();
  private final static JsonMapper       jsonMapper     = new JsonMapper();

  private final static boolean          WITH_COINS     = true;
  private final static boolean          WITHOUT_COINS  = false;

  /**
   * add coin process is single threaded
   */
  public synchronized JSONObject handleAddCoins(String authToken, String requestCoinsJson) {
    Object[] authTokenPayload   = remoteTokenApi.fetchAuthCheck(authToken);
    boolean  isUserAuthorized   = (Boolean) authTokenPayload[0];
    String   userId             = (String) authTokenPayload[1];
    boolean  isJsonRequestValid = jsonChecker.checkJsonRequestValid(requestCoinsJson);

    System.out.println("IS USER AUTHORIZED? " + isUserAuthorized);

    if(!isUserAuthorized) {
      JSONObject responseJson = jsonMapper.mapResponseJsonForClient(null, "request unauthorized", 401, WITHOUT_COINS);
      return responseJson;
    }

    if(!isJsonRequestValid) {
      JSONObject responseJson = jsonMapper.mapResponseJsonForClient(null, "request invalid", 400, WITHOUT_COINS);
      return responseJson;
    }

    DBA.createConnection();
    boolean doesUserExist = DBA.checkUserExists(userId);

    User user = new User(authToken, userId, doesUserExist, requestCoinsJson);
    ArrayList<Coin> coins = coinFactory.createUserCoinCollection(user);
    user.setCoins(coins);

    if(user.getStatus()) DBA.updateUser(user);
    else DBA.insertNewUser(user);
    DBA.closeConnection();

    JSONObject responseJson = jsonMapper.mapResponseJsonForClient(null, "coins add successful", 201, WITHOUT_COINS);
    return responseJson;
  }

  public JSONObject handleGetCoins(String authToken) {
    Object[] authTokenPayload   = remoteTokenApi.fetchAuthCheck(authToken);
    boolean  isUserAuthorized   = (Boolean) authTokenPayload[0];
    String   userId             = (String) authTokenPayload[1];

    System.out.println("IS USER AUTHORIZED? " + isUserAuthorized);

    if(!isUserAuthorized) {
      JSONObject responseJson = jsonMapper.mapResponseJsonForClient(null, "request unauthorized", 401, WITHOUT_COINS);
      return responseJson;
    }

    System.out.println("User id: " + userId);

    DBA.createConnection();
    boolean doesUserExist = DBA.checkUserExists(userId);
    DBA.closeConnection();

    if(!doesUserExist) {
      JSONObject responseJson = jsonMapper.mapResponseJsonForClient(null, "no coins to get", 400, WITHOUT_COINS);
      return responseJson;
    }

    DBA.createConnection();
    User user = DBA.selectUser(userId);
    DBA.closeConnection();

    user.calculateCoinHoldingValues();
    user.calculatePortfolioValue();
    ArrayList<Coin> coins = user.getCoins();

    JSONObject responseJson = jsonMapper.mapResponseJsonForClient(coins, "coins get successful", 200, WITH_COINS);
    return responseJson;
  }

  public boolean handleCheckUserExists(String userId) {
    DBA.createConnection();
    boolean doesUserExist = DBA.checkUserExists(userId);
    DBA.closeConnection();
    return doesUserExist;
  }

}
