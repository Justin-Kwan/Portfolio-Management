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

  private static final String LOCAL_IP   = "127.0.0.1";
  private static final int    PORT       = 8001;

  private static final AddCoinsHandler addCoinsHandler = new AddCoinsHandler();
  private static final GetCoinsHandler getCoinsHandler = new GetCoinsHandler();

  private static void initServer() {
    ipAddress(LOCAL_IP);
    port(PORT);
    enableCors();
    System.out.println("Backend server started at " + LOCAL_IP + ":" + PORT + "...");
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
      JSONObject responseJson = addCoinsHandler.handleAddCoins(authToken, requestCoinsJson);
      return responseJson;
    });

    get("/getCoins", "application/json", (req, res) -> {
      System.out.println("Auth Token: " + req.cookie("crypto_cost_session"));
      String authToken = req.cookie("crypto_cost_session");
      JSONObject responseJson = getCoinsHandler.handleGetCoins(authToken);
      return responseJson;
    });
  }

}
