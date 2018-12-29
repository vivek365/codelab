package com.codelab.service;

import java.util.List;

import com.codelab.beans.Email;

public interface EmailService {

	public List<Email> getPendingMails();
}
