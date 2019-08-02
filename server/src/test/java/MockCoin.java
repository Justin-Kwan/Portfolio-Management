import UserPortfolioManagement.Coin;

class MockCoin extends Coin {

  // ctor
  public MockCoin(String coinTicker, double coinAmount, double latestCoinPrice, double coinHoldingValueUsd) {
    super(coinTicker, coinAmount);
    super.latestCoinPrice = latestCoinPrice;
    super.coinHoldingValueUsd = coinHoldingValueUsd;
  }

  // public String observableGenerateFetchPriceUrl() {
  //   String fetchPriceUrl = super.generateFetchPriceUrl();
  //   return fetchPriceUrl;
  // }

}
