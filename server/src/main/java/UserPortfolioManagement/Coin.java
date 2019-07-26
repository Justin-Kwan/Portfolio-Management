package UserPortfolioManagement;

public class Coin {

  protected String coinTicker;
  protected double coinAmount = 0;
  protected double latestCoinPrice = 0;
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

  protected void incrementAmount(double coinAmount) {
    this.coinAmount += coinAmount;
  }

  public void fetchAndSetLatestPrice(String coinTicker) {
    coinTicker = this.getTicker();

    // this.latestCoinPrice = // http GET req latest price;
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
