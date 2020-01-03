package UserPortfolioManagement;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 *  class responsible for handling interpreted client/user portfolio requests
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class RequestHandler {

  private final ResultCodes      resultCodes    = new ResultCodes();
  private final JsonChecker      jsonChecker    = new JsonChecker();
  private final TokenChecker     tokenChecker   = new TokenChecker();
  private final CoinFactory      coinFactory    = new CoinFactory();
  private final DatabaseAccessor DBA            = new DatabaseAccessor();
  private final RemoteTokenApi   remoteTokenApi = new RemoteTokenApi();

  private final int USERNAME = 0;
  private final int USER_ID  = 1;

  Gson gson = new Gson();






  /**
   * add coin process is single threaded
   */
  public synchronized String handleAddCoins(String authToken, String requestCoinsJson) {
    boolean isRequestAuthorized = tokenChecker.checkAuthTokenValid(authToken);
    if(!isRequestAuthorized) return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    boolean isJsonRequestValid = jsonChecker.checkJsonRequestValid(requestCoinsJson);
    if(!isJsonRequestValid) return resultCodes.ERROR_JSON_REQUEST_INVALID;

    String[] userInfoPayload = tokenChecker.getAuthTokenInfo(authToken);
    String userId            = userInfoPayload[0];
    DBA.createConnection();
    boolean doesUserExist    = DBA.checkUserExists(userId);

    User user = new User(authToken, userId, doesUserExist, requestCoinsJson);
    ArrayList<Coin> coins = coinFactory.createUserCoinCollection(user);
    user.setCoins(coins);

    if(user.getStatus()) DBA.updateUser(user);
    else DBA.insertNewUser(user);
    DBA.closeConnection();

    return resultCodes.SUCCESS;
  }

  /*TEST!!*/
  // todo: if user DNE yet, return empty
  public String handleGetCoins(String authToken) {
    boolean isRequestAuthorized = tokenChecker.checkAuthTokenValid(authToken);
    if(!isRequestAuthorized) return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    String[] userInfoPayload = tokenChecker.getAuthTokenInfo(authToken);
    String username          = userInfoPayload[USERNAME];
    String userId            = userInfoPayload[USER_ID];

    System.out.println("User id: " + userId);

    DBA.createConnection();
    User user = DBA.selectUser(userId);
    DBA.closeConnection();

    user.calculateCoinHoldingValues();
    user.calculatePortfolioValue();

    String jsonUser = gson.toJson(user);
    return jsonUser;
  }

  public boolean handleCheckUserExists(String userId) {
    DBA.createConnection();
    boolean doesUserExist = DBA.checkUserExists(userId);
    DBA.closeConnection();
    return doesUserExist;
  }

}
