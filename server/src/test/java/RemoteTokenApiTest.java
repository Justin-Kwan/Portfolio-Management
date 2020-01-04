import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Rule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.json.JSONObject;
import UserPortfolioManagement.RemoteTokenApi;


public class RemoteTokenApiTest {

  RemoteTokenApi remoteTokenApi = new RemoteTokenApi();

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(5000);

  /**
   * mock auth token server response on token check
   */
  public void setMockAuthServer(String mockAuthToken, String mockAuthServerResponse) {
    WireMock.reset();
    stubFor(post(urlEqualTo("/authorizeUser"))
          .withRequestBody(containing("\"crypto_cost_session\":" + "\"" + mockAuthToken + "\""))
          .willReturn(aResponse()
          .withBody(mockAuthServerResponse)));
  }

  @Test
  public void test_fetchAuthCheck() {

    Thread.sleep(4000);

    String mockAuthToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpcyB1c2VyIGF1dGhvcml6ZWQiOnRydWUsInVzZXIgaWQiOiJ1c2VyX2lkXzEiLCJyZXNwb25zZSBjb2RlIjoyMDB9.eBmCKoQ39E58vc5_RAJ7ysXA5evzYDUShGXSXfGAnWU";
    String mockAuthServerResponse = "{\"is user authorized\": true, \"user id\":\"user_id_1\", \"response code\":200}";

    this.setMockAuthServer(mockAuthToken, mockAuthServerResponse);
    
    Object[] authTokenPayload = remoteTokenApi.fetchAuthCheck(mockAuthToken);
    assertEquals(true, authTokenPayload[0]);
    assertEquals("user_id_1", authTokenPayload[1]);

    mockAuthToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpcyB1c2VyIGF1dGhvcml6ZWQiOmZhbHNlLCJ1c2VyIGlkIjoidXNlcl9pZF8yIiwicmVzcG9uc2UgY29kZSI6MjAwfQ.r-tqkgYlc3PksCZPS--Hq7T3qLifdCSYrwLwCIpEGOg";
    mockAuthServerResponse = "{\"is user authorized\": false, \"user id\":\"user_id_2\", \"response code\":200}";

    this.setMockAuthServer(mockAuthToken, mockAuthServerResponse);

    authTokenPayload = remoteTokenApi.fetchAuthCheck(mockAuthToken);
    assertEquals(false, authTokenPayload[0]);
    assertEquals("user_id_2", authTokenPayload[1]);

  }

}
