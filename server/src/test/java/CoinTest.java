import static org.junit.Assert.assertEquals;
import org.junit.Test;
import UserPortfolioManagement.Coin;

public class CoinTest {

  @Test
  public void test_setTicker() {
    Coin coin;

    coin = new Coin("BTC", 0.5);
    assertEquals("BTC", coin.getTicker());

    coin = new Coin("XRP", 0.5);
    assertEquals("XRP", coin.getTicker());
  }

  @Test
  public void test_setAmount() {
    Coin coin;

    coin = new Coin("BTC", 0.5);
    assertEquals(0.5, coin.getAmount(), 1e-8);

    coin = new Coin("XRP", 0.51);
    assertEquals(0.51, coin.getAmount(), 1e-8);
  }

  @Test
  public void test_incrementAmount() {
    Coin coin;

    coin = new Coin("BTC", 0.5);
    coin.incrementAmount(0.34);
    coin.incrementAmount(1.23);
    assertEquals(2.07, coin.getAmount(), 1e-8);

    coin = new Coin("TRON", 0);
    coin.incrementAmount(2.0019230129234231);
    coin.incrementAmount(0.0000000000000001);
    assertEquals(2.0019230129234232, coin.getAmount(), 1e-8);
  }

  @Test
  public void test_fetchAndSetLatestPrice() {
    Coin coin;

    coin = new Coin("BTC", 0.55);
    coin.fetchAndSetLatestPrice();



  }











}
