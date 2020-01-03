package UserPortfolioManagement;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ArrayList;
import com.mongodb.MongoException;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import com.mongodb.ServerAddress;

/**
 *  database accessing and interaction class for mongodb
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class DatabaseAccessor {

  private final String              MONGODB_HOST     = "127.0.0.1";
  private final int                 MONGODB_PORT     = 27017;

  private final String              USER_ID_FIELD    = "user_id";
  private final String              USER_COINS_FIELD = "coins";

  private MongoClient               mongoClient;
  private MongoDatabase             userPortfoliosDb;
  private MongoCollection<Document> userCollection;

  Gson gson = new Gson();
  JsonMapper jsonMapper = new JsonMapper();
  ObjectMapper objectMapper = new ObjectMapper();

  public void createConnection() {
    this.mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
    this.userPortfoliosDb = mongoClient.getDatabase("User_Portfolios");
    this.userCollection = userPortfoliosDb.getCollection("Users");
  }

  /**
   * db write operations are single threaded
   */
  public synchronized void insertNewUser(User user) {
    JSONObject userJsonMap = jsonMapper.mapUserJsonForDb(user);
    Document userDocument = Document.parse(userJsonMap.toString());
    userCollection.insertOne(userDocument);
  }

  public synchronized void updateUser(User user) {
    JSONObject userJson = jsonMapper.mapUserJsonForDb(user);
    Document userDocument = Document.parse(userJson.toString());
    Bson filter = eq(USER_ID_FIELD, user.getUserId());
    userCollection.replaceOne(filter, userDocument);
  }

  public User selectUser(String userId) {
    Document userFieldsDocument = new Document(USER_ID_FIELD, userId);
    Document userDocument = userCollection.find(userFieldsDocument).first();
    JSONObject jsonUserObj = new JSONObject(userDocument);
    User user = objectMapper.mapUserObjForApp(jsonUserObj);
    return user;
  }

  public ArrayList<Coin> selectUserCoins(User user) {
    String userId = user.getUserId();
    BasicDBObject userFieldsDocument = new BasicDBObject(USER_ID_FIELD, userId);

    Document coinsDocument = userCollection.find(userFieldsDocument)
    .projection(Projections.fields(Projections.include(USER_COINS_FIELD),
    Projections.excludeId())).first();

    JSONObject jsonCoinsObj = new JSONObject(coinsDocument);
    JSONArray jsonCoins = jsonCoinsObj.getJSONArray("coins");

    ArrayList<Coin> coins = objectMapper.mapCoinsObjForApp(jsonCoins);
    return coins;
  }

  public boolean checkUserExists(String userId) {
    Document userFieldsDocument = new Document(USER_ID_FIELD, userId);
    FindIterable<Document> iterable = userPortfoliosDb.getCollection("Users")
    .find(userFieldsDocument);

    boolean doesUserExist = iterable.first() != null;
    return doesUserExist;
  }

  public void clearDatabase() {
    userPortfoliosDb.getCollection("Users").drop();
  }

  public void closeConnection() {
    mongoClient.close();
  }

}
