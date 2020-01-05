package PortfolioManagement;

public class Response {

	private User user;
	private String responseString;
	private int responseCode;
	private boolean withCoins;

	public static class Builder {

		private User user;
		private String responseString;
		private int responseCode;
		private boolean withCoins;

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Builder withResponseString(String responseString) {
			this.responseString = responseString;
			return this;
		}

		public Builder withResponseCode(int responseCode) {
			this.responseCode = responseCode;
			return this;
		}

		public Builder withCoins(boolean withCoins) {
			this.withCoins = withCoins;
			return this;
		}

		public Response build() {
			Response response = new Response();
			response.setUser(this.user);
			response.setResponseString(this.responseString);
			response.setResponseCode(this.responseCode);
			response.setWithCoins(this.withCoins);
			return response;
		}

	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setWithCoins(boolean withCoins) {
		this.withCoins = withCoins;
	}

	public User getUser() {
		return this.user;
	}

	public String getResponseString() {
		return this.responseString;
	}

	public int getResponseCode() {
		return this.responseCode;
	}

	public boolean isWithCoins() {
		return this.withCoins;
	}

}
