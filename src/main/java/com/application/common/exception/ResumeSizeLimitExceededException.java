package com.application.common.exception;

public class ResumeSizeLimitExceededException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResumeSizeLimitExceededException(String message) {
		super(message);
	}

}
