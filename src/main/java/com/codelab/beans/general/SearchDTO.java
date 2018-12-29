package com.codelab.beans.general;

import java.io.Serializable;
import java.util.List;

import com.codelab.common.QueryParams;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DTOSerializer.class)
public class SearchDTO extends AbstractDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*getListByCriteria*/
	private String entity;
	private String[] columnNames;
	private List<QueryParams> whereClause;
	
	private int offset;
	private int limit;
	private boolean wantPagination = false;
	
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public List<QueryParams> getWhereClause() {
		return whereClause;
	}
	public void setWhereClause(List<QueryParams> whereClause) {
		this.whereClause = whereClause;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public boolean getWantPagination() {
		return wantPagination;
	}
	public void setWantPagination(boolean wantPagination) {
		this.wantPagination = wantPagination;
	}
	
}
