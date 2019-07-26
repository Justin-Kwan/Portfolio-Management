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

  public boolean checkSecurityToken(String securityToken) {
    try{
      DecodedJWT decodedSecurityToken = this.decodeSecurityToken(securityToken);
    }catch(Exception error) {
      return REQUEST_UNAUTHORIZED;
    }
    return REQUEST_AUTHORIZED;
  }

  // public Array getSecurityTokenInfo(String securityToken) {
  //   DecodedJWT jwt = decodeSecurityToken(securityToken);
  //
  // }

  public DecodedJWT decodeSecurityToken(String securityToken) throws JWTVerificationException {
    Algorithm algorithm = Algorithm.HMAC256("fake_secret_key");
    JWTVerifier verifier = JWT.require(algorithm).build();
    DecodedJWT decodedSecurityToken = verifier.verify(securityToken);
    return decodedSecurityToken;
  }

}
