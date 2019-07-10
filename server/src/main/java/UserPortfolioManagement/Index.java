package UserPortfolioManagement;
import static spark.Spark.*;

public class Index {
    public static void main(String[] args) {

      get("/", (request, response) -> {
        return "Hey!";
      });

      get("/", (request, response) -> {
        return "Hey!";
      });


    }
}
