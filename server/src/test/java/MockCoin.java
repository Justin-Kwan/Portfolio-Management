import UserPortfolioManagement.Coin;

class MockCoin extends Coin {

  // ctor
  public MockCoin(String coinTicker, double coinAmount, double latestCoinPrice, double coinHoldingValueUsd) {
    super("", 0);

    this.updateTicker(coinTicker);
    this.updateAmount(coinAmount);
    this.latestCoinPrice = latestCoinPrice;
    this.coinHoldingValueUsd = coinHoldingValueUsd;
  }

  // alter function for purpose of subclass mock
  @Override
  public double getHoldingValueUsd() {
    return this.coinHoldingValueUsd;
  }

}
