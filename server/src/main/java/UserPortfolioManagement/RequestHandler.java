package UserPortfolioManagement;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 *  class responsible for handling interpreted client/user portfolio requests
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class RequestHandler {

  private final ResultCodes      resultCodes  = new ResultCodes();
  private final JsonChecker      jsonChecker  = new JsonChecker();
  private final TokenChecker     tokenChecker = new TokenChecker();
  private final CoinFactory      coinFactory  = new CoinFactory();
  private final DatabaseAccessor DBA          = new DatabaseAccessor();

  private final int USERNAME = 0;
  private final int USER_ID  = 1;

  Gson gson = new Gson();

  /*TEST!!*/
  public String handleAddCoins(String authToken, String jsonRequest) {
    boolean isRequestAuthorized = tokenChecker.checkAuthTokenValid(authToken);
    if(!isRequestAuthorized) return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    boolean isJsonRequestValid = jsonChecker.checkJsonRequestValid(jsonRequest);
    if(!isJsonRequestValid) return resultCodes.ERROR_JSON_REQUEST_INVALID;

    String[] userInfoPayload = tokenChecker.getAuthTokenInfo(authToken);
    String username          = userInfoPayload[USERNAME];
    String userId            = userInfoPayload[USER_ID];
    DBA.createConnection();
    boolean doesUserExist    = DBA.checkUserExists(userId);

    User user = new User(authToken, username, userId, doesUserExist, jsonRequest);
    coinFactory.createUserCoinCollection(user);

    if(doesUserExist) DBA.updateUser(user);
    else DBA.insertNewUser(user);
    DBA.closeConnection();

    return resultCodes.SUCCESS;
  }

  public String handleGetCoins(String authToken) {
    boolean isRequestAuthorized = tokenChecker.checkAuthTokenValid(authToken);
    if(!isRequestAuthorized) return resultCodes.ERROR_REQUEST_UNAUTHORIZED;

    String[] userInfoPayload = tokenChecker.getAuthTokenInfo(authToken);
    String username          = userInfoPayload[USERNAME];
    String userId            = userInfoPayload[USER_ID];

    JSONObject jsonUserObj = DBA.selectUser(userId);
    User user = gson.fromJson(jsonUserObj.toString(), User.class);
    user.calculateCoinHoldingValues();
    user.calculatePortfolioValue();

    String jsonUserStr = gson.toJson(user);
    return jsonUserStr.toString();
  }

  public void handleUpdateCoins() {
  }

  public void handleDeleteCoins() {
  }

  public boolean handleCheckUserExists(String userId) {
    DBA.createConnection();
    boolean doesUserExist = DBA.checkUserExists(userId);
    DBA.closeConnection();
    return doesUserExist;
  }

}
