$("#add-coin-button").click(function(event) {
  $("#coin-input-table tbody").append(newCoinInputRow);
});

$(document).on("click", '.close', function() {
  $(this).closest('tr').remove();
});

$("#submit-portfolio-button").click(function(event) {
  onSubmitClick();
});

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
  const coinsTable = buildCoinTable(response["coins"]);
  displayCoinTable(coinsTable);
}

async function onSubmitClick() {
  document.getElementById("submit-portfolio-button").disabled = true;

  const coins = getCoinsFromFields();
  const requestJson = getRequestJson(coins);
  const responseJson = await postCoinsToServer(requestJson);
  const response = JSON.parse(responseJson);

  if(response['response_string'] === "request invalid") {
    $("#submit-result-bar").replaceWith(requestInvalid);
    document.getElementById("submit-portfolio-button").disabled = false;
  } else if(response['response_string'] === "request unauthorized") {
    deleteCookie();
    window.location.href = "http://127.0.0.1:5001/login";
  } else {
    window.location.href = "http://127.0.0.1:8000/getPortfolio";
  }
}

function getCoinsFromFields() {
  var coins = [];
  const coinTickerInput = document.getElementsByTagName("select");
  const coinAmountInput = document.getElementsByTagName("input");

  for (let i = 0; i < coinTickerInput.length; ++i) {
    const coin = {
      "coin_ticker": String(coinTickerInput[i].value),
      "coin_amount": Number(coinAmountInput[i].value)
    };
    coins.push(coin);
  }
  return coins;
}

function getRequestJson(coins) {
  return JSON.stringify({
    "coins": coins
  });
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

function postCoinsToServer(requestJson) {
  console.log("Posted Json!");
  console.log(requestJson);

  var promise = new Promise(function(resolve, reject) {
    $.ajaxSetup({
      crossDomain: true,
      xhrFields: {
        withCredentials: true
      }
    });

    $.post("http://127.0.0.1:8001/addCoins", requestJson, function(serverResponse) {
      resolve(serverResponse);
    });
  });
  return promise;
}

function buildCoinTable(coins) {
  var coinsTable = [];

  for (var i = 0; i < coins.length; i++) {
    const coinTicker = coins[i]['coin_ticker'];
    const coinAmount = addCommas(parseFloat(coins[i]['coin_amount'])) + " " + coinTicker;
    const dropDownTickers = determineSelectedTicker(coinTicker);

    const currentHtmlCoinInputRow =
      `<tr id="new-coin-form-row">
        <td class="text-center">
        <form class="form-inline" id="select-coin-input">
          <select class="form-control" id="select-coin-input">` +
      dropDownTickers +
      `</select>
        </form>
      </td>
      <td class="text-center">
        <form class="form-inline" id="coin-amount-input">
          <div class="form-group">
            <input class="form-control" placeholder="Coin Amount" value=` + coins[i]['coin_amount'] + `>
          </div>
        </form>
      </td>
      <td class="row no-gutter">
        <button type='button' class='close' aria-label='Close'>
          <span aria-hidden='true'>&times;</span>
        </button>
      </td>
    </tr>`;

    coinsTable.push(currentHtmlCoinInputRow);
  }
  return coinsTable;
}

function determineSelectedTicker(coinTicker) {
  var btcSelected = "";
  var ethSelected = "";
  var ltcSelected = "";
  var neoSelected = "";
  var batSelected = "";

  if (coinTicker === "BTC") {
    btcSelected = `selected="selected"`;
  } else if (coinTicker === "ETH") {
    ethSelected = `selected="selected"`;
  } else if (coinTicker === "LTC") {
    ltcSelected = `selected="selected"`;
  } else if (coinTicker === "NEO") {
    neoSelected = `selected="selected"`;
  } else {
    batSelected = `selected="selected"`;
  }
  return `<option ` + btcSelected + `>BTC</option>
          <option ` + ethSelected + `>ETH</option>
          <option ` + ltcSelected + `>LTC</option>
          <option ` + neoSelected + `>NEO</option>
          <option ` + batSelected + `>BAT</option>`;

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
    for (var i = 0; i < coinsTable.length; i++) {
      $("#coin-input-table tbody").append(coinsTable[i]);
    }
  }

  const newCoinInputRow = `<tr id='new-coin-forms-'>
                            <td class='text-center'>
                                <form class='form-inline' id='select-coin-input-'>
                                  <select class='form-control' id='select-coin-input'>
                                      <option>BTC</option>
                                      <option>ETH</option>
                                      <option>LTC</option>
                                      <option>NEO</option>
                                      <option>BAT</option>
                                  </select>
                                </form>
                            </td>
                            <td class='text-center'>
                                <form class='form-inline' id='coin-amount-input-'>
                                    <div class='form-group'>
                                      <input class='form-control' id='coin-amount-input' placeholder='Coin Amount'>
                                    </div>
                                </form>
                            </td>
                            <td class="row no-gutter">
                              <button type='button' class='close' aria-label='Close'>
                                <span aria-hidden='true'>&times;</span>
                              </button>
                            </td>
                            </tr>`;

const requestInvalid = `<div id="submit-result-bar" class="col-6 container text-center alert alert-danger">
                        <strong>Sorry, invalid coin values</strong></div>`;
