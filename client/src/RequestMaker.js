'use strict';

class RequestMaker {

  constructor() {
  }

  postUserCoinsToServer(userJson) {

    console.log("POSTED JSON!");

    console.log(userJson);

    $.post("http://127.0.0.1:8001/createPortfolio", userJson, function(data, status) {

      // alert("Data: " + data + "\nStatus: " + status);
    });

  }

}
