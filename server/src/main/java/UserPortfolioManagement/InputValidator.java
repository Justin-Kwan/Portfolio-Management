package UserPortfolioManagement;
import java.io.InputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.apache.commons.io.IOUtils;

/**
 *  class responsible for validating received client json requests and auth
 *  tokens
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class InputValidator {

  private final boolean AUTH_TOKEN_VALID             = true;
  private final boolean AUTH_TOKEN_INVALID           = false;
  private final boolean JSON_REQUEST_VALID           = true;
  private final boolean JSON_REQUEST_INVALID         = false;
  private final String  CLIENT_ADD_JSON_COINS_SCHEMA = "/ClientCoinsSchema.json";

  private final ObjectMapper mapper                  = new ObjectMapper();


  public boolean handleAuthTokenValidation(String authToken) {
    boolean isAuthTokenEmpty = this.checkInputEmpty(authToken);

    if(isAuthTokenEmpty) return AUTH_TOKEN_INVALID;
    
    return AUTH_TOKEN_VALID;
  }

  public boolean checkInputEmpty(String input) {
    boolean isInputEmpty = (input == null || input.isEmpty());
    return isInputEmpty;
  }

  // use strategy pattern for schema loading and json validation?
  public boolean validateJsonRequest(String jsonRequest) {
    JsonNode jsonClientSchemaObj = getJsonClientSchema();

    try {
      JsonNode jsonRequestObj           = mapper.readTree(jsonRequest);
      final JsonSchemaFactory factory   = JsonSchemaFactory.byDefault();
      final JsonSchema metaSchema       = factory.getJsonSchema(jsonClientSchemaObj);
      ProcessingReport validationReport = metaSchema.validate(jsonRequestObj);

      if(!validationReport.isSuccess()) return JSON_REQUEST_INVALID;

    }catch(Exception error) {
      return JSON_REQUEST_INVALID;
    }

    return JSON_REQUEST_VALID;
  }

  private JsonNode getJsonClientSchema() {
    InputStream inputStream = getClass().getResourceAsStream(CLIENT_ADD_JSON_COINS_SCHEMA);
    String jsonClientSchema = IOUtils.toString(inputStream, "utf-8");
    JsonNode jsonClientSchemaObj = mapper.readTree(jsonClientSchema);
    return jsonClientSchemaObj;
  }

}
