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


}
