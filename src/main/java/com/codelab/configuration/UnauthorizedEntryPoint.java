package com.codelab.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
	static final Logger logger = LoggerFactory.getLogger(UnauthorizedEntryPoint.class);

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.debug("Execute method : UnauthorizedEntryPoint.commence: " + request.getRequestURI());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		
	}
}
