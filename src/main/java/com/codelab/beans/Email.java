package com.codelab.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.codelab.beans.general.AbstractBO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "email")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Email extends AbstractBO {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9154828366275137404L;
	
	private int type;
	private Long senderId;
	private String sender;
	// can be a comma seperated value
	private String recipient;
	// can be a comma seperated value
	private String cc;
	// can be a comma seperated value
	private String bcc;
	private String subject;
	private String body;
	private String signature;
	private String isSent;
	private int attempts;
	private int maxAttempts;
	private Date sentTime;
	
	public Email(final int type,final Long senderId,final String sender,final String recipient,
				final String subject,final String body) {
		super();
		this.type=type;
		this.senderId = 1L;
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
		this.body = body;
		this.isSent = "N";
		this.attempts = 0;
		this.maxAttempts = 3;
	}
	
	public Email(){
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getIsSent() {
		return isSent;
	}

	public void setIsSent(String isSent) {
		this.isSent = isSent;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	
	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@Override
	public void setId(Long id) {

		super.setId(id);
	}

	@Override
	public Date getCreatedOn() {

		return super.getCreatedOn();
	}

	@Override
	public void setCreatedOn(Date createdOn) {

		super.setCreatedOn(createdOn);
	}

	@Override
	public Date getUpdatedOn() {

		return super.getUpdatedOn();
	}

	@Override
	public void setUpdatedOn(Date updatedOn) {

		super.setUpdatedOn(updatedOn);
	}

	@Override
	public String toString() {
		return "Email [type=" + type + ", senderId=" + senderId + ", sender="
				+ sender + ", recipient=" + recipient + ", cc=" + cc + ", bcc=" + bcc
				+ ", subject=" + subject + ", body=" + body + ", signature="
				+ signature + ", isSent=" + isSent + ", attempts=" + attempts
				+ ", maxAttempts=" + maxAttempts + "]";
	}
	
	
}
