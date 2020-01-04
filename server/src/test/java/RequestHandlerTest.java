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

import PortfolioManagement.RequestHandler;
import PortfolioManagement.DatabaseAccessor;
import PortfolioManagement.User;
import PortfolioManagement.Coin;

import java.util.Arrays;
import java.util.ArrayList;

public class RequestHandlerTest {

  RequestHandler requestHandler = new RequestHandler();

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
    responseJson = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":201,\"response_string\":\"coins add successful\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest2);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest3);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest4);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins(mockGoodAuthToken1, "123");
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"request invalid\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer(mockBadAuthToken1, mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins(mockBadAuthToken1, mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer(mockBadAuthToken1, mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins(mockBadAuthToken1, mockBadJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer("", mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins("", mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer("123", mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins("123", mockGoodJsonRequest1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJson,
      true
    );

    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"unauthorized\", \"response code\":401}";
    this.setMockAuthServer("null", mockAuthServerResponse);
    responseJson = requestHandler.handleAddCoins("null", mockGoodJsonRequest1);
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
    responseJson = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest2);
    JSONAssert.assertEquals(
      "{\"response_code\":201,\"response_string\":\"coins add successful\"}",
      responseJson,
      true
    );

    this.afterTest(DBA);
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

    JSONObject responseJsonAddCoins = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest1);
    JSONObject responseJsonGetCoins = requestHandler.handleGetCoins(mockGoodAuthToken1);
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

    responseJsonAddCoins = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest2);
    responseJsonGetCoins = requestHandler.handleGetCoins(mockGoodAuthToken1);
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

    responseJsonGetCoins = requestHandler.handleGetCoins(mockBadAuthToken1);
    JSONAssert.assertEquals(
      "{\"response_code\":401,\"response_string\":\"request unauthorized\"}",
      responseJsonGetCoins,
      true
    );

    // user does not exist get coin tests
    mockAuthServerResponse = "{\"is user authorized\":true, \"user id\":\"randomuser123\", \"response code\":200}";
    this.setMockAuthServer(mockGoodAuthToken1, mockAuthServerResponse);

    responseJsonGetCoins = requestHandler.handleGetCoins(mockGoodAuthToken1);
    JSONAssert.assertEquals(
      "{\"response_code\":400,\"response_string\":\"no coins to get\"}",
      responseJsonGetCoins,
      true
    );

  }

  @Test
  public void test_handleCheckUserExists() {
    DatabaseAccessor DBA = this.beforeTest();
    boolean doesUserExist;
    User user;

    // test when db is empty
    doesUserExist = requestHandler.handleCheckUserExists("random_id");
    assertEquals(false, doesUserExist);

    user = new User("authtoken1", "user__id1*", null, "jsonrequest1");
    DBA.insertNewUser(user);
    user = new User("authtoken1", "user__id2*", null, "jsonrequest2");
    DBA.insertNewUser(user);
    user = new User("authtoken1", "user__id3*", null, "jsonrequest3");
    DBA.insertNewUser(user);
    user = new User("authtoken1", "user__id4*", null, "jsonrequest4");
    DBA.insertNewUser(user);

    doesUserExist = requestHandler.handleCheckUserExists("user__id0*");
    assertEquals(false, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id1*");
    assertEquals(true, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id2*");
    assertEquals(true, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id3*");
    assertEquals(true, doesUserExist);

    doesUserExist = requestHandler.handleCheckUserExists("user__id4*");
    assertEquals(true, doesUserExist);

    this.afterTest(DBA);
  }


}
