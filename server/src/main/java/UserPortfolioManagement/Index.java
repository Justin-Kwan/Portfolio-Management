package UserPortfolioManagement;
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

  private static final RequestHandler reqHandler = new RequestHandler();
  private static final IndexResponseDecider IRD = new IndexResponseDecider();

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
      System.out.println("Auth Token: " + req.cookie("crypto_cost_session"));
      String requestCoinsJson = req.body();

      System.out.println("Coins: " + requestCoinsJson);

      String responseJson = reqHandler.handleAddCoins(authToken, requestCoinsJson);

      String indexResponse = IRD.determineAddCoinsResponse(responseJson);
      return indexResponse;
    });

    // todo: change name to getCoins
    get("/getCoins", "application/json", (req, res) -> {

      System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
      System.out.println("Auth Token: " + req.cookie("crypto_cost_session"));

      String authToken = req.cookie("crypto_cost_session");

      // would be returning JSON
      String responseJson = reqHandler.handleGetCoins(authToken);

      System.out.println("KKKKKKKK");
      System.out.println("^^^^^ " + responseJson + " ^^^^^^");

      String indexResponse = IRD.determineAddCoinsResponse(responseJson);

      System.out.println("INDEX RESPONSE: " + responseJson);

      return responseJson;
    });

    get("/checkUserExists/", "application/json", (req, res) -> {
      String userId = req.queryParams("userId");
      boolean doesUserExist = reqHandler.handleCheckUserExists(userId);
      return doesUserExist;
    });

  }

}
