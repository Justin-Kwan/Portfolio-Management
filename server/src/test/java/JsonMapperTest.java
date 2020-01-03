import static org.junit.Assert.assertEquals;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.Test;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import manifold.ext.api.Jailbreak;
import UserPortfolioManagement.JsonMapper;
import UserPortfolioManagement.User;
import UserPortfolioManagement.Coin;

public class JsonMapperTest {

  JsonMapper jsonMapper = new JsonMapper();

  @Test
  public void test_mapCoinsJsonForDb() {

    Coin coin1 = new Coin("BTC", 1);
    Coin coin2 = new Coin("LTC", 2.34);
    Coin coin3 = new Coin("ETH", 0.001);

    ArrayList<Coin> coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    JSONArray coinsJsonMap = jsonMapper.jailbreak().mapCoinsJsonForDb(coins);

    JSONAssert.assertEquals(
      "[{coin_ticker:\"BTC\", coin_amount:1},{coin_ticker:\"LTC\", coin_amount:2.34},{coin_ticker:\"ETH\", coin_amount:0.001}]",
      coinsJsonMap,
      true
    );

    coin1 = new Coin("NEO", 0);
    coin2 = new Coin("LTC", -1);
    coin3 = new Coin("ETH", -1.023);

    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    coinsJsonMap = jsonMapper.jailbreak().mapCoinsJsonForDb(coins);

    JSONAssert.assertEquals(
      "[{coin_ticker:\"NEO\", coin_amount:0},{coin_ticker:\"LTC\", coin_amount:-1},{coin_ticker:\"ETH\", coin_amount:-1.023}]",
      coinsJsonMap,
      true
    );
  }

  @Test
  public void test_mapUserJsonForDb() {

    Coin coin1 = new Coin("BTC", 1);
    Coin coin2 = new Coin("LTC", 2.34);
    Coin coin3 = new Coin("ETH", 0.001);

    ArrayList<Coin> coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    User user = new User("auth_token", "user_id", false, "request_json");
    user.jailbreak().coins = coins;

    JSONObject userJsonMap = jsonMapper.mapUserJsonForDb(user);

    JSONAssert.assertEquals(
      "{\"user_id\":\"user_id\",\"coins\":[{\"coin_amount\":1,\"coin_ticker\":\"BTC\"},{\"coin_amount\":2.34,\"coin_ticker\":\"LTC\"},{\"coin_amount\":0.001,\"coin_ticker\":\"ETH\"}]}",
      userJsonMap,
      true
    );

    coin1 = new Coin("NEO", 0);
    coin2 = new Coin("LTC", -1);
    coin3 = new Coin("ETH", -1.023);

    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user = new User("auth_token", "user_id", false, "request_json");
    user.jailbreak().coins = coins;

    userJsonMap = jsonMapper.mapUserJsonForDb(user);

    JSONAssert.assertEquals(
      "{\"user_id\":\"user_id\",\"coins\":[{\"coin_amount\":0,\"coin_ticker\":\"NEO\"},{\"coin_amount\":-1,\"coin_ticker\":\"LTC\"},{\"coin_amount\":-1.023,\"coin_ticker\":\"ETH\"}]}",
      userJsonMap,
      true
    );
  }

}
