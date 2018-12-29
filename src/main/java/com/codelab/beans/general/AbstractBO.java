package com.codelab.beans.general;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Transient;

import com.codelab.common.ObjUtillity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractBO implements Serializable {

	private static final long serialVersionUID = 50155935974678419L;

	/** The id. */
	protected Long id;
	private Long createdById;
	private Long updatedById;

	/** The created by. */
	// protected User createdBy;

	/** The updated by. */
	// protected User updatedBy;

	/** The active status. */
	// protected Status activeStatus;

	/** The created on. */
	// @JsonSerialize(using = JsonDateTimeSerializer.class)
	// @JsonDeserialize(using = JsonDateTimeDeserializer.class)
	protected Date createdOn;

	/** The updated on. */
	// @JsonSerialize(using = JsonDateTimeSerializer.class)
	// @JsonDeserialize(using = JsonDateTimeDeserializer.class)
	protected Date updatedOn;

	/** The json data. */
	protected String jsonData;

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	/**
	 * Gets the json map.
	 *
	 * @return the json map
	 */
	@Transient
	public Map<String, Object> getJsonMap() {
		final ObjectMapper mapper = new ObjectMapper();
		final String json = this.jsonData;
		Map<String, Object> map = null;
		if (ObjUtillity.isNotBlank(json)) {
			map = new HashMap<String, Object>();
			// convert JSON string to Map
			try {
				map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
				});
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				// logger.error("error", e);
			}
		}
		return map;
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

}
