package UserPortfolioManagement;

public class Coin {

  private String coinTicker;
  private double coinAmount = 0;
  private double latestCoinPrice = 0;
  private double coinHoldingValueUsd = 0;

  // ctor
  public Coin(String coinTicker, double coinAmount) {
    this.updateTicker(coinTicker);
    this.updateAmount(coinAmount);
  }

  private void updateTicker(String coinTicker) {
    this.coinTicker = coinTicker;
  }

  private void updateAmount(double coinAmount) {
    this.coinAmount = coinAmount;
  }

  private void incrementAmount(double coinAmount) {
    this.coinAmount += coinAmount;
  }

  public void fetchAndUpdateLatestPrice(String coinTicker) {
    coinTicker = this.getTicker();

    // this.latestCoinPrice = // http GET req latest price;
  }

  private void calculateHoldingValue() {
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
