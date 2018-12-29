package com.codelab.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codelab.beans.CommonGridSetUp;
import com.codelab.beans.Module;
import com.codelab.beans.SubModule;
import com.codelab.common.Constant;
import com.codelab.configuration.CodelabRuntimeException;
import com.codelab.service.CommonGridService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "commonGrid", tags = "CommonGrid")
@RequestMapping("/codelab/commonGrid")
public class CommonGridController implements Constant {
	static final Logger LOGGER = LoggerFactory.getLogger(CommonGridController.class);

	@Autowired
	private CommonGridService commonGridService;

	/**
	 * Return Boolean
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	@ApiOperation(value = "Save CommonGrid Main JSON")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "requestBody", value = "json which you want to save(this is developer level set up)", required = true, paramType = "body") })
	@RequestMapping(value = "/setup/save", method = RequestMethod.POST, produces = "application/json")
	public Long saveCommonGridSetUpJson(
			@ApiParam(name = JSON, value = "Other JSON paramaters", required = false) @RequestBody ObjectNode json) {
		LOGGER.debug("Execute method : saveCommonGridSetUpJson()");
		try {

			CommonGridSetUp commonGridSetUp = this.commonGridService.saveCommonGridSetUpJson(json);

			if (commonGridSetUp != null && commonGridSetUp.getCommonGridSetUpId() != null) {
				return commonGridSetUp.getCommonGridSetUpId();
			}

		} catch (Exception e) {
			LOGGER.error("Error in saveCommonGridSetUpJson()", e);
			throw new CodelabRuntimeException("Error in saveCommonGridSetUpJson()", e);
		}
		return null;
	}

	/**
	 * Return CommonGridSetUp
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	@ApiOperation(value = "Get CommonGrid Main JSON")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "requestBody", value = "pass sub module id", required = true, paramType = "body") })
	@RequestMapping(value = "/setup/get", method = RequestMethod.POST, produces = "application/json")
	public CommonGridSetUp getCommonGridSetUpJson(
			@ApiParam(name = JSON, value = "Other JSON paramaters", required = false) @RequestBody ObjectNode json) {
		LOGGER.debug("Execute method : getCommonGridSetUpJson()");
		CommonGridSetUp commonGridSetUp = null;

		try {
			commonGridSetUp = this.commonGridService.getCommonGridSetUpJson(json);

		} catch (Exception e) {
			LOGGER.error("Error in getCommonGridSetUpJson()", e);
			throw new CodelabRuntimeException("Error in getCommonGridSetUpJson()", e);
		}
		return commonGridSetUp;
	}

	/**
	 * Return Boolean
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	/*
	 * @ApiOperation(value = "Save CommonGrid JSON")
	 * 
	 * @ApiImplicitParams({
	 * 
	 * @ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token",
	 * required = true, dataType = "string", paramType = "header"),
	 * 
	 * @ApiImplicitParam(name = "requestBody", value =
	 * "json which you want to save from search or commonGrid result", required
	 * = true, paramType = "body") })
	 * 
	 * @RequestMapping(value = "/save", method = RequestMethod.POST, produces =
	 * "application/json") public Boolean saveCommonGrid(
	 * 
	 * @ApiParam(name = JSON, value = "Other JSON paramaters", required =
	 * false) @RequestBody ObjectNode json) { LOGGER.debug(
	 * "Execute method : saveCommonGridSetUpJson()"); boolean flag = false; try
	 * { CommonGridFlexibleWorkFlow commonGridFlexibleWorkFlow =
	 * this.commonGridService.saveCommonGrid(json);
	 * 
	 * if (commonGridFlexibleWorkFlow.getFlexibleWorkFlowID() != null) { flag =
	 * true; }
	 * 
	 * } catch (Exception e) { LOGGER.error("Error in saveCommonGrid()", e);
	 * throw new HISRuntimeException("Error in saveCommonGrid()", e); } return
	 * flag; }
	 */

	/**
	 * Return CommonGridFlexibleWorkFlow
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	/*
	 * @ApiOperation(value = "Get CommonGrid JSON")
	 * 
	 * @ApiImplicitParams({
	 * 
	 * @ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token",
	 * required = true, dataType = "string", paramType = "header"),
	 * 
	 * @ApiImplicitParam(name = "requestBody", value =
	 * "pass sub module id or module id or flexibleworkflow id and isCommonGrid flag"
	 * , required = true, paramType = "body") })
	 * 
	 * @RequestMapping(value = "/get", method = RequestMethod.POST, produces =
	 * "application/json") public List<CommonGridFlexibleWorkFlow>
	 * getCommonGrid(
	 * 
	 * @ApiParam(name = JSON, value = "Other JSON paramaters", required =
	 * false) @RequestBody ObjectNode json) { LOGGER.debug(
	 * "Execute method : getCommonGrid()"); List<CommonGridFlexibleWorkFlow>
	 * commonGridFlexibleWorkFlowList = null;
	 * 
	 * try { commonGridFlexibleWorkFlowList =
	 * this.commonGridService.getCommonGrid(json);
	 * 
	 * } catch (Exception e) { LOGGER.error("Error in getCommonGrid()", e);
	 * throw new HISRuntimeException("Error in getCommonGrid()", e); } return
	 * commonGridFlexibleWorkFlowList; }
	 */

	/**
	 * Return Boolean
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	/*
	 * @ApiOperation(value = "Delete, Active or Inactive CommonGrid/Search")
	 * 
	 * @ApiImplicitParams({
	 * 
	 * @ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token",
	 * required = true, dataType = "string", paramType = "header"),
	 * 
	 * @ApiImplicitParam(name = "requestBody", value =
	 * "delete,active or inactive commonGrid/search", required = true, paramType
	 * = "body") })
	 * 
	 * @RequestMapping(value = "/update", method = RequestMethod.POST, produces
	 * = "application/json") public Boolean updateCommonGrid(
	 * 
	 * @ApiParam(name = JSON, value = "Other JSON paramaters", required =
	 * false) @RequestBody ObjectNode json) { LOGGER.debug(
	 * "Execute method : updateCommonGrid()"); Boolean flag = false; try { flag
	 * = this.commonGridService.updateCommonGrid(json);
	 * 
	 * } catch (Exception e) { LOGGER.error("Error in updateCommonGrid()", e);
	 * throw new HISRuntimeException("Error in updateCommonGrid()", e); } return
	 * flag; }
	 */

	/**
	 * Return Boolean
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	@ApiOperation(value = "load data table")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "requestBody", value = "load data table", required = true, paramType = "body") })
	@RequestMapping(value = "/grid/load", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> loadCommonDataGridJson(
			@ApiParam(name = JSON, value = "Other JSON paramaters", required = false) @RequestBody ObjectNode json) {
		Map<String, Object> map = null;
		try {

			map = this.commonGridService.loadCommonDataGridJson(json);

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error in loadCommonDataGridJson()", e);
			throw new CodelabRuntimeException("Error in loadCommonDataGridJson()", e);
		}

		return map;
	}

	/**
	 * Return Boolean
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 10, 2017
	 */
	@ApiOperation(value = "get data from data table")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "requestBody", value = "get data from data table", required = true, paramType = "body") })
	@RequestMapping(value = "/grid/get", method = RequestMethod.POST, produces = "application/json")
	public Map<String, Object> getDataForCommonDataGrid(
			@ApiParam(name = JSON, value = "Other JSON paramaters", required = false) @RequestBody ObjectNode json) {
		Map<String, Object> map = null;
		try {
			map = this.commonGridService.getDataForCommonDataGrid(json);

		} catch (Exception e) {
			LOGGER.error("Error in getDataForCommonDataGrid()", e);
			throw new CodelabRuntimeException("Error in getDataForCommonDataGrid()", e);
		}

		return map;
	}

	/**
	 * Return Boolean
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 22, 2017
	 */
	@ApiOperation(value = "get module")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "requestBody", value = "get module list", required = true, paramType = "body") })
	@RequestMapping(value = "/module/get", method = RequestMethod.POST, produces = "application/json")
	public List<Module> getModule(
			@ApiParam(name = JSON, value = "Other JSON paramaters", required = false) @RequestBody ObjectNode json) {
		List<Module> moduleList = null;
		try {

			moduleList = this.commonGridService.getModuleList(json);

		} catch (Exception e) {
			LOGGER.error("Error in getModule()", e);
			throw new CodelabRuntimeException("Error in getModule()", e);
		}

		return moduleList;
	}

	/**
	 * Return Boolean
	 * 
	 * @param json
	 * @return
	 * @author Vivek Jain
	 * @ModifiedBy Vivek Jain
	 * @ModifiedDate Mar 22, 2017
	 */
	@ApiOperation(value = "get module")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-Auth-Token", value = "Authorization token", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "requestBody", value = "get module list", required = true, paramType = "body") })
	@RequestMapping(value = "/subModule/get", method = RequestMethod.POST, produces = "application/json")
	public List<SubModule> getSubModule(
			@ApiParam(name = JSON, value = "Other JSON paramaters", required = false) @RequestBody ObjectNode json) {
		List<SubModule> subModuleList = null;
		try {

			subModuleList = this.commonGridService.getSubModuleList(json);

		} catch (Exception e) {
			LOGGER.error("Error in getSubModule()", e);
			throw new CodelabRuntimeException("Error in getSubModule()", e);
		}

		return subModuleList;
	}

	@ApiOperation(value = "grid export")
	@ApiImplicitParams({
			@ApiImplicitParam(name = XAUTHTOKEN, value = "Authorization token", required = true, dataType = STRING, paramType = HEADER),
			@ApiImplicitParam(name = REQUEST_BODY, value = "Document Data", required = true, paramType = "body") })
	@RequestMapping(value = "/export", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadImage(
			@ApiParam(name = JSON, value = "Other JSON paramaters", required = false) @RequestBody ObjectNode json,
			HttpServletResponse response) {
		LOGGER.debug("Execute export()");
		try {
			this.commonGridService.export(json, response);
		} catch (Exception ex) {
			LOGGER.error("Error in export()", ex);
		}
	}
}
