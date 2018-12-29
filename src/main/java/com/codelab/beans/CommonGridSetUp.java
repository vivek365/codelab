package com.codelab.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "commongridsetup")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "commonGridSetUpId")
public class CommonGridSetUp implements Serializable {

	private static final long serialVersionUID = 3961371619023603111L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long commonGridSetUpId;
	private Long moduleId;
	private Long subModuleId;
	private String json;
	private String fromClause;
	
	@Transient
	private String moduleName;
	
	@Transient
	private String subModuleName;
	

	/*@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "moduleId", insertable = false, updatable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Module module;

	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subModuleId", insertable = false, updatable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private SubModule subModule;*/

	public Long getCommonGridSetUpId() {
		return commonGridSetUpId;
	}
	public void setCommonGridSetUpId(Long commonGridSetUpId) {
		this.commonGridSetUpId = commonGridSetUpId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Long getSubModuleId() {
		return subModuleId;
	}
	public void setSubModuleId(Long subModuleId) {
		this.subModuleId = subModuleId;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getFromClause() {
		return fromClause;
	}
	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}
	/*public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public SubModule getSubModule() {
		return subModule;
	}
	public void setSubModule(SubModule subModule) {
		this.subModule = subModule;
	}*/
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	
	
}
