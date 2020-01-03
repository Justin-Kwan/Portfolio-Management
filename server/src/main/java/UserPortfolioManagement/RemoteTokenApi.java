package UserPortfolioManagement;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RemoteTokenApi {

  JsonMapper jsonMapper = new JsonMapper();
  ObjectMapper objectMapper = new ObjectMapper();

  public String[] requestAuthCheck(String authToken) {

    JSONObject requestJson = jsonMapper.mapRequestJsonForAuthServer(authToken);

    HttpResponse<JsonNode> authServerResponse = Unirest.post("http://localhost:5000/authorizeUser")
          .header("accept", "application/json")
          .body(requestJson)
          .asJson();

    objectMapper.mapTokenServerResponseObjForApp(authServerResponse);

    JSONObject = response.getBody().getObject();

    return authServerResponse;


  }

}









}
