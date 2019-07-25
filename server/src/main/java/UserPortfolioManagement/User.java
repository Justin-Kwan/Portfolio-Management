package UserPortfolioManagement;
import java.util.ArrayList;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Base64;

public class User {

  private String username;
  private String userId;
  private String securityToken;
  private double portfolioValueUsd;
  private ArrayList<Coin> coins = new ArrayList<Coin>();

  // ctor
  public User(String securityToken) {
    this.updateSecurityToken(securityToken);
  }

  private void updateUsername(String username) {
    this.username = username;
  }

  private void updateUserId(String userId) {
    this.userId = userId;
  }

  /* test!! */
  public void updateUserInfo(String username, String userId) {
    this.updateUsername(username);
    this.updateUserId(userId);
  }

  private void updateSecurityToken(String securityToken) {
    this.securityToken = securityToken;
  }

  public void addCoin(Coin coin) {
    (this.coins).add(coin);
  }

  public String getUsername() {
    return username;
  }

  public String getUserId() {
    return userId;
  }

  public String getSecurityToken() {
    return securityToken;
  }

  public ArrayList getCoins() {
    return coins;
  }

  public double getPortfolioValueUsd() {
    return portfolioValueUsd;
  }

  public boolean authorizeRequest() {

    boolean REQUEST_AUTHORIZED = true;
    boolean REQUEST_UNAUTHORIZED = false;

    if(this.securityToken == null) {
      return REQUEST_UNAUTHORIZED;
    }

    try {
      Algorithm algorithm = Algorithm.HMAC256("fake_secret_key");
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT jwt = verifier.verify(this.securityToken);
      return REQUEST_AUTHORIZED;
    }
    catch (JWTVerificationException exception){
      return REQUEST_UNAUTHORIZED;
    }

  }

  // public Array getSecurityTokenInfo() {
  //
  //
  //
  // }

  public void calculatePortfolioValue() {
    for(int i = 0; i < coins.size(); i++) {
      double currentCoinHoldingValue = coins.get(i).getHoldingValueUsd();
      this.portfolioValueUsd += currentCoinHoldingValue;
    }
  }

}
