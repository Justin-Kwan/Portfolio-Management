import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.InputValidator;

public class InputValidatorTest {

  private InputValidator inputValidator = new InputValidator();
  private boolean isAuthTokenEmpty;
  private boolean isAuthTokenValid;

  @Test
  public void test_checkInputEmpty() {
    isAuthTokenEmpty = inputValidator.checkInputEmpty("authToken_1*");
    assertEquals(false, isAuthTokenEmpty);

    isAuthTokenEmpty = inputValidator.checkInputEmpty(" ");
    assertEquals(false, isAuthTokenEmpty);

    isAuthTokenEmpty = inputValidator.checkInputEmpty("");
    assertEquals(true, isAuthTokenEmpty);

    isAuthTokenEmpty = inputValidator.checkInputEmpty(null);
    assertEquals(true, isAuthTokenEmpty);
  }

  @Test
  public void test_handleAuthTokenValidation() {
    isAuthTokenValid = inputValidator.handleAuthTokenValidation("authToken_1*");
    assertEquals(true, isAuthTokenValid);

    isAuthTokenValid = inputValidator.handleAuthTokenValidation("");
    assertEquals(false, isAuthTokenValid);

    isAuthTokenValid = inputValidator.handleAuthTokenValidation(null);
    assertEquals(false, isAuthTokenValid);
  }

  // @Test
  // public void test_validateTypeIsJson() {
  //   String mockJsonRequest;
  //   boolean isJsonRequestValid;
  //
  //   // bad JSON, malformed JSON
  //   mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, malformed JSON
  //   mockJsonRequest = "{\"coins\":{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, malformed JSON
  //   mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\"\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, malformed JSON
  //   mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123}{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, malformed JSON
  //   mockJsonRequest = "{\"coins\":[{\"coinTicker:\"\"BTC\",\"coinAmount\":123}{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}]}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, not JSON
  //   mockJsonRequest = "random";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, not JSON
  //   mockJsonRequest = "";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, missing value
  //   mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":1}]}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, missing value
  //   mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":2},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":,\"coinAmount\":1}]}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, missing key
  //   mockJsonRequest = "{\"coins\":[{:\"BTC\",\"coinAmount\":23},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":1}]}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //   // bad JSON, missing key
  //   mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":2},{\"coinTicker\":\"NEO\",:23.41},{\"coinTicker\":\"ETH\",\"coinAmount\":1}]}";
  //   isJsonRequestValid = inputValidator.validateTypeIsJson(mockJsonRequest);
  //   assertEquals(false, isJsonRequestValid);
  //
  //
  //
  // }

  @Test
  public void test_validateRequestJson() {
    String mockJsonRequest;
    boolean isJsonRequestValid;

    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"BAT\",\"coinAmount\":3.41}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"LTC\",\"coinAmount\":1223}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    // edge case for minimum coinAmount
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":0.0001},{\"coinTicker\":\"LTC\",\"coinAmount\":0.0001},{\"coinTicker\":\"ETH\",\"coinAmount\":0.0001}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(true, isJsonRequestValid);

    // bad JSON, edge case for less than minimum coinAmount
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":0.00001},{\"coinTicker\":\"LTC\",\"coinAmount\":0.00001},{\"coinTicker\":\"ETH\",\"coinAmount\":0.00001}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, coinAmount values are strings
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":\"1s23\"},{\"coinTicker\":\"NEO\",\"coinAmount\":\"23.41\"},{\"coinTicker\":\"LTC\",\"coinAmount\":\"31.49123\"}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, wrong key instead of "coinTicker"
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":\"1s23\"},{\"coinTicker\":\"NEO\",\"coinAmount\":\"23.41\"},{\"coinTicke r\":\"LTC\",\"coinAmount\":\"31.49123\"}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, wrong key instead of "coinAmount"
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":\"123\"},{\"coinTicker\":\"NEO\",\"coinAmmount\":\"23.41\"},{\"coinTicke r\":\"LTC\",\"coinAmount\":\"31.49123\"}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, wrong key instead of "coins"
    mockJsonRequest = "{\"coinss\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, number key
    mockJsonRequest = "{\"coins\":[{123:\"BTC\",\"coinAmount\":23},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, number key
    mockJsonRequest = "{\"coins\":[{1:\"BTC\",\"coinAmount\":0.00001},{\"coinTicker\":\"LTC\",\"coinAmount\":0.00001},{\"coinTicker\":\"ETH\",\"coinAmount\":0.00001}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, bad key
    mockJsonRequest = "{\"coinss\":[{\"coinTicker\":\"BTC\",coinAmount:123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random key with no quotes
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":2},{\"coinTicker\":\"NEO\",randomstuff:23.41},{\"coinTicker\":\"ETH\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random value with no quotes
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":randomstuff},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"ETH\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random value with no quotes and escape character
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":randomstuff\\,\"coinAmount\":23.41},{\"coinTicker\":\"ETH\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed additional fields
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}], \"time\":3}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed additional fields
    mockJsonRequest = "{\"cars\":\"five\",\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":31.49123}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed boolean value
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":false,\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":12}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed null value
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":null}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, unallowed null value
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":null,\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":87}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random value with no quotes and bad escape character "\"
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":2},{\"coinTicker\":\"NEO\",randomstuff\\:23.41},{\"coinTicker\":\"ETH\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, random JSON
    mockJsonRequest = "{\"name\":\"John\", \"age\":30, \"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123}], \"car\":null}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":{\"coinTicker\":\"BTC\",\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\"\"coinAmount\":123},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":123}{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, malformed JSON
    mockJsonRequest = "{\"coins\":[{\"coinTicker:\"\"BTC\",\"coinAmount\":123}{\"coinTicker\":\"NEO\",\"coinAmount\":23.41}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, not JSON
    mockJsonRequest = "random";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, not JSON
    mockJsonRequest = "";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing value
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing value
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":2},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":,\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing key
    mockJsonRequest = "{\"coins\":[{:\"BTC\",\"coinAmount\":23},{\"coinTicker\":\"NEO\",\"coinAmount\":23.41},{\"coinTicker\":\"LTC\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);

    // bad JSON, missing key
    mockJsonRequest = "{\"coins\":[{\"coinTicker\":\"BTC\",\"coinAmount\":2},{\"coinTicker\":\"NEO\",:23.41},{\"coinTicker\":\"ETH\",\"coinAmount\":1}]}";
    isJsonRequestValid = inputValidator.validateRequestJson(mockJsonRequest);
    assertEquals(false, isJsonRequestValid);
  }

}
