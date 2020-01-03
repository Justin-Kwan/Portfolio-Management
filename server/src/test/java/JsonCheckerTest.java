import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.JsonChecker;

public class JsonCheckerTest {

  private JsonChecker jsonChecker = new JsonChecker();
  private boolean isAuthTokenEmpty;
  private boolean isAuthTokenValid;

  @Test
  public void test_checkJsonRequestValid() {
    String mockJsonRequest;
    boolean isJsonRequestValid;

    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"BAT\",\"coin_amount\":3.41}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":31.49123}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"LTC\",\"coin_amount\":1223}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    // edge case for minimum coin_amount
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":0.0001},{\"coin_ticker\":\"LTC\",\"coin_amount\":0.0001},{\"coin_ticker\":\"ETH\",\"coin_amount\":0.0001}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    // bad JSON, edge case for less than minimum coin_amount
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":0.00001},{\"coin_ticker\":\"LTC\",\"coin_amount\":0.00001},{\"coin_ticker\":\"ETH\",\"coin_amount\":0.00001}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, coins not from site
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTP\",\"coin_amount\":\"1s23\"},{\"coin_ticker\":\"NEO\",\"coin_amount\":\"23.41\"},{\"coin_ticker\":\"LTC\",\"coin_amount\":\"31.49123\"}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, coins not from site
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":\"1s23\"},{\"coin_ticker\":\"NIO\",\"coin_amount\":\"23.41\"},{\"coin_ticker\":\"LTC\",\"coin_amount\":\"31.49123\"}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, empty string coin ticker
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":\"1s23\"},{\"coin_ticker\":\"NEO\",\"coin_amount\":\"23.41\"},{\"coin_ticker\":\"\",\"coin_amount\":\"31.49123\"}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, empty string coin ticker
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"\",\"coin_amount\":\"1s23\"},{\"coin_ticker\":\"NEO\",\"coin_amount\":\"23.41\"},{\"coin_ticker\":\"BAT\",\"coin_amount\":\"31.49123\"}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, coin_amount values are strings
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":\"1s23\"},{\"coin_ticker\":\"NEO\",\"coin_amount\":\"23.41\"},{\"coin_ticker\":\"LTC\",\"coin_amount\":\"31.49123\"}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, wrong key instead of "coin_ticker"
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":\"1s23\"},{\"coin_ticker\":\"NEO\",\"coin_amount\":\"23.41\"},{\"coinTicke r\":\"LTC\",\"coin_amount\":\"31.49123\"}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, wrong key instead of "coin_amount"
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":\"123\"},{\"coin_ticker\":\"NEO\",\"coinAmmount\":\"23.41\"},{\"coinTicke r\":\"LTC\",\"coin_amount\":\"31.49123\"}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, wrong key instead of "coins"
    mockJsonRequest = "{\"coinss\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":31.49123}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, number key
    mockJsonRequest = "{\"coins\":[{123:\"BTC\",\"coin_amount\":23},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, number key
    mockJsonRequest = "{\"coins\":[{1:\"BTC\",\"coin_amount\":0.00001},{\"coin_ticker\":\"LTC\",\"coin_amount\":0.00001},{\"coin_ticker\":\"ETH\",\"coin_amount\":0.00001}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, bad key
    mockJsonRequest = "{\"coinss\":[{\"coin_ticker\":\"BTC\",coin_amount:123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":31.49123}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random key with no quotes
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":2},{\"coin_ticker\":\"NEO\",randomstuff:23.41},{\"coin_ticker\":\"ETH\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random value with no quotes
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":randomstuff},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"ETH\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random value with no quotes and escape character
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":randomstuff\\,\"coin_amount\":23.41},{\"coin_ticker\":\"ETH\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed additional fields
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":31.49123}], \"time\":3}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed additional fields
    mockJsonRequest = "{\"cars\":\"five\",\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":31.49123}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed boolean value
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":false,\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":12}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed null value
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":null}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed null value
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":null,\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":87}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random value with no quotes and bad escape character "\"
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":2},{\"coin_ticker\":\"NEO\",randomstuff\\:23.41},{\"coin_ticker\":\"ETH\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random JSON
    mockJsonRequest = "{\"name\":\"John\", \"age\":30, \"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123}], \"car\":null}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":{\"coin_ticker\":\"BTC\",\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\"\"coin_amount\":123},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":123}{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coin_ticker:\"\"BTC\",\"coin_amount\":123}{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, not JSON
    mockJsonRequest = "random";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, not JSON
    mockJsonRequest = "";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing value
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing value
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":2},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":,\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing key
    mockJsonRequest = "{\"coins\":[{:\"BTC\",\"coin_amount\":23},{\"coin_ticker\":\"NEO\",\"coin_amount\":23.41},{\"coin_ticker\":\"LTC\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing key
    mockJsonRequest = "{\"coins\":[{\"coin_ticker\":\"BTC\",\"coin_amount\":2},{\"coin_ticker\":\"NEO\",:23.41},{\"coin_ticker\":\"ETH\",\"coin_amount\":1}]}";
    isJsonRequestValid = jsonChecker.checkJsonRequestValid(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);
  }

}
