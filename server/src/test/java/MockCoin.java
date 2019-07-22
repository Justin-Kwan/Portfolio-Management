import UserPortfolioManagement.Coin;

class MockCoin extends Coin {

  private double observerCoinHoldingValueUsd = 0;

  // ctor
  public MockCoin(double coinHoldingValueUsd) {
    super("", 0);
    this.observerCoinHoldingValueUsd = coinHoldingValueUsd;
  }
  
  // alter function for subclass mock
  @Override
  public double getHoldingValueUsd() {
    //observerCoinHoldingValueUsd = super.getHoldingValueUsd(); //Call getHoldingValueUsd of Coin
    return observerCoinHoldingValueUsd; //Or return super.observerCoinHoldingValueUsd
  }

}
