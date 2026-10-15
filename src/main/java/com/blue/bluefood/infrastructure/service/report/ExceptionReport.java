package com.blue.bluefood.infrastructure.service.report;

public class ExceptionReport extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExceptionReport(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionReport(String message) {
		super(message);
	}
	
	

}
