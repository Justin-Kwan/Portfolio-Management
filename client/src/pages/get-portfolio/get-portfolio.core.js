$("#navbar-logout").click(function(event) {
  console.log("logout!");
  deleteCookie();
  window.location.href = "http://cryptocost.live";
});

onPageLoad();

async function onPageLoad() {
  const authToken = getTokenByCookieName("crypto_cost_session");
  const responseJson = await fetchCoinsFromServer();
  const response = JSON.parse(responseJson);

  displayPortfolioValue(response["response_string"], response["portfolio_value_usd"]);

  const coinsTable = buildCoinTable(response["coins"]);

  console.log(responseJson);

  displayCoinTable(coinsTable);

  updateTableForMobile();
}

function getTokenByCookieName(cookieName) {
  var authToken = document.cookie.match('(^|[^;]+)\\s*' + cookieName + '\\s*=\\s*([^;]+)');
  return authToken ? authToken.pop() : '';
}

function deleteCookie() {
  document.cookie = "crypto_cost_session=; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
}

function fetchCoinsFromServer(authToken) {
  var promise = new Promise(function(resolve, reject) {
    $.ajaxSetup({
      crossDomain: true,
      xhrFields: {
        withCredentials: true
      }
    });

    $.get("http://127.0.0.1:8001/getCoins", function(serverResponse) {
      resolve(serverResponse);
    });
  });
  return promise;
}

function displayPortfolioValue(serverResponseString, portfolioValueUsd) {
  if (serverResponseString === "no coins to get") {
    portfolioValueUsd = "0"
  }
  portfolioValueUsd = addCommas(parseFloat(portfolioValueUsd).toFixed(3));
  document.getElementById("portfolio_value_usd").innerHTML = "$" + portfolioValueUsd;
}

function buildCoinTable(coins) {
  var coinsTable = [];

  for (var i = 0; i < coins.length; i++) {
    const currentCoinImg = determineCoinRowImg(coins[i]['coin_ticker']);
    const coinTicker = coins[i]['coin_ticker'];
    const coinAmount = addCommas(parseFloat(coins[i]['coin_amount'])) + " " + coinTicker;
    const coinPrice = addCommas(parseFloat(coins[i]['latest_coin_price']).toFixed(3));
    const coinHoldingValue = addCommas(parseFloat(coins[i]['coin_holding_value_usd']).toFixed(3));

    const currentHtmlCoinRow =
    `<tr>
     <td>` + currentCoinImg + `</td>
     <td>` + coinTicker + `</td>
     <td>` + coinAmount + `</td>
     <td>$` + coinPrice + `</td>
     <td>$` + coinHoldingValue + `</td>
     </tr>`;

    coinsTable.push(currentHtmlCoinRow);
  }
  return coinsTable;
}

function determineCoinRowImg(coinTicker) {
  var currentCoinImg;

  if(coinTicker === "BTC") {
    currentCoinImg = `<img src="btc.png" id="coin-row-image" width=25>`;
  } else if(coinTicker === "ETH") {
    currentCoinImg = `<img src="eth.png" id="coin-row-image" width=22>`;
  } else if(coinTicker === "LTC") {
    currentCoinImg = `<img src="ltc.png" id="coin-row-image" width=25>`;
  } else if(coinTicker === "NEO") {
    currentCoinImg = `<img src="neo.png" id="coin-row-image" width=25>`;
  } else if(coinTicker === "BAT") {
    currentCoinImg = `<img src="bat.png" id="coin-row-image" width=25>`;
  }
  return currentCoinImg;
}

function addCommas(num) {
  var str = num.toString().split('.');
  if (str[0].length >= 4) {
    str[0] = str[0].replace(/(\d)(?=(\d{3})+$)/g, '$1,');
  }
  if (str[1] && str[1].length >= 4) {
    str[1] = str[1].replace(/(\d{3})/g, '$1 ');
  }
  return str.join('.');
}

function displayCoinTable(coinsTable) {
  for(var i = 0; i < coinsTable.length; i++) {
    $("#get-coins-table tbody").append(coinsTable[i]);
  }
}

function updateTableForMobile() {
  $(".table-wrap").each(function() {
    var nmtTable = $(this);
    var nmtHeadRow = nmtTable.find("thead tr");
    nmtTable.find("tbody tr").each(function() {
      var curRow = $(this);
      for (var i = 0; i < curRow.find("td").length; i++) {
        var rowSelector = "td:eq(" + i + ")";
        var headSelector = "th:eq(" + i + ")";
        curRow.find(rowSelector).attr('data-title', nmtHeadRow.find(headSelector).text());
      }
    });
  });
}
