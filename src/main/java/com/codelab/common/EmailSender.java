package com.codelab.common;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.codelab.beans.Email;


@Component
@PropertySource("classpath:mail.properties")
public class EmailSender {
	
	@Autowired
    private Environment env;
		
	private static String username = null;
	private static String password = null;
	private static final Properties props = new Properties();
	static {
		
		/*username = "dev.codelab@outlook.com";
		password = "asdasd@codelab";*/
		
		username = "support@fieldplex.com";
		password = "Fieldplex@12345";
		
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");

	}
	
	/*public static void main(String[] args) {
		EmailSender e = new EmailSender();
		Email email = new Email(Constant.MAIL_INVITEUSER, 1L, username, "mhr1chauhan@gmail.com", "check", " hjkhfasdjkhfjkdh jkhd jkhj");
		System.out.println(e.sendEmail(email));
	}*/
	
	
	public boolean sendEmail(final Email email){
		try {
		if(email.getAttempts() >= email.getMaxAttempts() || !isValid(email)){
			return false;
		}
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EmailSender.username, EmailSender.password);
			}
		  });
		
		//session.setDebug(true);
		
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(EmailSender.username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
			if(ObjUtillity.isNotBlank(email.getCc())){
				message.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(email.getCc()));
			}
			if(ObjUtillity.isNotBlank(email.getBcc())){
				message.setRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(email.getBcc()));
			}
			message.setSubject(email.getSubject());
			message.setText(email.getBody());
			Transport.send(message);
			email.setIsSent("Y");
			email.setSentTime(new Date());
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			email.setIsSent("Y");
			email.setAttempts(email.getAttempts()+1);
			email.setUpdatedOn(new Date());
		}
		return false;
	}
	private boolean isValid(Email email) {
		if(ObjUtillity.isBlank(email.getRecipient()) 
				&& ObjUtillity.isBlank(email.getCc()) 
				&& ObjUtillity.isBlank(email.getBcc())){
			return false;
		}
		if(ObjUtillity.isBlank(email.getSubject())){
			return false;
		}
		return true;
	}
}
