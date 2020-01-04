package PortfolioManagement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;

/**
 *  class responsible for checking and validating received client json requests
 *
 *  @author justin kwan
 *  @version 1.0.0
 */

public class JsonChecker {

  private static final String       CLIENT_ADD_JSON_COINS_SCHEMA = "/ClientCoinsSchema.json";
  private static final ObjectMapper jacksonMapper                = new ObjectMapper();

  private static final boolean JSON_REQUEST_VALID   = true;
  private static final boolean JSON_REQUEST_INVALID = false;

  // todo: use strategy pattern for schema loading and json validation?
  public boolean checkJsonRequestValid(String jsonRequest) {

    JsonNode jsonClientSchemaObj = getJsonClientSchema();

    try {
      JsonNode jsonRequestObj           = jacksonMapper.readTree(jsonRequest);
      final JsonSchemaFactory factory   = JsonSchemaFactory.byDefault();
      final JsonSchema metaSchema       = factory.getJsonSchema(jsonClientSchemaObj);
      ProcessingReport validationReport = metaSchema.validate(jsonRequestObj);

      if(!validationReport.isSuccess()) return JSON_REQUEST_INVALID;

    }catch(Exception error) {
      return JSON_REQUEST_INVALID;
    }
    return JSON_REQUEST_VALID;
  }

  // todo: split into loadSchema and getSchema
  private JsonNode getJsonClientSchema() {
    InputStream inputStream = getClass().getResourceAsStream(CLIENT_ADD_JSON_COINS_SCHEMA);
    String jsonClientSchema = IOUtils.toString(inputStream, "utf-8");
    JsonNode jsonClientSchemaObj = jacksonMapper.readTree(jsonClientSchema);
    return jsonClientSchemaObj;
  }

}
