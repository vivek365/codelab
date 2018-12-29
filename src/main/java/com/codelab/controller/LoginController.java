package com.codelab.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codelab.beans.UserContext;
import com.codelab.common.Constant;
import com.codelab.configuration.CodelabRuntimeException;
import com.codelab.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "login", tags = "Login Controller")
@RequestMapping("/login")
public class LoginController implements Constant {
	static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserService userService;

	@ApiOperation(value = "Login")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Username", value = "Username", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "X-Password", value = "Password", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "requestBody", value = "loginModel", required = true, paramType = "body") })
	@RequestMapping(value = { "/" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserContext login(HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		LOGGER.debug("Execute method : login()");
		UserContext user = null;
		try {
			user = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		} catch (Exception e) {
			LOGGER.error("Error in login()", e);
			throw new CodelabRuntimeException("Error in login()", e);
		}
		return user;
	}
}
