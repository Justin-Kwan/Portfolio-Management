package UserPortfolioManagement;
import java.io.InputStream;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.ValidationException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class InputValidator {

  private final boolean AUTH_TOKEN_VALID     = true;
  private final boolean AUTH_TOKEN_INVALID   = false;
  private final boolean JSON_REQUEST_VALID   = true;
  private final boolean JSON_REQUEST_INVALID = false;

  public boolean handleAuthTokenValidation(String authToken) {
    boolean isAuthTokenEmpty = this.checkInputEmpty(authToken);

    if(isAuthTokenEmpty) {
      return AUTH_TOKEN_INVALID;
    }
    return AUTH_TOKEN_VALID;
  }

  public boolean checkInputEmpty(String input) {
    boolean isInputEmpty = (input == null || input.isEmpty());
    return isInputEmpty;
  }

  public boolean validateTypeIsJson(String requestJsonString) {
    try {
      JSONObject requestJsonObject = new JSONObject(requestJsonString);
    }catch(Exception error) {
      return JSON_REQUEST_INVALID;
    }

    return JSON_REQUEST_VALID;
  }

  // can use strategy pattern here? for different request types
  public boolean validateRequestJson(String requestJsonString) {

    try(InputStream inputStream = getClass().getResourceAsStream("/ClientCoinsSchema.json")) {

      System.out.println("***INPUT STREAM: " + inputStream);

      JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
      Schema schema = SchemaLoader.load(rawSchema);
      // throws a ValidationException if this object is invalid
      schema.validate(new JSONObject(requestJsonString));

    }catch(ValidationException error) {
      System.out.println("error: " + error);

      return JSON_REQUEST_INVALID;
    }

    return JSON_REQUEST_VALID;
  }


}
