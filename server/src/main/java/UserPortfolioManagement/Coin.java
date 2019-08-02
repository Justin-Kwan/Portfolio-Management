package UserPortfolioManagement;
import java.io.IOException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class Coin {

  protected String coinTicker;
  protected double coinAmount          = 0;
  protected double latestCoinPrice     = 0;
  protected double coinHoldingValueUsd = 0;

  // ctor
  public Coin(String coinTicker, double coinAmount) {
    this.setTicker(coinTicker);
    this.setAmount(coinAmount);
  }

  protected void setTicker(String coinTicker) {
    this.coinTicker = coinTicker;
  }

  protected void setAmount(double coinAmount) {
    this.coinAmount = coinAmount;
  }

  public void incrementAmount(double coinAmount) {
    this.coinAmount += coinAmount;
  }

  public void fetchAndSetLatestPrice() {
    String fetchCoinPriceUrl = generateFetchPriceUrl();

    try {
      HttpResponse<JsonNode> response = Unirest.get(fetchCoinPriceUrl)
                                     .header("accept", "application/json")
                                     .asJson();

      JSONObject coinJson = response.getBody().getObject();
      this.latestCoinPrice = coinJson.getJSONObject("RAW").getJSONObject(getTicker())
                                     .getJSONObject("USD").getDouble("PRICE");

      System.out.println("LATEST PRICE: " + getLatestPrice());

    }catch(UnirestException error) {
      error.printStackTrace();
    }
    
  }

  protected String generateFetchPriceUrl() {
    final String urlStart = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=";
    final String urlEnd   = "&tsyms=USD";

    String coinTicker = this.getTicker();
    String fetchCoinPriceUrl = urlStart + coinTicker + urlEnd;

    return fetchCoinPriceUrl;
  }

  public void calculateAndSetHoldingValue() {
    this.coinHoldingValueUsd = this.getAmount() * this.getLatestPrice();
  }

  public String getTicker() {
    return coinTicker;
  }

  public double getAmount() {
    return coinAmount;
  }

  public double getLatestPrice() {
    return latestCoinPrice;
  }

  public double getHoldingValueUsd() {
    return coinHoldingValueUsd;
  }

}
