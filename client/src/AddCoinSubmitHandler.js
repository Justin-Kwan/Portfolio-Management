
class AddCoinSubmitHandler {

  constructor() {
  }

  handleFormJsonPrep() {
    const user = new User();
    user.createCoinCollection();
    const userJson = JSON.stringify(user);
    console.log(userJson);
    // make req
  }

}

$("#submit-portfolio-button").click(function(event) {
  console.log("SUBMIT!");
  addCoinSubmitHandler = new AddCoinSubmitHandler();
  addCoinSubmitHandler.handleFormJsonPrep();
});
