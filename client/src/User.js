'use strict';

class User {

  constructor() {
    this.coins = [];
  }

  appendCoin(coin) {
    this.coins.push(coin);
    console.log(coin.ticker);
    console.log("CTICKER" + this.coins[0].ticker);
  }

  createCoinCollection() {
    const coinTickerInput = document.getElementsByTagName("select");
    const coinAmountInput = document.getElementsByTagName("input");

    for(let i = 0; i < coinTickerInput.length; ++i) {
      const coin = new Coin(coinTickerInput[i].value, coinAmountInput[i].value);
      this.appendCoin(coin);
    }
  }

}
