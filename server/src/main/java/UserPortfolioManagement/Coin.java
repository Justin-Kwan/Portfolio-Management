

public class Coin {

  private String coinTicker;
  private double coinAmount = 0;
  private double latestCoinPrice;
  private double coinHoldingValueUsd;

  // ctor
  public Coin(String coinTicker, double coinAmount) {
    this.updateCoinTicker(coinTicker);
    this.updateCoinAmount(coinAmount);
  }

  private void updateCoinTicker(String coinTicker) {
    this.coinTicker = coinTicker;
  }

  private void updateCoinAmount(double coinAmount) {
    this.coinAmount = coinAmount;
  }

  private void incrementCoinAmount(double coinAmount) {
    this.coinAmount += coinAmount;
  }

  public void fetchAndUpdateLatestCoinPrice(String coinTicker) {
    coinTicker = this.getCoinTicker();

    // this.latestCoinPrice = // http GET req latest price;
  }
  
  private void calculateCoinHoldingValue() {
    coinAmount = this.getCoinAmount();
    latestCoinPrice = this.getLatestCoinPrice();

    this.coinHoldingValueUsd = coinAmount * latestCoinPrice;
  }

  private double getCoinHoldingValueUsd() {
    return coinHoldingValueUsd;
  }

  public String getCoinTicker() {
    return coinTicker;
  }

  public double getCoinAmount() {
    return coinAmount;
  }

  public double getLatestCoinPrice() {
    return latestCoinPrice;
  }

}
