package com.codelab.common;

import com.codelab.beans.general.AbstractDTO;

public class QueryParams extends AbstractDTO{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9049388729301929946L;

	public enum QueryConstants{
			
		LIKE,
		STARTS_WITH,
		GT,
		LT,
		GE,
		LE,
		IN,
		NOT_IN,
		EQ,
		IS_NULL,
		IS_NOT_NULL,
		NE,
		BETWEEN,
		ORDER_BY,
		RESULT_COUNT,
		DESC,
		ASC;
	}
	
	private Object propertyName;
	private Object condition;
	private Object propertyValue;
	private Long value;
	private Double valueD;
	private Integer valueI;
	private Boolean valueBoolean;
	private String valueString;
	
	public QueryParams(Object propertyName, Object condition, Object propertyValue){
		
		this.propertyName = propertyName;
		this.condition = condition;
		this.propertyValue = propertyValue;
	}

	public Object getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(Object propertyName) {
		this.propertyName = propertyName;
	}

	public Object getCondition() {
		return condition;
	}

	public void setCondition(Object condition) {
		this.condition = condition;
	}

	public Object getPropertyValue() {
		if(this.value!= null){
			return this.value;
		}else if(this.valueI!= null){
			return this.valueI;
		}else if(this.valueD!= null){
			return this.valueD;
		}else if(this.valueBoolean!=null){
			return this.valueBoolean;
		}else if(this.valueString!=null){
			return this.valueString;
		}else{
			return this.propertyValue;
		}
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
	
}
