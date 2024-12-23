package com.synechron.project.exception;

public class InSufficientFundsException extends RuntimeException {

	private String message;

	public InSufficientFundsException(String message) {
		super(message);
		this.message= message;
	}

}
