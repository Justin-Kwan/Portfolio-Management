import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import UserPortfolioManagement.Coin;

public class CoinTest {

  Coin coin;
  MockCoin mockCoin;

  @Test
  public void test_setTicker() {
    coin = new Coin("BTC", 0.5);
    assertEquals("BTC", coin.getTicker());

    coin = new Coin("XRP", 0.5);
    assertEquals("XRP", coin.getTicker());
  }

  @Test
  public void test_setAmount() {
    coin = new Coin("BTC", 0.5);
    assertEquals(0.5, coin.getAmount(), 1e-8);

    coin = new Coin("XRP", 0.51);
    assertEquals(0.51, coin.getAmount(), 1e-8);
  }

  @Test
  public void test_incrementAmount() {
    coin = new Coin("BTC", 0.5);
    coin.incrementAmount(0.34);
    coin.incrementAmount(1.23);
    assertEquals(2.07, coin.getAmount(), 1e-8);

    coin = new Coin("TRON", 0);
    coin.incrementAmount(2.00192301);
    coin.incrementAmount(0.00000001);
    coin.incrementAmount(0.00000001);
    assertEquals(2.00192303, coin.getAmount(), 1e-8);
  }

  @Test
  public void test_fetchAndSetLatestPrice() {
    double latestCoinPrice;

    coin = new Coin("BTC", 0.55);
    coin.fetchAndSetLatestPrice();
    latestCoinPrice = coin.getLatestPrice();
    assertNotNull(latestCoinPrice);

    coin = new Coin("ETH", 123);
    coin.fetchAndSetLatestPrice();
    latestCoinPrice = coin.getLatestPrice();
    assertNotNull(latestCoinPrice);

    coin = new Coin("XRP", 13);
    coin.fetchAndSetLatestPrice();
    latestCoinPrice = coin.getLatestPrice();
    assertNotNull(latestCoinPrice);
  }

  @Test
  public void test_generateFetchPriceUrl() {
    String fetchCoinPriceUrl;

    coin = new Coin("BTC", 0);
    fetchCoinPriceUrl = coin.jailbreak().generateFetchPriceUrl();
    assertEquals("https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC&tsyms=USD", fetchCoinPriceUrl);

    coin = new Coin("ETH", 0);
    fetchCoinPriceUrl = coin.jailbreak().generateFetchPriceUrl();
    assertEquals("https://min-api.cryptocompare.com/data/pricemultifull?fsyms=ETH&tsyms=USD", fetchCoinPriceUrl);

    coin = new Coin("TRON", 0);
    fetchCoinPriceUrl = coin.jailbreak().generateFetchPriceUrl();
    assertEquals("https://min-api.cryptocompare.com/data/pricemultifull?fsyms=TRON&tsyms=USD", fetchCoinPriceUrl);
  }

  @Test
  public void test_calculateAndSetHoldingValue() {
    double latestCoinPrice;

    mockCoin = new MockCoin("BTC", 0.5, 9871.2314, 0);
    mockCoin.calculateAndSetHoldingValue();
    latestCoinPrice = mockCoin.getHoldingValueUsd();
    assertEquals(4935.6157, latestCoinPrice, 1e-8);

    mockCoin = new MockCoin("ETH", 14.59812, 23.142, 0);
    mockCoin.calculateAndSetHoldingValue();
    latestCoinPrice = mockCoin.getHoldingValueUsd();
    assertEquals(337.82969304, latestCoinPrice, 1e-8);

    mockCoin = new MockCoin("LTC", 0.0035, 999991231.2312, 0);
    mockCoin.calculateAndSetHoldingValue();
    latestCoinPrice = mockCoin.getHoldingValueUsd();
    assertEquals(3499969.3093092, latestCoinPrice, 1e-8);

    mockCoin = new MockCoin("NANO", 0.89, 0, 0);
    mockCoin.calculateAndSetHoldingValue();
    latestCoinPrice = mockCoin.getHoldingValueUsd();
    assertEquals(0, latestCoinPrice, 1e-8);
  }

  @Test
  public void test_getTicker() {
    coin = new Coin("ZTC", 0);
    assertEquals("ZTC", coin.getTicker());

    coin = new Coin("BTP", 0);
    assertEquals("BTP", coin.getTicker());
  }

  @Test
  public void test_getAmount() {
    coin = new Coin("", 0.0000001);
    assertEquals(0.0000001, coin.getAmount(), 1e-8);

    coin = new Coin("", 23123.1231249871623);
    assertEquals(23123.1231249871623, coin.getAmount(), 1e-8);
  }

  @Test
  public void test_getLatestPrice() {
    double latestCoinPrice;

    mockCoin = new MockCoin("", 0, 9123.2813, 0);
    latestCoinPrice = mockCoin.getLatestPrice();
    assertEquals(9123.2813, latestCoinPrice, 1e-8);

    mockCoin = new MockCoin("", 0, -2312.3, 0);
    latestCoinPrice = mockCoin.getLatestPrice();
    assertEquals(-2312.3, latestCoinPrice, 1e-8);
  }

  @Test
  public void test_getHoldingValueUsd() {
    double coinHoldingValueUsd;

    mockCoin = new MockCoin("", 0, 0, 0.01);
    coinHoldingValueUsd = mockCoin.getHoldingValueUsd();
    assertEquals(0.01, coinHoldingValueUsd, 1e-8);

    mockCoin = new MockCoin("", 0, 0, -0.00123412);
    coinHoldingValueUsd = mockCoin.getHoldingValueUsd();
    assertEquals(-0.00123412, coinHoldingValueUsd, 1e-8);
  }


}
