package br.com.devdojo.springbootessentials.error;

public class CustomErrorType {

	private String errorMessage;

	public CustomErrorType(String string) {
		errorMessage = string;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
