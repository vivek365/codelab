package com.codelab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codelab.common.Constant;
import com.codelab.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "unsecure", tags = "UnAuthorized Controller ")
@RequestMapping("/unsecure")
public class UnAuthorizedController implements Constant {
	static final Logger LOGGER = LoggerFactory.getLogger(UnAuthorizedController.class);

	@Autowired
	UserService userService;
	
	@ApiOperation(value = "forgotPassword")
	@RequestMapping(value = {
			"/forgotPassword" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String forgotPassword(@RequestBody ObjectNode json) {
		LOGGER.debug("Execute method : forgotPassword()");
		return "forgotPassword";
	}

	@ApiOperation(value = "resetPassword")
	@RequestMapping(value = {
			"/resetPassword" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String resetPassword(@RequestBody ObjectNode json) {
		LOGGER.debug("Execute method : resetPassword()");
		
		return "resetPassword";
	}
	
}
