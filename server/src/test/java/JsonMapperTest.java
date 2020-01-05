import static org.junit.Assert.assertEquals;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.Test;
import org.json.JSONArray;
import org.json.JSONObject;
import manifold.ext.api.Jailbreak;

import PortfolioManagement.JsonMapper;
import PortfolioManagement.User;
import PortfolioManagement.Coin;
import PortfolioManagement.Response;

import java.util.ArrayList;
import java.util.Arrays;


public class JsonMapperTest {

  JsonMapper jsonMapper = new JsonMapper();
  private final static boolean WITH_COINS    = true;
  private final static boolean WITHOUT_COINS = false;

  @Test
  public void test_mapUserJsonForDb() {
    Coin coin1 = new Coin("BTC", 1);
    Coin coin2 = new Coin("LTC", 2.34);
    Coin coin3 = new Coin("ETH", 0.001);

    ArrayList<Coin> coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    User user = new User("auth_token", "user_id", false, "request_json");
    user.jailbreak().coins = coins;

    JSONObject userJson = jsonMapper.mapUserJsonForDb(user);

    JSONAssert.assertEquals(
      "{\"user_id\":\"user_id\",\"coins\":[{\"coin_amount\":1,\"coin_ticker\":\"BTC\"},{\"coin_amount\":2.34,\"coin_ticker\":\"LTC\"},{\"coin_amount\":0.001,\"coin_ticker\":\"ETH\"}]}",
      userJson,
      true
    );

    coin1 = new Coin("NEO", 0);
    coin2 = new Coin("LTC", -1);
    coin3 = new Coin("ETH", -1.023);

    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user = new User("auth_token", "user_id", false, "request_json");
    user.jailbreak().coins = coins;

    userJson = jsonMapper.mapUserJsonForDb(user);

    JSONAssert.assertEquals(
      "{\"user_id\":\"user_id\",\"coins\":[{\"coin_amount\":0,\"coin_ticker\":\"NEO\"},{\"coin_amount\":-1,\"coin_ticker\":\"LTC\"},{\"coin_amount\":-1.023,\"coin_ticker\":\"ETH\"}]}",
      userJson,
      true
    );
  }

  @Test
  public void test_mapResponseJsonForClient() {
    ArrayList<Coin> coins = new ArrayList<Coin>();
    User user = new User("", "", null, "");

    Response response = new Response.Builder()
                  .withResponseString("coins add successful")
                  .withResponseCode(201)
                  .withCoins(false)
                  .build();

    JSONObject responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\":\"coins add successful\", \"response_code\":201}",
      responseJson,
      true
    );

    response = new Response.Builder()
                  .withResponseString("request invalid")
                  .withResponseCode(400)
                  .withCoins(false)
                  .build();

    responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\":\"request invalid\", \"response_code\":400}",
      responseJson,
      true
    );

    response = new Response.Builder()
                  .withResponseString("request unauthorized")
                  .withResponseCode(401)
                  .withCoins(false)
                  .build();

    responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\":\"request unauthorized\", \"response_code\":401}",
      responseJson,
      true
    );

    response = new Response.Builder()
                  .withResponseString("request unauthorized")
                  .withResponseCode(401)
                  .withCoins(false)
                  .build();

    responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\":\"request unauthorized\", \"response_code\":401}",
      responseJson,
      true
    );

    Coin coin1 = new Coin("EOS", 1);
    coin1.jailbreak().latestCoinPrice = 2;
    coin1.jailbreak().coinHoldingValueUsd = 3;
    Coin coin2 = new Coin("BTP", 4);
    coin2.jailbreak().latestCoinPrice = 5;
    coin2.jailbreak().coinHoldingValueUsd = 6;
    Coin coin3 = new Coin("ETC", 7);
    coin3.jailbreak().latestCoinPrice = 8;
    coin3.jailbreak().coinHoldingValueUsd = 9;

    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.setCoins(coins);
    user.jailbreak().portfolioValueUsd = 100.2;

    response = new Response.Builder()
                  .withUser(user)
                  .withResponseString("coins get successful")
                  .withResponseCode(200)
                  .withCoins(true)
                  .build();

    responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\": \"coins get successful\",\"response_code\":200,\"portfolio_value_usd\": 100.2, \"coins\":" +
          "[{\"coin_ticker\":\"EOS\",\"coin_amount\":1,\"latest_coin_price\":2,\"coin_holding_value_usd\":3}," +
           "{\"coin_ticker\":\"BTP\",\"coin_amount\":4,\"latest_coin_price\":5,\"coin_holding_value_usd\":6}," +
           "{\"coin_ticker\":\"ETC\",\"coin_amount\":7,\"latest_coin_price\":8,\"coin_holding_value_usd\":9}]}",
      responseJson,
      true
    );

    coin1 = new Coin("NEO", 0.001);
    coin1.jailbreak().latestCoinPrice = 0.02;
    coin1.jailbreak().coinHoldingValueUsd = 2.4;
    coin2 = new Coin("LTC", 1);
    coin2.jailbreak().latestCoinPrice = 0.03;
    coin2.jailbreak().coinHoldingValueUsd = 2.5;
    coin3 = new Coin("ETH", 1.023);
    coin3.jailbreak().latestCoinPrice = 0.04;
    coin3.jailbreak().coinHoldingValueUsd = 2.6;

    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));
    user.setCoins(coins);
    user.jailbreak().portfolioValueUsd = 100.3;

    response = new Response.Builder()
                  .withUser(user)
                  .withResponseString("coins get successful")
                  .withResponseCode(200)
                  .withCoins(true)
                  .build();

    responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\": \"coins get successful\",\"response_code\":200,\"portfolio_value_usd\": 100.3,\"coins\":" +
          "[{\"coin_ticker\":\"NEO\",\"coin_amount\":0.001,\"latest_coin_price\":0.02,\"coin_holding_value_usd\":2.4}," +
           "{\"coin_ticker\":\"LTC\",\"coin_amount\":1,\"latest_coin_price\":0.03,\"coin_holding_value_usd\":2.5}," +
           "{\"coin_ticker\":\"ETH\",\"coin_amount\":1.023,\"latest_coin_price\":0.04,\"coin_holding_value_usd\":2.6}]}",
      responseJson,
      true
    );

    coin1 = new Coin("NEO", 8);
    coin1.jailbreak().latestCoinPrice = 9;
    coin1.jailbreak().coinHoldingValueUsd = 10.1;

    coins = new ArrayList<>(Arrays.asList(coin1));
    user.setCoins(coins);
    user.jailbreak().portfolioValueUsd = 100.4;

    response = new Response.Builder()
                  .withUser(user)
                  .withResponseString("coins get successful")
                  .withResponseCode(200)
                  .withCoins(true)
                  .build();

    responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\": \"coins get successful\",\"response_code\":200,\"portfolio_value_usd\": 100.4,\"coins\":" +
          "[{\"coin_ticker\":\"NEO\",\"coin_amount\":8,\"latest_coin_price\":9,\"coin_holding_value_usd\":10.1}]}",
      responseJson,
      true
    );

    coins = new ArrayList<>(Arrays.asList());
    user.setCoins(coins);
    user.jailbreak().portfolioValueUsd = 0;

    response = new Response.Builder()
                  .withUser(user)
                  .withResponseString("coins get successful")
                  .withResponseCode(200)
                  .withCoins(true)
                  .build();

    responseJson = jsonMapper.mapResponseJsonForClient(response);
    JSONAssert.assertEquals(
      "{\"response_string\": \"coins get successful\",\"response_code\":200,\"portfolio_value_usd\":0,\"coins\":" +
          "[]}",
      responseJson,
      true
    );

  }

  @Test
  public void test_mapCoinsJson() {
    Coin coin1 = new Coin("BTC", 1);
    coin1.jailbreak().latestCoinPrice = 0.01;
    coin1.jailbreak().coinHoldingValueUsd = 2.3;
    Coin coin2 = new Coin("LTC", 2.34);
    coin2.jailbreak().latestCoinPrice = 0.02;
    coin2.jailbreak().coinHoldingValueUsd = 2.4;
    Coin coin3 = new Coin("ETH", 0.001);
    coin3.jailbreak().latestCoinPrice = 0.03;
    coin3.jailbreak().coinHoldingValueUsd = 2.5;

    ArrayList<Coin> coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // get JSON for DB
    JSONArray coinsJson = jsonMapper.jailbreak().mapCoinsJson(coins, "DATABASE");
    JSONAssert.assertEquals(
      "[{coin_ticker:\"BTC\", coin_amount:1},{coin_ticker:\"LTC\", coin_amount:2.34},{coin_ticker:\"ETH\", coin_amount:0.001}]",
      coinsJson,
      true
    );

    // get JSON for client
    coinsJson = jsonMapper.jailbreak().mapCoinsJson(coins, "CLIENT");
    JSONAssert.assertEquals(
      "[{coin_ticker:\"BTC\", coin_amount:1, latest_coin_price:0.01, coin_holding_value_usd:2.3}," +
       "{coin_ticker:\"LTC\", coin_amount:2.34, latest_coin_price:0.02, coin_holding_value_usd:2.4}," +
       "{coin_ticker:\"ETH\", coin_amount:0.001, latest_coin_price:0.03, coin_holding_value_usd:2.5}]",
      coinsJson,
      true
    );

    coin1 = new Coin("NEO", 0);
    coin1.jailbreak().latestCoinPrice = 1.2;
    coin1.jailbreak().coinHoldingValueUsd = 301;
    coin2 = new Coin("LTC", -1);
    coin2.jailbreak().latestCoinPrice = 1.3;
    coin2.jailbreak().coinHoldingValueUsd = 302;
    coin3 = new Coin("ETH", -1.023);
    coin3.jailbreak().latestCoinPrice = 1.4;
    coin3.jailbreak().coinHoldingValueUsd = 303;

    coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3));

    // get JSON for DB
    coinsJson = jsonMapper.jailbreak().mapCoinsJson(coins, "DATABASE");
    JSONAssert.assertEquals(
      "[{coin_ticker:\"NEO\", coin_amount:0},{coin_ticker:\"LTC\", coin_amount:-1},{coin_ticker:\"ETH\", coin_amount:-1.023}]",
      coinsJson,
      true
    );

    // get JSON for client
    coinsJson = jsonMapper.jailbreak().mapCoinsJson(coins, "CLIENT");
    JSONAssert.assertEquals(
      "[{coin_ticker:\"NEO\", coin_amount:0, latest_coin_price:1.2, coin_holding_value_usd:301}," +
       "{coin_ticker:\"LTC\", coin_amount:-1, latest_coin_price:1.3, coin_holding_value_usd:302}," +
       "{coin_ticker:\"ETH\", coin_amount:-1.023, latest_coin_price:1.4, coin_holding_value_usd:303}]",
      coinsJson,
      true
    );

  }

  @Test
  public void test_mapRequestJsonForAuthServer() {
    JSONObject requestJson = jsonMapper.mapRequestJsonForAuthServer("auth_token_123");
    JSONAssert.assertEquals(
      "{\"crypto_cost_session\":\"auth_token_123\"}",
      requestJson,
      true
    );

    requestJson = jsonMapper.mapRequestJsonForAuthServer("auth_token_124");
    JSONAssert.assertEquals(
      "{\"crypto_cost_session\":\"auth_token_124\"}",
      requestJson,
      true
    );

    requestJson = jsonMapper.mapRequestJsonForAuthServer("*");
    JSONAssert.assertEquals(
      "{\"crypto_cost_session\":\"*\"}",
      requestJson,
      true
    );
  }

}
