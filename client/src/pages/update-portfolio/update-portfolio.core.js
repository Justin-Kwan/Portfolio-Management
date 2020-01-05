$("#add-coin-button").click(function(event) {
  $("#coin-input-table tbody").append(newCoinInputRow);
});

$(document).on("click", '.close', function() {
  $(this).closest('tr').remove();
});

$("#submit-portfolio-button").click(function(event) {
  onSubmitClick();
});

async function onSubmitClick() {
  const coins = getCoinsFromFields();
  const requestJson = getRequestJson(coins);
  const serverResponse = await postCoinsToServer(requestJson);
  console.log("server response: " + serverResponse);
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
