package UserPortfolioManagement;

public class IndexResponseDecider {

  ResultCodes resultCodes = new ResultCodes();

  public String determineAddCoinsResponse(String resultCode) {
    String indexResponse = "";

    if(resultCode == resultCodes.SUCCESS)
      indexResponse =  "Coins successfully added";
    else if(resultCode == resultCodes.ERROR_REQUEST_UNAUTHORIZED)
      indexResponse = "Request is unauthorized";
    else if(resultCode == resultCodes.ERROR_JSON_REQUEST_INVALID)
      indexResponse =  "Request is invalid";

    return indexResponse;
  }







}
