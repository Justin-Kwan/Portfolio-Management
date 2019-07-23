import UserPortfolioManagement.Coin;

class MockCoin extends Coin {

  private double observerCoinHoldingValueUsd = 0;

  // ctor
  public MockCoin(double coinHoldingValueUsd) {
    super("", 0);
    this.observerCoinHoldingValueUsd = coinHoldingValueUsd;
  }

  // alter function for purpose of subclass mock
  @Override
  public double getHoldingValueUsd() {
    return observerCoinHoldingValueUsd;
  }

}
