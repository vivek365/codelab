package com.codelab.configuration;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public class TokenInfo {
	private final long created = System.currentTimeMillis();
	private final String token;
	private final UserDetails userDetails;

	public TokenInfo(String token, UserDetails userDetails) {
		this.token = token;
		this.userDetails = userDetails;
	}

	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "TokenInfo{" + "token='" + token + '\'' + ", userDetails" + userDetails + ", created="
				+ new Date(created) + '}';
	}
}
