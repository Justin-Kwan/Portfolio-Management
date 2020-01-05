import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.Test;
import org.junit.Rule;
import org.json.JSONObject;
import org.json.JSONArray;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import PortfolioManagement.AddCoinsHandler;
import PortfolioManagement.GetCoinsHandler;
import PortfolioManagement.DatabaseAccessor;
import PortfolioManagement.User;
import PortfolioManagement.Coin;


public class GetCoinsHandlerTest {

  GetCoinsHandler getCoinsHandler = new GetCoinsHandler();
  AddCoinsHandler addCoinsHandler = new AddCoinsHandler();

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(5000);

  /**
   * mock auth token server response on token check
   */
  public void setMockAuthServer(String mockAuthToken, String mockAuthServerResponse) {
    WireMock.reset();
    stubFor(post(urlEqualTo("/authorizeUser"))
          .withRequestBody(containing("\"crypto_cost_session\":" + "\"" + mockAuthToken + "\""))
          .willReturn(aResponse()
          .withBody(mockAuthServerResponse)));
  }

  private DatabaseAccessor beforeTest() {
    DatabaseAccessor DBA = new DatabaseAccessor();
    try {
      DBA.createConnection();
      DBA.clearDatabase();
    }
    catch(Exception error) {
      System.out.println(error);
    }
    return DBA;
  }

  private void afterTest(DatabaseAccessor DBA) {
    DBA.clearDatabase();
    DBA.closeConnection();
  }

  @Test
  public void test_handleGetCoins() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;

    Thread.sleep(4000);

    String mockGoodJsonRequest1 = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockGoodJsonRequest2 = "{\"coins\":[{\"coin_ticker\":\"NEO\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest1  = "{\"coins\":[{\"coin_ticker\":,\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest2  = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":2i3.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest3  = "{\"coins\":[{\"coin_ticker\":\"\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest4  = "{\"coins\":[{\"coin_ticker\":\"COINNOTHERE\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockGoodAuthToken1   = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsInVzZXIgaWQiOiJyYW5kb211c2VyMTIzIn0._ysaIs-QjPF4smD_LiNCAYruphEP-R5-Zg2VsVo4wXU";
    String mockBadAuthToken1    = "eyJ0eXAiOiJKV1QiLCJhbkGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";
    String mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";

    // successful get coins tests
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);

    JSONObject responseJsonAddCoins = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest1);
    JSONObject responseJsonGetCoins = getCoinsHandler.handleGetCoins(mockGoodAuthToken1);
    JSONArray coinsJson = responseJsonGetCoins.getJSONArray("coins");

    assertEquals("coins get successful", responseJsonGetCoins.getString("response_string"));
    assertEquals(200, responseJsonGetCoins.getInt("response_code"));
    assertTrue(0 <= responseJsonGetCoins.getDouble("portfolio_value_usd"));
    assertEquals(3, coinsJson.length(), 1e-8);
    assertEquals("BTC", coinsJson.getJSONObject(0).getString("coin_ticker"));
    assertEquals(123, coinsJson.getJSONObject(0).getDouble("coin_amount"), 1e-8);
    assertTrue(0 <= coinsJson.getJSONObject(0).getDouble("latest_coin_price"));
    assertTrue(0 <= coinsJson.getJSONObject(0).getDouble("coin_holding_value_usd"));
    assertEquals("NEO", coinsJson.getJSONObject(1).getString("coin_ticker"));
    assertEquals(23.41, coinsJson.getJSONObject(1).getDouble("coin_amount"), 1e-8);
    assertTrue(0 <= coinsJson.getJSONObject(1).getDouble("latest_coin_price"));
    assertTrue(0 <= coinsJson.getJSONObject(1).getDouble("coin_holding_value_usd"));
    assertEquals("BAT", coinsJson.getJSONObject(2).getString("coin_ticker"));
    assertEquals(3.41, coinsJson.getJSONObject(2).getDouble("coin_amount"), 1e-8);
    assertTrue(0 <= coinsJson.getJSONObject(2).getDouble("latest_coin_price"));
    assertTrue(0 <= coinsJson.getJSONObject(2).getDouble("coin_holding_value_usd"));

    DBA.clearDatabase();

    responseJsonAddCoins = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest2);
    responseJsonGetCoins = getCoinsHandler.handleGetCoins(mockGoodAuthToken1);
    coinsJson = responseJsonGetCoins.getJSONArray("coins");

    assertEquals("coins get successful", responseJsonGetCoins.getString("response_string"));
    assertEquals(200, responseJsonGetCoins.getInt("response_code"));
    assertTrue(0 <= responseJsonGetCoins.getDouble("portfolio_value_usd"));
    assertEquals(3, coinsJson.length(), 1e-8);
    assertEquals("NEO", coinsJson.getJSONObject(0).getString("coin_ticker"));
    assertEquals(146.41, coinsJson.getJSONObject(0).getDouble("coin_amount"), 1e-8);
    assertTrue(0 <= coinsJson.getJSONObject(0).getDouble("latest_coin_price"));
    assertTrue(0 <= coinsJson.getJSONObject(0).getDouble("coin_holding_value_usd"));
    assertEquals("BAT", coinsJson.getJSONObject(1).getString("coin_ticker"));
    assertEquals(3.41, coinsJson.getJSONObject(1).getDouble("coin_amount"), 1e-8);
    assertTrue(0 <= coinsJson.getJSONObject(1).getDouble("latest_coin_price"));
    assertTrue(0 <= coinsJson.getJSONObject(1).getDouble("coin_holding_value_usd"));
    assertEquals("BTC", coinsJson.getJSONObject(2).getString("coin_ticker"));
    assertEquals(3.41, coinsJson.getJSONObject(2).getDouble("coin_amount"), 1e-8);
    assertTrue(0 <= coinsJson.getJSONObject(2).getDouble("latest_coin_price"));
    assertTrue(0 <= coinsJson.getJSONObject(2).getDouble("coin_holding_value_usd"));

    DBA.clearDatabase();

    // user unauthorized get coins tests
    mockAuthServerResponse = "{\"is user authorized\":false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer(mockBadAuthToken1, mockAuthServerResponse);

    responseJsonGetCoins = getCoinsHandler.handleGetCoins(mockBadAuthToken1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJsonGetCoins,
      true
    );

    // user does not exist get coin tests
    mockAuthServerResponse = "{\"is user authorized\":true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);

    responseJsonGetCoins = getCoinsHandler.handleGetCoins(mockGoodAuthToken1);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"no coins to get\"}",
      responseJsonGetCoins,
      true
    );

  }

}
