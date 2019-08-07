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

  private static final RequestHandler requestHandler = new RequestHandler();
  private static final IndexResponseDecider IRD = new IndexResponseDecider();

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

      response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
      response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
    });
  }

  public static void main(String[] args) {

    initServer();

    post("/addCoinsToPortfolio", (request, response) -> {
      String authToken   = request.cookie("auth_token");
      String jsonRequest = request.body();
      String resultCode = requestHandler.handleAddCoins(authToken, jsonRequest);
      String indexResponse = IRD.determineAddCoinsResponse(resultCode);
      return indexResponse;
    });

    get("/getPortfolio", (request, response) -> {
      String authToken   = request.cookie("auth_token");
      // would be returning JSON
      String resultCode = requestHandler.handleGetCoins(authToken);
      //String indexResponse = IRD.determineAddCoinsResponse(resultCode);

      System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
      System.out.println("Auth Token: " + authToken);
      System.out.println("INDEX RESPONSE: " + resultCode);

      return resultCode;
    });

    get("/checkUserExists/", "application/json", (request, response) -> {
      String userId = request.queryParams("userId");
      boolean doesUserExist = requestHandler.handleCheckUserExists(userId);
      return doesUserExist;
    });

  }

}
