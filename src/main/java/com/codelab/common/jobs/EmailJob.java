package com.codelab.common.jobs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codelab.beans.Email;
import com.codelab.common.ObjUtillity;
import com.codelab.configuration.VelocityEmailSender;
import com.codelab.service.EmailService;
import com.codelab.service.GenericDao;

@Component("emailJob")
public class EmailJob {
	private Logger log = LoggerFactory.getLogger(EmailJob.class);
	private static boolean isWorking = false;

	/*@Autowired
	private EmailSender emailSender;*/
	
	@Autowired
	private VelocityEmailSender velocityEmailSender;
	
	@Autowired
	private GenericDao<Email> emailDao;
	
	@Autowired
	private EmailService emailService;
	
/*	@Autowired
	private DriveService driveService;
	*/
	
	//@Scheduled(cron = "0/30 0 * * * ?")
	//@Scheduled(fixedRate=Constant.emailJobDelay)
	public void emailSendJob(){
		if(EmailJob.isWorking){
			return;
		}
		try{
			EmailJob.isWorking = true;
			List<Email> emails = emailService.getPendingMails();
			if(ObjUtillity.isBlank(emails)){
				return;	
			}
			System.out.println("Emails found >> "+emails.size());
			for (Email email : emails) {
				if(!email.getIsSent().equals("Y") && !ObjUtillity.isNull(email)){
					//emailSender.sendEmail(email);
					velocityEmailSender.sendInviteEmail(email);
					emailDao.save(email);
				}
			}
			
			//List<File> files = driveService.retrieveAllFiles();
		}catch(Exception e){
			e.printStackTrace();
			log.error("ERROR Found In : EmailJob  >>  "+e.getMessage());
		}finally{
			EmailJob.isWorking = false;
		}
	}
}
