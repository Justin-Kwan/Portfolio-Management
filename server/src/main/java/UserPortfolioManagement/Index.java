package UserPortfolioManagement;
import static spark.Spark.*;
import spark.Filter;

/**
 *  server API routes for user cryptocurrency portfolio management service
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class Index {

  private static final String LOCAL_HOST = "127.0.0.1";
  private static final int    PORT       = 8001;

  private static RequestHandler requestHandler = new RequestHandler();

  private static void initServer() {
    ipAddress(LOCAL_HOST);
    port(PORT);
    enableCors();
    System.out.println("Backend server started at " + LOCAL_HOST + ":" + PORT + "...");
  }

  private static void enableCors() {
    after((Filter) (request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Credentials", "true");
    });
  }

  public static void main(String[] args) {

    initServer();

    post("/addCoinsToPortfolio", (request, response) -> {
      String authToken   = request.cookie("auth_token");
      String jsonRequest = request.body();

      System.out.println("AUTH TOKEN: " + authToken);
      System.out.println("JSON REQUEST: " + jsonRequest);

      String result = requestHandler.handleAddCoins(authToken, jsonRequest);

      System.out.println("RESULT: " + result);

      return result;
    });

    get("/checkUserExists/", "application/json", (request, response) -> {
      String userId = request.queryParams("userId");
      boolean doesUserExist = requestHandler.handleCheckUserExists(userId);
      return doesUserExist;
    });

  }



}
