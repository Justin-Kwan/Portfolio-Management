package UserPortfolioManagement;
import java.net.UnknownHostException;
import com.mongodb.MongoException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DB;
import com.mongodb.util.JSON;
import com.google.gson.Gson;

public class DatabaseAccessor {

  private final String  MONGODB_HOST    = "127.0.0.1";
  private final int     MONGODB_PORT    = 27017;

  private final String  USER_ID_FIELD   = "userId";

  private MongoClient   mongoClient;
  private DB            userPortfoliosDb;
  private DBCollection  userCollection;

  Gson gson = new Gson();

  public void createConnection() throws UnknownHostException {
    this.mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
    this.userPortfoliosDb = mongoClient.getDB("User_Portfolios");
    this.userCollection = userPortfoliosDb.getCollection("Users");
  }

  public void insertNewUser(User user) {
    String userJsonObject = gson.toJson(user);
    DBObject userDbObject = (DBObject) JSON.parse(userJsonObject);
    userCollection.insert(userDbObject);
  }

  // public void updateUser(User user) {
  //
  // }

  public boolean checkUserExists(User user) {
    String userId = user.getUserId();
    DBObject query = new BasicDBObject(USER_ID_FIELD, userId);
    DBCursor cursor = userCollection.find(query);
    DBObject userDbObject = cursor.one();
    boolean doesUserExist = (userDbObject != null);
    return doesUserExist;
  }

  public void clearDatabase() {
    userPortfoliosDb.getCollection("Users").remove(new BasicDBObject());
  }

  public void closeConnection() {
    mongoClient.close();
  }

}
