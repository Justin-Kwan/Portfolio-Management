

public class Coin {

  private final String coinTicker;
  private double coinAmount = 0;
  private double latestCoinPrice;
  private double coinHoldingValueUsd;

  // ctor
  public Coin(coinTicker, coinAmount) {
    this.updateCoinTicker(coinTicker);
    this.updateCoinAmount(coinAmounts);
  }

  private void updateCoinTicker(coinTicker) {
    this.coinTicker = coinTicker;
  }

  private void incrementCoinAmount(coinAmount) {
    this.coinAmount += coinAmount;
  }

  public double fetchAndUpdateLatestCoinPrice(String coinTicker) {
    coinTicker = this.getCoinTicker();
    // http GET req latest price
    this.latestCoinPrice = this.fetchLatestCoinPrice(coinTicker);
  }

  private double calculateCoinHoldingValue() {
    coinAmount = this.getCoinAmount();
    latestCoinPrice = this.getLatestCoinPrice();

    this.coinHoldingValueUsd = coinAmount * latestCoinPrice;
  }

  private double getCoinHoldingValue() {
    return coinHoldingValue;
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
