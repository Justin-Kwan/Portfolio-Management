import static org.junit.Assert.assertEquals;
import org.junit.Test;
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

    String mockGoodJsonRequest1 = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41}]}";
    String mockGoodJsonRequest2 = "{\"coins\":[{\"coinTicker\":\"NEO\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41},{\"coinTicker\":\"BTC\",\"coinAmount\":3.41}]}";
    String mockBadJsonRequest1  = "{\"coins\":[{\"coinTicker\":,\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41}]}";
    String mockBadJsonRequest2  = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":2i3.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41}]}";
    String mockBadJsonRequest3  = "{\"coins\":[{\"coinTicker\":\"\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41},{\"coinTicker\":\"BTC\",\"coinAmount\":3.41}]}";
    String mockBadJsonRequest4  = "{\"coins\":[{\"coinTicker\":\"COINNOTHERE\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41},{\"coinTicker\":\"BTC\",\"coinAmount\":3.41}]}";
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

    user = new MockUser("authtoken___2", "randomuser123", "ff4406fc-67b2-4f86-b2dd-4f9b35c64202", null, "", 543.33);
    coin = new MockCoin("NEO", 0.202331231231, 88291, 201);
    user.addCoin(coin);
    coin = new MockCoin("BAT", 1233.2, 2131, 839);
    user.addCoin(coin);
    DBA.insertNewUser(user);

    resultCode = requestHandler.handleAddCoins(mockGoodAuthToken1, mockGoodJsonRequest2);
    assertEquals("SUCCESS", resultCode);

    this.afterTest(DBA);
  }

  @Test
  public void test_handleCheckUserExists() {
    DatabaseAccessor DBA = this.beforeTest();
    boolean doesUserExist;
    User user;

    // test when db is empty
    doesUserExist = requestHandler.handleCheckUserExists("random_id");
    assertEquals(false, doesUserExist);

    user = new MockUser("authtoken1", "username1", "user__id1*", null, "jsonrequest1", 543.32);
    DBA.insertNewUser(user);
    user = new MockUser("authtoken1", "username2", "user__id2*", null, "jsonrequest2", 544.32);
    DBA.insertNewUser(user);
    user = new MockUser("authtoken1", "username3", "user__id3*", null, "jsonrequest3", 545.32);
    DBA.insertNewUser(user);
    user = new MockUser("authtoken1", "username4", "user__id4*", null, "jsonrequest4", 546.32);
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
