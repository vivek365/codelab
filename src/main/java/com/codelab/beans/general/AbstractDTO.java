package com.codelab.beans.general;

import java.io.Serializable;
import java.util.Date;

import com.codelab.common.CommonUtility;
import com.codelab.common.DTOEntityMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DTOSerializer.class)
public abstract class AbstractDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4641315984233767490L;
	public static DTOEntityMapper DTO_ENTITY_MAPPER = CommonUtility.getDTOEntityMapperInstance();
	/*
	 * static { if(DTO_ENTITY_MAPPER == null){ DTO_ENTITY_MAPPER = new
	 * DTOEntityMapper(); } }
	 */
	private Long id;
	private Date createdOn;
	private Date updatedOn;
	private Long createdById;
	private Long updatedById;
	
	private String createdByFirstName;
	private String updatedByFirstName;
	
	private String createdByLastName;
	private String updatedByLastName;

	private String createdByProfilePic;
	private String updatedByProfilePic;
	/** The map to. */
	/*
	 * @NotNul
	 * 
	 * @NotBlank
	 */
	private String mapTo;

	public String getMapTo() {
		return mapTo;
	}

	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getUpdatedById() {
		return updatedById;
	}

	public void setUpdatedById(Long updatedById) {
		this.updatedById = updatedById;
	}

	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getUpdatedByFirstName() {
		return updatedByFirstName;
	}

	public void setUpdatedByFirstName(String updatedByFirstName) {
		this.updatedByFirstName = updatedByFirstName;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public String getUpdatedByLastName() {
		return updatedByLastName;
	}

	public void setUpdatedByLastName(String updatedByLastName) {
		this.updatedByLastName = updatedByLastName;
	}

	public String getCreatedByProfilePic() {
		return createdByProfilePic;
	}

	public void setCreatedByProfilePic(String createdByProfilePic) {
		this.createdByProfilePic = createdByProfilePic;
	}

	public String getUpdatedByProfilePic() {
		return updatedByProfilePic;
	}

	public void setUpdatedByProfilePic(String updatedByProfilePic) {
		this.updatedByProfilePic = updatedByProfilePic;
	}

	

}
