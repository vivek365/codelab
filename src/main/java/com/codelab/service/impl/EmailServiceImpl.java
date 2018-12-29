package com.codelab.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codelab.beans.Email;
import com.codelab.service.EmailService;
import com.codelab.service.GenericDao;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private GenericDao<Email> emailDao;
	
   	@Override
	public List<Email> getPendingMails() {
		StringBuilder emailQuery = new StringBuilder();
		
		emailQuery.append(" FROM Email WHERE isSent = :isSent ");

		Map<String, Object> column = new HashMap<>();
		//column.put(Constant.ALIAS, Email.class);
		column.put("isSent", "N");

		return this.emailDao.executeHQLSelect(emailQuery.toString(), column);
	}

}
