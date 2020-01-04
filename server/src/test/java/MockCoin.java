import UserPortfolioManagement.Coin;
import manifold.ext.api.Jailbreak;

class MockCoin extends Coin {

  // ctor
  public MockCoin(String coinTicker, double coinAmount, double latestCoinPrice, double coinHoldingValueUsd) {
    super(coinTicker, coinAmount);
    super.jailbreak().latestCoinPrice = latestCoinPrice;
    super.jailbreak().coinHoldingValueUsd = coinHoldingValueUsd;
  }

}
