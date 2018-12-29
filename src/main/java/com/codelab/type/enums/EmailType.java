package com.codelab.type.enums;

public enum EmailType {

	forgotPassword(0),
	registerNewUser(1),
	inviteUser(2);
	
	
	private int value;
	private EmailType(int value) {
		this.value = value;
	}
	
	public EmailType getType(int value){
		switch(value){
			case 0:
				return EmailType.forgotPassword;
			case 1:
				return EmailType.registerNewUser;
			case 2:
				return EmailType.inviteUser;
			default:
				return null;
		}
	}
}
