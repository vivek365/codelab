package com.codelab.configuration;

/**
 * @author Vivek Jain
 *
 */
public class CodelabRuntimeException extends RuntimeException {
	private Exception e;

	public CodelabRuntimeException(String message, Exception e) {
		super(message,e);
		this.e = e;
		e.printStackTrace();
	}

	public Exception getException() {
		return this.e;
	}
}
