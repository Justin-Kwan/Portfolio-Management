
getPortfolioCoins();

async function getPortfolioCoins() {
  const authToken = getTokenByCookieName("crypto_cost_session");
  const coinsJson = await fetchCoinsFromServer();

  console.log("User's coins: " + coinsJson);
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

function getTokenByCookieName(cookieName) {
  var authToken = document.cookie.match('(^|[^;]+)\\s*' + cookieName + '\\s*=\\s*([^;]+)');
  return authToken ? authToken.pop() : '';
}
