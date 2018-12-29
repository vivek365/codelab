package com.codelab.common;

public enum ConstantStatus {

	OK(200),

	BAD_REQUEST(400), // Bad Request

	UNAUTHORIZED(401), // Wrong Credentials (Ex. Username or Password is
						// incorrect)

	FORBIDDEN(403), // Unauthorized/Forbidden (Ex. You are not authorized to
					// view this page)

	NOT_FOUND(404),

	CONFLICT(409), // Duplicate Record

	UNPROCESSABLE_ENTITY(422), // Invalid Data/Required Field value missing

	INTERNAL_SERVER_ERROR(500);

	private int code;

	ConstantStatus(int code) {
		this.code = code;
	}

	public int value() {
		return code;
	}
}
