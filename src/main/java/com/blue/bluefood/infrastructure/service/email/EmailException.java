package com.blue.bluefood.infrastructure.service.email;

public class EmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailException(String message) {
		super(message);
	}

	public EmailException(String message, Exception e) {
		super(message, e);
	}
	
}
