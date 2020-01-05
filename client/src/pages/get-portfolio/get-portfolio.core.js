getPortfolioCoins();

async function getPortfolioCoins() {
  const authToken = getTokenByCookieName("crypto_cost_session");
  const coinsString = await fetchCoinsFromServer();
  const coinsJson = JSON.parse(coinsString);
  displayPortfolioValue(coinsJson["portfolio_value_usd"]);


  console.log("User's coins: " + JSON.stringify(coinsJson));
  console.log(typeof coinsJson.portfolio_value_usd);
}

function getTokenByCookieName(cookieName) {
  var authToken = document.cookie.match('(^|[^;]+)\\s*' + cookieName + '\\s*=\\s*([^;]+)');
  return authToken ? authToken.pop() : '';
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

function displayPortfolioValue(portfolioValueUsd) {
  document.getElementById("portfolio_value_usd").innerHTML = portfolioValueUsd;
}

const emailInvalid = `<h1 id="portfolio_value_usd" class="display-3"></h1>`;
