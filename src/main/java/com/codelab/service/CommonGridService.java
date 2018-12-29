package com.codelab.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.codelab.beans.CommonGridSetUp;
import com.codelab.beans.Module;
import com.codelab.beans.SubModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface CommonGridService {

	/**
	 * To save or update commonGrid main json
	 * 
	 * @param json {"moduleId":1,"subModuleId":1,"json":{"columns":[],"actions":[]}, "fromClause": "from appointmenttable" }
	 * @return CommonGridSetUp
	 * @throws Exception
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	CommonGridSetUp saveCommonGridSetUpJson(ObjectNode json) throws Exception;

	/**
	 * To fetch commonGrid main json from submoduleid
	 * 
	 * @param json {"subModuleId":1}
	 * @return CommonGridSetUp
	 * @throws Exception
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	CommonGridSetUp getCommonGridSetUpJson(ObjectNode json) throws Exception;

	

	/**
	 * To save or update CommonGrid
	 * 
	 * @param json 
	  	{
			"flexibleWorkFlowID":112,
			"moduleId":1,
			"subModuleId":1,
			"mainJson":{"columns":[1],"actions":[1]}, 
			"quickLink":"default",
			"description":"this is default",
			"practice":"1",
			"users":"2",
			"isCommonGrid":0
		
		}
	 * @return CommonGridFlexibleWorkFlow
	 * @throws Exception
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
//	CommonGridFlexibleWorkFlow saveCommonGrid(ObjectNode json)throws Exception;
	
	
	/**
	 * To fetch CommonGrid from flexibleWorkFlowID or ( subModuleId or moduleId )+ isCommonGrid
	 * 
	 * @param json {"flexibleWorkFlowID":123} or {"subModuleId":1, "isCommonGrid":1} or or {"moduleId":1, "isCommonGrid":1}
	 * @return CommonGridFlexibleWorkFlow
	 * @throws Exception
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
//	List<CommonGridFlexibleWorkFlow> getCommonGrid(ObjectNode json) throws Exception;

	/**
	 * To delete,active or inactive commonGrid/search
	 * 
	 * @param json {"flexibleWorkFlowID":123, "isCommonGrid":3}
	 * @return Boolean
	 * @throws Exception
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 14, 2017
	 */
//	Boolean updateCommonGrid(ObjectNode json) throws Exception;


	/**
	 * To load data table json
	 * 
	 * @param json {"moduleId":123, subModuleId=1}
	 * @return Map
	 * @throws Exception
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 14, 2017
	 */
	Map<String, Object> loadCommonDataGridJson(ObjectNode json) throws Exception;

	Map<String, Object> getDataForCommonDataGrid(ObjectNode objectNode)throws Exception;

	List<Module> getModuleList(ObjectNode objectNode)throws Exception;

	List<SubModule> getSubModuleList(ObjectNode objectNode) throws Exception;

	void export(ObjectNode json, HttpServletResponse response) throws Exception;



	

}
