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
<<<<<<< HEAD
    //observerCoinHoldingValueUsd = super.getHoldingValueUsd();
    return observerCoinHoldingValueUsd;
=======
    //observerCoinHoldingValueUsd = super.getHoldingValueUsd(); //Call getHoldingValueUsd of Coin
    return observerCoinHoldingValueUsd; //Or return super.observerCoinHoldingValueUsd
>>>>>>> 1511d697d61f5cb9e2832bc46d358913954cbde7
  }

}
