package com.codelab.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.codelab.beans.User;

public interface UserService extends UserDetailsService {

	User getByUserName(String userName) throws Exception;

	User getByUserName(String userName, Integer status) throws Exception;

}
