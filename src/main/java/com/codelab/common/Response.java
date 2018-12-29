package com.codelab.common;

/**
 * This class is used to send response with messages.
 * 
 * @author Vivek Jain
 *
 */
public class Response implements Constant {
	private int code;
	private String message;
	private Object data;

	public Response() {
		this.code = ConstantStatus.OK.value();
		this.message = SUCCESS;
	}

	/**
	 * @param code
	 * @param message
	 * @author BSATHVARA
	 * @ModifiedBy BSATHVARA
	 * @ModifiedDate Mar 24, 2017
	 */
	public Response(ConstantStatus status, String message) {
		this.code = status.value();
		this.message = message;
	}

	public Response(ConstantStatus status, String message, Object data) {
		this.code = status.value();
		this.message = message;
		this.data = data;
	}

	public int getcode() {
		return code;
	}

	public void setcode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
