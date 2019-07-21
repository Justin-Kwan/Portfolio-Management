import java.util.ArrayList;

public class CoinFactory {

  private final String BTC_TICKER = "BTC";
  private final String LTC_TICKER = "LTC";
  private final String ETH_TICKER = "ETH";
  private final String NEO_TICKER = "NEO";
  private final String BAT_TICKER = "BAT";

  public ArrayList createCoinList() {

    ArrayList<Coin> coins = new ArrayList<Coin>();

    // for(int i = 0; i < ...; i++) {



    // }

    return coins;

  }

  private void addCoinsToListFromDb(ArrayList<Coin> coins) {

  }

  private void addCoinsToListFromRequest(ArrayList<Coin> coins) {

  }

  private Coin getNewCoin(String coinTicker, double coinAmount) {
    Coin coin = new Coin(coinTicker, coinAmount);
    return coin;
  }












}
