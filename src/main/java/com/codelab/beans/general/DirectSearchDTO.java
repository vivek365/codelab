package com.codelab.beans.general;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DTOSerializer.class)
public class DirectSearchDTO extends AbstractDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
	private Long companyId;
	private Long projectId;
	private Long boqId;
	private Long activityId;
	private Boolean needFullData;
	
	private String searchEntity;
	
	public Boolean getNeedFullData() {
		return needFullData;
	}
	public void setNeedFullData(Boolean needFullData) {
		this.needFullData = needFullData;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getBoqId() {
		return boqId;
	}
	public void setBoqId(Long boqId) {
		this.boqId = boqId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getSearchEntity() {
		return searchEntity;
	}
	public void setSearchEntity(String searchEntity) {
		this.searchEntity = searchEntity;
	}
	
}
