package com.codelab.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
//@Table(name = "SubModuleTable")
@Table(name = "submodule")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "subModuleId")
public class SubModule implements Serializable {
	private static final long serialVersionUID = -2231200872530757460L;

	private Long subModuleId;
	private Long moduleId;
	private String subModuleName;
	/*@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "moduleId", insertable = false, updatable = false)
	@JsonIdentityReference(alwaysAsId = true)
	private Module module;*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getSubModuleId() {
		return subModuleId;
	}
	public void setSubModuleId(Long subModuleId) {
		this.subModuleId = subModuleId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	/*public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}*/
}
