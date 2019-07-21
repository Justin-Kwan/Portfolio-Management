package UserPortfolioManagement;
import static spark.Spark.*;

public class Index {
    public static void main(String[] args) {

      get("/", (request, response) -> {

        RequestHandler requestHandler = new RequestHandler();

        String userSecurityToken = request.cookie("security_token");

        requestHandler.handleAddingCoin(userSecurityToken);


          System.out.println("***" + userSecurityToken);
          return "GET" + userSecurityToken;

      });


    }
}
