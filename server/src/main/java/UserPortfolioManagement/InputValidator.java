package UserPortfolioManagement;

// old lib
import java.io.InputStream;
// import org.everit.json.schema.Schema;
// import org.everit.json.schema.loader.SchemaLoader;
// import org.everit.json.schema.ValidationException;
//
import org.json.JSONObject;
// import org.json.JSONArray;
import org.json.JSONException;
// import org.json.JSONTokener;
//
// // second lib
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.apache.commons.io.IOUtils;


// third lib
// import java.nio.file.Path;
// import java.nio.file.Paths;
//
// import javax.json.stream.JsonParser;
//
// import org.leadpony.justify.api.JsonSchema;
// import org.leadpony.justify.api.JsonValidationService;
// import org.leadpony.justify.api.ProblemHandler;


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
      new JSONObject(requestJsonString);
    }catch(JSONException error) {
      return JSON_REQUEST_INVALID;
    }

    return JSON_REQUEST_VALID;
  }


  public boolean validateRequestJson(String requestJsonString) {

    ObjectMapper mapper      = new ObjectMapper();
    // get stream of json meta schema
    InputStream inputStream  = getClass().getResourceAsStream("/ClientCoinsSchema.json");
    // convert meta schema stream to string
    String coinsSchemaString = IOUtils.toString(inputStream, "utf-8");
    // convert meta schema string to json Node
    JsonNode coinsSchema     = mapper.readTree(coinsSchemaString);

    try {
      // create json node instance of request json string
      JsonNode requestJsonObject = mapper.readTree(requestJsonString);
      final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
      final JsonSchema schema = factory.getJsonSchema(coinsSchema);
      ProcessingReport report;
      report = schema.validate(requestJsonObject);

      if(report.isSuccess() == false) {
        return JSON_REQUEST_INVALID;
      }

    }catch(Exception error) {
      return JSON_REQUEST_INVALID;
    }

    return JSON_REQUEST_VALID;
  }





}
