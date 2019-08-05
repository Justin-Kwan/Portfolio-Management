'use strict';

class RequestMaker {

  constructor() {
  }

  postUserCoinsToServer(userJson) {

    console.log("POSTED JSON!");

    console.log(userJson);

    $.ajaxSetup({
      crossDomain: true,
      xhrFields: {
        withCredentials: true
      }
    });

    $.post("http://127.0.0.1:8001/addCoinsToPortfolio", userJson, function(data, status) {

      // alert("Data: " + data + "\nStatus: " + status);
    });

  }

}
