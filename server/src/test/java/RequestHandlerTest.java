import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.ArrayList;
import UserPortfolioManagement.RequestHandler;
import UserPortfolioManagement.DatabaseAccessor;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class RequestHandlerTest {

  RequestHandler requestHandler = new RequestHandler();

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

    String resultCode;

    String mockGoodJsonRequest1 = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockGoodJsonRequest2 = "{\"coins\":[{\"coin_ticker\":\"NEO\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest1  = "{\"coins\":[{\"coin_ticker\":,\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest2  = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":2i3.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest3  = "{\"coins\":[{\"coin_ticker\":\"\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockBadJsonRequest4  = "{\"coins\":[{\"coin_ticker\":\"COINNOTHERE\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41},{\"coin_ticker\":\"BTC\",\"coin_amount\":3.41}]}";
    String mockGoodAuthToken1   = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";
    String mockBadAuthToken1    = "eyJ0eXAiOiJKV1QiLCJhbkGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest1);
    assertEquals("SUCCESS", resultCode);

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest1);
    assertEquals("REQUEST_INVALID", resultCode);

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest2);
    assertEquals("REQUEST_INVALID", resultCode);

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest3);
    assertEquals("REQUEST_INVALID", resultCode);

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockBadJsonRequest4);
    assertEquals("REQUEST_INVALID", resultCode);

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, "123");
    assertEquals("REQUEST_INVALID", resultCode);

    resultCode = requestHandler.handleAddCoins(mockBadAuthToken1, mockGoodJsonRequest1);
    assertEquals("REQUEST_UNAUTHORIZED", resultCode);

    resultCode = requestHandler.handleAddCoins(mockBadAuthToken1, mockBadJsonRequest1);
    assertEquals("REQUEST_UNAUTHORIZED", resultCode);

    resultCode = requestHandler.handleAddCoins("", mockGoodJsonRequest1);
    assertEquals("REQUEST_UNAUTHORIZED", resultCode);

    resultCode = requestHandler.handleAddCoins("123", mockGoodJsonRequest1);
    assertEquals("REQUEST_UNAUTHORIZED", resultCode);

    resultCode = requestHandler.handleAddCoins(null, mockGoodJsonRequest1);
    assertEquals("REQUEST_UNAUTHORIZED", resultCode);

    user = new MockUser("authtoken___2", "ff4406fc-67b2-4f86-b2dd-4f9b35c64202", null, "", 543.33);
    Coin coin1 = new MockCoin("NEO", 0.202331231231, 88291, 201);
    Coin coin2 = new MockCoin("BAT", 1233.2, 2131, 839);
    ArrayList<Coin> coins = new ArrayList<>(Arrays.asList(coin1, coin2));
    user.setCoins(coins);
    DBA.insertNewUser(user);

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest2);
    assertEquals("SUCCESS", resultCode);

    this.afterTest(DBA);
  }

  @Test
  public void test_handleGetCoins() {
    int FIRST_COIN  = 0;
    DatabaseAccessor DBA = this.beforeTest();
    User user;
    Coin coin;

    String mockGoodJsonRequest1 = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    String mockGoodAuthToken1 = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InJhbmRvbXVzZXIxMjMiLCJ1c2VyIGlkIjoiZmY0NDA2ZmMtNjdiMi00Zjg2LWIyZGQtNGY5YjM1YzY0MjAyIn0.i2jAkld6y2KkRtgpzFYK449E1EGScB3DeZpLi4BqxVs";

    String resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest1);

    String mockUserObj = requestHandler.handleGetCoins(mockGoodAuthToken1);

    

    System.out.println("Mock User Obj: " + mockUserObj);
    System.out.println("^^^^^^^^^^^^^^^^^^^^");

    JSONAssert.assertEquals("{coin_ticker:\"EON\", coin_amount:32.292, latestCoinPrice:92234, coinHoldingValueUsd:8.22291}", coinsJsonArray.get(FIRST_COIN).toString(), true);
  }

  @Test
  public void test_handleCheckUserExists() {
    DatabaseAccessor DBA = this.beforeTest();
    boolean doesUserExist;
    User user;

    // test when db is empty
    doesUserExist = requestHandler.handleCheckUserExists("random_id");
    assertEquals(false, doesUserExist);

    user = new MockUser("authtoken1", "user__id1*", null, "jsonrequest1", 543.32);
    DBA.insertNewUser(user);
    user = new MockUser("authtoken1", "user__id2*", null, "jsonrequest2", 544.32);
    DBA.insertNewUser(user);
    user = new MockUser("authtoken1", "user__id3*", null, "jsonrequest3", 545.32);
    DBA.insertNewUser(user);
    user = new MockUser("authtoken1", "user__id4*", null, "jsonrequest4", 546.32);
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
