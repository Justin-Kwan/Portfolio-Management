package PortfolioManagement;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  class responsible for handling interpreted client/user add coins portfolio
 *  requests
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class AddCoinsHandler {

	private final static JsonChecker jsonChecker = new JsonChecker();
	private final static CoinFactory coinFactory = new CoinFactory();
	private final static DatabaseAccessor DBA = new DatabaseAccessor();
	private final static RemoteTokenApi remoteTokenApi = new RemoteTokenApi();
	private final static JsonMapper jsonMapper = new JsonMapper();
	private final static Response response = new Response();


	public AddCoinsHandler() {
		this.DBA.createConnection();
	}

	/**
         * add coin process is single threaded so database access is qeued
         */
	public synchronized JSONObject handleAddCoins(String authToken, String requestCoinsJson) {
		Object[] authTokenPayload = remoteTokenApi.fetchAuthCheck(authToken);
		boolean isUserAuthorized = (Boolean) authTokenPayload[0];
		String userId = (String) authTokenPayload[1];
		boolean isJsonRequestValid = jsonChecker.checkJsonRequestValid(requestCoinsJson);

		if (!isUserAuthorized) {
			Response response = new Response.Builder()
      			.withResponseString("request unauthorized")
      			.withResponseCode(401)
      			.withCoins(false)
      			.build();

			return jsonMapper.mapResponseJsonForClient(response);
		}

		if (!isJsonRequestValid) {
			Response response = new Response.Builder()
      			.withResponseString("request invalid")
      			.withResponseCode(400)
      			.withCoins(false)
      			.build();

			return jsonMapper.mapResponseJsonForClient(response);
		}

		boolean doesUserExist = DBA.checkUserExists(userId);

		User user = new User(authToken, userId, doesUserExist, requestCoinsJson);

		ArrayList <Coin> coins = coinFactory.createUserCoinCollection(user);
		user.setCoins(coins);

		if (user.getStatus()) DBA.updateUser(user);
		else DBA.insertNewUser(user);

		Response response = new Response.Builder()
    		.withResponseString("coins add successful")
    		.withResponseCode(201)
    		.withCoins(false)
    		.build();

		return jsonMapper.mapResponseJsonForClient(response);
	}

}
