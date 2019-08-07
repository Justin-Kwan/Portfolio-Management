import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.IndexResponseDecider;

public class IndexResponseDeciderTest {

  IndexResponseDecider IRD = new IndexResponseDecider();

  @Test
  public void test_determineAddCoinsResponse() {
    String indexResponse;

    indexResponse = IRD.determineAddCoinsResponse("SUCCESS");
    assertEquals("Coins successfully added", indexResponse);

    indexResponse = IRD.determineAddCoinsResponse("REQUEST_UNAUTHORIZED");
    assertEquals("Request is unauthorized", indexResponse);

    indexResponse = IRD.determineAddCoinsResponse("REQUEST_INVALID");
    assertEquals("Request is invalid", indexResponse);
  }

}
