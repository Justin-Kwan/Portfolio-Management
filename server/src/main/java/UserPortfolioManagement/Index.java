package UserPortfolioManagement;
import static spark.Spark.*;
import spark.Filter;

public class Index {

  private static final String LOCAL_HOST_IP_ADDRESS = "127.0.0.1";
  private static final int PORT_NUMBER = 8001;

  private static void initServer() {
    ipAddress(LOCAL_HOST_IP_ADDRESS);
    port(PORT_NUMBER);
    System.out.println("Backend server started at " + LOCAL_HOST_IP_ADDRESS + ":" + PORT_NUMBER + "...");
  }

  private static void enableCors() {
    after((Filter) (request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
    });
  }

  public static void main(String[] args) {

    initServer();
    enableCors();

    post("/createPortfolio", (request, response) -> {

      RequestHandler requestHandler = new RequestHandler();
      String securityToken = request.cookie("security_token");
      String jsonRequest = request.body();


      System.out.println("SECURITY TOKEN: " + securityToken);
      System.out.println("JSON REQUEST: " + jsonRequest);

      // requestHandler.handleAddingCoins(securityToken, "");
      return "GET" + securityToken;

    });

  }

}
