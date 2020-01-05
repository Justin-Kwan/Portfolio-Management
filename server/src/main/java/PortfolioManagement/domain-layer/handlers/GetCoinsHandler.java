package PortfolioManagement;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  class responsible for handling interpreted client/user get coins portfolio
 *  requests
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class GetCoinsHandler {

	private final static DatabaseAccessor DBA = new DatabaseAccessor();
	private final static RemoteTokenApi remoteTokenApi = new RemoteTokenApi();
	private final static JsonMapper jsonMapper = new JsonMapper();
	private final static Response response = new Response();

	public JSONObject handleGetCoins(String authToken) {
		Object[] authTokenPayload = remoteTokenApi.fetchAuthCheck(authToken);
		boolean isUserAuthorized = (Boolean) authTokenPayload[0];
		String userId = (String) authTokenPayload[1];

		if (!isUserAuthorized) {
			Response response = new Response.Builder()
      .withResponseString("request unauthorized")
      .withResponseCode(401)
      .withCoins(false)
      .build();

			return jsonMapper.mapResponseJsonForClient(response);
		}

		DBA.createConnection();
		boolean doesUserExist = DBA.checkUserExists(userId);
		DBA.closeConnection();

		if (!doesUserExist) {
			Response response = new Response.Builder()
      .withResponseString("no coins to get")
      .withResponseCode(400)
      .withCoins(false)
      .build();

			return jsonMapper.mapResponseJsonForClient(response);
		}

		DBA.createConnection();
		User user = DBA.selectUser(userId);
		DBA.closeConnection();

		user.calculateCoinHoldingValues();
		user.calculatePortfolioValue();

		Response response = new Response.Builder()
    .withUser(user)
    .withResponseString("coins get successful")
    .withResponseCode(200)
    .withCoins(true)
    .build();

		return jsonMapper.mapResponseJsonForClient(response);
	}

}
