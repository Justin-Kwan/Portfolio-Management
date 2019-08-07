'use strict';

class RequestMaker {

  constructor() {
  }

  postUserCoinsToServer(userJson) {

    console.log("Posted Json!");

    console.log(userJson);

    $.ajaxSetup({
      crossDomain: true,
      xhrFields: {
        withCredentials: true
      }
    });

    $.post("http://127.0.0.1:8001/addCoinsToPortfolio", userJson, function(data, status) {

      console.log("CRUD server response: " + data);

    });

  }
  
  getUserCoinsFromServer() {

    console.log("Getting Coins!");

    var httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'http://127.0.0.1:8001/getPortfolio', true);
    httpRequest.withCredentials = true;
    httpRequest.send(null);

    console.log("Server User Json: " + httpRequest.responseText);

  }

}
