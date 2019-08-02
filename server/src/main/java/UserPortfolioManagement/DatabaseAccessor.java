package UserPortfolioManagement;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import java.util.Arrays;
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

  private final String              USER_ID_FIELD    = "userId";
  private final String              USER_COINS_FIELD = "coins";

  private MongoClient               mongoClient;
  private MongoDatabase             userPortfoliosDb;
  private MongoCollection<Document> userCollection;

  Gson gson = new Gson();

  public void createConnection() {
    this.mongoClient = new MongoClient(MONGODB_HOST, MONGODB_PORT);
    this.userPortfoliosDb = mongoClient.getDatabase("User_Portfolios");
    this.userCollection = userPortfoliosDb.getCollection("Users");
  }

  public void insertNewUser(User user) {
    String userJsonObject = gson.toJson(user);
    Document userDocument = Document.parse(userJsonObject.toString());
    userCollection.insertOne(userDocument);
  }

  // public void updateUser(User user) {
  //
  // }

  public JSONArray selectUserCoins(User user) {
    String userId = user.getUserId();

    // get single field from document
    Document coinsDocument = userCollection
    .find(new BasicDBObject(USER_ID_FIELD, userId))
    .projection(Projections.fields(Projections.include(USER_COINS_FIELD), Projections.excludeId()))
    .first();

    JSONObject coinsJsonObject = new JSONObject(coinsDocument);
    JSONArray coinsJsonArray = coinsJsonObject.getJSONArray("coins");
    return coinsJsonArray;
  }

  public boolean checkUserExists(String userId) {
    FindIterable<Document> iterable = userPortfoliosDb.getCollection("Users")
                                    .find(new Document(USER_ID_FIELD, userId));

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
