package UserPortfolioManagement;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
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
    System.out.println("######################################################");
    String fetchPriceUrl = generateFetchPriceUrl();

    try {

      HttpResponse<JsonNode> response = Unirest.get(fetchPriceUrl)
                                          .header("accept", "application/json")
                                          .asJson();

      JSONObject myObj = response.getBody().getObject();

      double price = myObj.getJSONObject("RAW").getJSONObject("BTC").getJSONObject("USD").getDouble("PRICE");

      System.out.println("*****COIN FETCH RESPONSE: " + price);

      System.out.println("*****COIN FETCH RESPONSE: " + myObj);


    }catch(Exception error) {
      error.printStackTrace();
    }

  }

  protected String generateFetchPriceUrl() {
    String coinTicker = this.getTicker();
    String fetchPriceUrl = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=" + coinTicker + "&tsyms=USD";
    return fetchPriceUrl;
  }

  protected void calculateHoldingValue() {
    coinAmount = this.getAmount();
    latestCoinPrice = this.getLatestPrice();

    this.coinHoldingValueUsd = coinAmount * latestCoinPrice;
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
