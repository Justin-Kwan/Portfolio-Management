'use strict';

class SubmitHandler {

  constructor() {
  }

  handleAddCoinJsonPrep() {
    const user = new User();
    const requestMaker = new RequestMaker();

    user.createCoinCollection();
    const userJson = JSON.stringify(user);

    requestMaker.postUserCoinsToServer(userJson);
  }

}

$("#submit-portfolio-button").click(function(event) {
  console.log("SUBMIT!");
  const submitHandler = new SubmitHandler();
  submitHandler.handleAddCoinJsonPrep();
});
