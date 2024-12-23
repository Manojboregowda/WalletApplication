package com.synechron.project.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
    
    public CustomerNotFoundException() {
    	
    }
}