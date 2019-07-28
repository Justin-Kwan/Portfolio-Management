package UserPortfolioManagement;
import static spark.Spark.*;
import spark.Filter;

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
    });
  }

  public static void main(String[] args) {

    initServer();

    post("/createPortfolio", (request, response) -> {
      String authToken = request.cookie("auth_token");
      String jsonRequest = request.body();

      System.out.println("AUTH TOKEN: " + authToken);
      System.out.println("JSON REQUEST: " + jsonRequest);

      // requestHandler.handleAddCoins(authToken, "");
      return "GET" + authToken;
    });

    get("/checkUserExists/", "application/json", (request, response) -> {
      String userId = request.queryParams("userId");
      boolean doesUserExist = requestHandler.handleCheckUserExists(userId);
      return doesUserExist;
    });

  }



}
