package UserPortfolioManagement;
import java.net.UnknownHostException;
import com.mongodb.MongoException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;
import com.google.gson.Gson;

public class DatabaseAccessor {

  private final String MONGODB_HOST = "127.0.0.1";
  private final int MONGODB_PORT    = 27017;

  private MongoClient mongoClient;
  private DB userPortfoliosDb;
  private DBCollection userCollection;

  public void createConnection() throws UnknownHostException {
    this.mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
    this.userPortfoliosDb = mongoClient.getDB("User_Portfolios");
    this.userCollection = userPortfoliosDb.getCollection("Users");
  }

  public void insertNewUser(User user)  {
    String userJsonObject = this.convertUserToJsonObject(user);
    DBObject userDbObject = (DBObject) JSON.parse(userJsonObject);
    userCollection.insert(userDbObject);
  }

  // public void updateUser(User user) {
  //
  // }
  //
  // public void checkUserExists(user) {
  //
  // }

  public String convertUserToJsonObject(User user) {
    Gson gson = new Gson();
    String userJsonObject = gson.toJson(user);
    return userJsonObject;
  }

  // MongoClient.close()

}
