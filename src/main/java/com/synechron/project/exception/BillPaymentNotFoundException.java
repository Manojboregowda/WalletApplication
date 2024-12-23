package com.synechron.project.exception;

public class BillPaymentNotFoundException extends RuntimeException {
	private String message;

	public BillPaymentNotFoundException(String message) {
		super(message);
		
	}

}
