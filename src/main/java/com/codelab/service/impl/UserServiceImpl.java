package com.codelab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.codelab.beans.User;
import com.codelab.beans.UserContext;
import com.codelab.common.Constant;
import com.codelab.common.QueryParams;
import com.codelab.common.QueryParams.QueryConstants;
import com.codelab.service.GenericDao;
import com.codelab.service.UserService;

public class UserServiceImpl implements UserService, Constant {
	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private GenericDao<User> userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug(" *** UseDetailService.loadUserByUsername");
		User user = null;
		try {
			user = getByUserName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		return new UserContext(user);
	}

	@Override
	public User getByUserName(String userName) throws Exception {
		return getByUserName(userName, 1);
	}

	@Override
	public User getByUserName(String userName, Integer status) throws Exception {
		if (userName == null || "".equals(userName)) {
			return null;
		}
		List<QueryParams> queryParamList = new ArrayList<>();
		queryParamList.add(new QueryParams("userName", QueryConstants.EQ, userName));
		if (status != null) {
			queryParamList.add(new QueryParams(STATUS, QueryConstants.EQ, status));
		}

		String[] userNameArr = null;
		List<User> userList = userDao.getListByCriteria(User.class, userNameArr, queryParamList, false);

		if (!userList.isEmpty()) {
			return userList.get(0);
		}
		return null;
	}

}
