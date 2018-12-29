package com.codelab.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.codelab.beans.general.AbstractBO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends AbstractBO implements Serializable {

	public User(final Long id) {
		this.id = id;
	}
	public User(){
		super();
	}

	private static final long serialVersionUID = -5038587605668973077L;

	private String userName;
	private String password;
	private Integer status;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getResetPasswordFlag() {
		return resetPasswordFlag;
	}

	public void setResetPasswordFlag(Integer resetPasswordFlag) {
		this.resetPasswordFlag = resetPasswordFlag;
	}
	
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
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
	public Long getCreatedById() {
		return super.getCreatedById();
	}

	@Override
	public void setCreatedById(Long createdById) {
		super.setCreatedById(createdById);
	}

	@Override
	public Long getUpdatedById() {
		return super.getUpdatedById();
	}

	@Override
	public void setUpdatedById(Long updatedById) {
		super.setUpdatedById(updatedById);
	}

}
