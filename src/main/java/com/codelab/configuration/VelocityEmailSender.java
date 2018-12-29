package com.codelab.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.codelab.beans.Email;
import com.codelab.common.ObjUtillity;

public class VelocityEmailSender {

	private static final Logger logger = LoggerFactory
			.getLogger(VelocityEmailSender.class);

	private VelocityEngine velocityEngine;
	private JavaMailSenderImpl mailSender;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void sendInviteEmail(final Email email) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(InternetAddress.parse(email.getRecipient()));
				if(ObjUtillity.isNotBlank(email.getCc())){
					message.setCc(InternetAddress.parse(email.getCc()));
				}
				if(ObjUtillity.isNotBlank(email.getBcc())){
					message.setBcc(InternetAddress.parse(email.getBcc()));
				}
				message.setSubject(email.getSubject());
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("resetLink", email.getBody());
		        
				String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/userInvite.vm", "utf-8", model);

				message.setText(body, true);
			}
		};

		mailSender.send(preparator);
		email.setIsSent("Y");
		email.setAttempts(email.getAttempts()+1);
		email.setUpdatedOn(new Date());

		logger.info("Sent e-mail to '{}'.", email.getRecipient());
	}

	public void sendForgotPassEmail(final Email email, final Map<String, Object> model) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(InternetAddress.parse(email.getRecipient()));
				if(ObjUtillity.isNotBlank(email.getCc())){
					message.setCc(InternetAddress.parse(email.getCc()));
				}
				if(ObjUtillity.isNotBlank(email.getBcc())){
					message.setBcc(InternetAddress.parse(email.getBcc()));
				}
				message.setSubject(email.getSubject());
			/*	Map<String, Object> model = new HashMap<String, Object>();
				model.put("resetLink", email.getBody());*/
		        
				String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/forgotPassword.vm", "utf-8", model);

				message.setText(body, true);
			}
		};

		mailSender.send(preparator);

		logger.info("Sent e-mail to '{}'.", email.getRecipient());
	}

}
