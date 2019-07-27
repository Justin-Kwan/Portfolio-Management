package UserPortfolioManagement;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Base64;

public class TokenChecker {

  private final boolean REQUEST_AUTHORIZED = true;
  private final boolean REQUEST_UNAUTHORIZED = false;

  public boolean checkAuthToken(String authToken) {
    try{
      DecodedJWT decodedAuthToken = this.decodeAuthToken(authToken);
    }catch(Exception error) {
      return REQUEST_UNAUTHORIZED;
    }
    return REQUEST_AUTHORIZED;
  }

  public String[] getauthTokenInfo(String authToken) {
    DecodedJWT decodedAuthToken = decodeAuthToken(authToken);
    String username = decodedAuthToken.getClaim("username").asString();
    String userId = decodedAuthToken.getClaim("user id").asString();
    String[] userInfoPayload = {username, userId};
    return userInfoPayload;
  }

  public DecodedJWT decodeAuthToken(String authToken) throws JWTVerificationException {
    Algorithm algorithm = Algorithm.HMAC256("fake_secret_key");
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedAuthToken = verifier.verify(authToken);
    return decodedAuthToken;
  }


}
