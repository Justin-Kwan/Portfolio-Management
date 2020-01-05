import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.Test;
import org.junit.Rule;
import org.json.JSONObject;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import PortfolioManagement.AddCoinsHandler;
import PortfolioManagement.DatabaseAccessor;
import PortfolioManagement.User;
import PortfolioManagement.Coin;

import java.util.Arrays;
import java.util.ArrayList;


public class AddCoinsHandlerTest {

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
  public void test_handleAddCoins() {
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;

    JSONObject responseJson;

    String mockGoodJsonRequest1 = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockGoodJsonRequest2 = "{\"coins\":[{\"coin_ticker\":\"NEO\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest1  = "{\"coins\":[{\"coin_ticker\":,\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest2  = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":2i3.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest3  = "{\"coins\":[{\"coin_ticker\":\"\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest4  = "{\"coins\":[{\"coin_ticker\":\"COINNOTHERE\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockGoodAuthToken1   = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsInVzZXIgaWQiOiJyYW5kb211c2VyMTIzIn0._ysaIs-QjPF4smD_LiNCAYruphEP-R5-Zg2VsVo4wXU";
    String mockBadAuthToken1    = "eyJ0eXAiOiJKV1QiLCJhbkGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";


    Thread.sleep(4000);

    String mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":201,\"response_string\":\"coins add successful\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest2);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest3);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest4);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, "123");
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer(mockBadAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockBadAuthToken1, mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer(mockBadAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockBadAuthToken1, mockBadJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer("", mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins("", mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer("123", mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins("123", mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer("null", mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins("null", mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    user = new User("authtoken___2", "ff4406fc-67b2-4f86-b2dd-4f9b35c64202", null, "");
    Coin coin1 = new MockCoin("NEO", 0.202331231231, 88291, 201);
    Coin coin2 = new MockCoin("BAT", 1233.2, 2131, 839);
    ArrayList<Coin> coins = new ArrayList<>(Arrays.asList(coin1, coin2));
    user.setCoins(coins);
    DBA.insertNewUser(user);

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = addCoinsHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest2);
    JSONAssert.assertEquals(
      "{\"response_code\":201,\"response_string\":\"coins add successful\"}",
      responseJson,
      true
    );

    this.afterTest(DBA);
  }

}
