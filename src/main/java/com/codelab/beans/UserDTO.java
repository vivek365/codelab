package com.codelab.beans;

import java.io.Serializable;

import com.codelab.beans.general.AbstractDTO;
import com.codelab.beans.general.DTOSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DTOSerializer.class)
@JsonInclude(Include.NON_NULL)
public class UserDTO extends AbstractDTO implements Serializable {
	private static final long serialVersionUID = 1297255599607122695L;

	private String userName;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;

	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String profilePic;
	private Integer resetPasswordFlag;

	public UserDTO() {
	}

	public UserDTO(Long id, String firstName, String lastName) {
		super.setId(id);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Integer getResetPasswordFlag() {
		return resetPasswordFlag;
	}

	public void setResetPasswordFlag(Integer resetPasswordFlag) {
		this.resetPasswordFlag = resetPasswordFlag;
	}

}
