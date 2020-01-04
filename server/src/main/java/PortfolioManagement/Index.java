package PortfolioManagement;
import static spark.Spark.*;
import spark.Filter;
import org.json.JSONObject;
import org.json.*;

/**
 *  server API routes for user cryptocurrency portfolio management service
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class Index {

  private static final String LOCAL_HOST = "127.0.0.1";
  private static final int    PORT       = 8001;

  private static final RequestHandler requestHandler = new RequestHandler();

  private static void initServer() {
    ipAddress(LOCAL_HOST);
    port(PORT);
    enableCors();
    System.out.println("Backend server started at " + LOCAL_HOST + ":" + PORT + "...");
  }

  private static void enableCors() {
    after((Filter) (req, res) -> {
      res.header("Access-Control-Allow-Origin", "http://127.0.0.1:8000");
      res.header("Access-Control-Allow-Credentials", "true");
    });
  }

  public static void main(String[] args) {

    initServer();

    post("/addCoins", (req, res) -> {
      String authToken = req.cookie("crypto_cost_session");
      String requestCoinsJson = req.body();
      JSONObject responseJson = requestHandler.handleAddCoins(authToken, requestCoinsJson);
      System.out.println("Response: " + responseJson);
      return responseJson;
    });

    get("/getCoins", "application/json", (req, res) -> {
      System.out.println("Auth Token: " + req.cookie("crypto_cost_session"));

      String authToken = req.cookie("crypto_cost_session");

      JSONObject responseJson = requestHandler.handleGetCoins(authToken);
      System.out.println("INDEX RESPONSE: " + responseJson);

      return responseJson;
    });

    get("/checkUserExists/", "application/json", (req, res) -> {
      String userId = req.queryParams("userId");
      boolean doesUserExist = requestHandler.handleCheckUserExists(userId);
      return doesUserExist;
    });

  }

}
