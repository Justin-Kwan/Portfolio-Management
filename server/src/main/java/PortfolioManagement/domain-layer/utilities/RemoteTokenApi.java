package PortfolioManagement;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;

public class RemoteTokenApi {

	private static JsonMapper jsonMapper = new JsonMapper();
	private static ObjectMapper objectMapper = new ObjectMapper();

	public Object[] fetchAuthCheck(String authToken) {
		JSONObject requestJson = jsonMapper.mapRequestJsonForAuthServer(authToken);

		System.out.println("Auth token: " + authToken);

		HttpResponse < JsonNode > serverResponse = Unirest.post("http://localhost:5000/authorizeUser")
    .header("accept", "application/json")
    .body(requestJson)
    .asJson();

		JSONObject responseJson = serverResponse.getBody().getObject();
		Object[] authTokenPayload = objectMapper.mapTokenServerResponseObjForApp(responseJson);
		return authTokenPayload;
	}

}
