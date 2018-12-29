package com.codelab.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codelab.beans.CommonGridSetUp;
import com.codelab.beans.Module;
import com.codelab.beans.SubModule;
import com.codelab.common.CommonGridCommon;
import com.codelab.common.Constant;
import com.codelab.common.QueryParams;
import com.codelab.common.QueryParams.QueryConstants;
import com.codelab.service.CommonGridService;
import com.codelab.service.GenericDao;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class CommonGridServiceImpl implements CommonGridService, Constant {
	static final Logger LOGGER = LoggerFactory.getLogger(CommonGridServiceImpl.class);
	@Autowired
	GenericDao<CommonGridSetUp> commonGridSetUpDao;
	/*
	 * @Autowired GenericDao<CommonGridFlexibleWorkFlow>
	 * commonGridFlexibleWorkFlowDao;
	 */
	@Autowired
	GenericDao<Module> moduleDao;
	@Autowired
	GenericDao<SubModule> subModuleDao;
	@Autowired
	CommonGridCommon commonGridCommon;
	@Autowired
	private CommonGridExportManager commonGridExportManager;

	@Override
	@Transactional
	public CommonGridSetUp saveCommonGridSetUpJson(ObjectNode subModuleJson) throws Exception {
		CommonGridSetUp commonGridSetUp = new CommonGridSetUp();

		String json = subModuleJson.get("json").toString();
		Long moduleId = subModuleJson.get("moduleId").asLong();
		Long subModuleId = subModuleJson.get("subModuleId").asLong();
		String fromClause = subModuleJson.get(FROMCLAUSE).asText();

		if (subModuleJson.get("commonGridSetUpId") != null) {
			Long commonGridSetUpId = subModuleJson.get("commonGridSetUpId").asLong();
			commonGridSetUp.setCommonGridSetUpId(commonGridSetUpId);
		}

		commonGridSetUp.setFromClause(fromClause);
		commonGridSetUp.setJson(json);
		commonGridSetUp.setModuleId(moduleId);
		commonGridSetUp.setSubModuleId(subModuleId);

		commonGridSetUp = this.commonGridSetUpDao.save(commonGridSetUp);

		return commonGridSetUp;
	}

	@Override
	public CommonGridSetUp getCommonGridSetUpJson(ObjectNode json) throws Exception {
		CommonGridSetUp commonGridSetUp;
		List<QueryParams> queryParamList = new ArrayList<>();

		Long subModuleId = json.get("subModuleId").asLong();
		queryParamList.add(new QueryParams(SUBMODULEID, QueryConstants.EQ, subModuleId));

		List<CommonGridSetUp> commonGridSetUpList = this.commonGridSetUpDao.getListByCriteria(CommonGridSetUp.class,
				null, queryParamList, false);
		if (commonGridSetUpList != null && !commonGridSetUpList.isEmpty()) {
			commonGridSetUp = commonGridSetUpList.get(0);
		} else {
			commonGridSetUp = new CommonGridSetUp();
		}
		return commonGridSetUp;
	}

	/*
	 * @Override public CommonGridFlexibleWorkFlow saveCommonGrid(ObjectNode
	 * json) throws Exception { CommonGridFlexibleWorkFlow
	 * commonGridFlexibleWorkFlow = new CommonGridFlexibleWorkFlow();
	 * 
	 * String mainJson = json.get("mainJson").toString(); String quickLink =
	 * json.get("quickLink").asText(); Long moduleId =
	 * json.get(MODULEID).asLong(); String description =
	 * json.get("description").asText(); Long subModuleId =
	 * json.get(SUBMODULEID).asLong(); String practice =
	 * json.get("practice").asText(); String users = json.get("users").asText();
	 * Integer isCommonGrid = json.get(ISCommonGrid).asInt();
	 * 
	 * if (json.get(FLEXIBLEWORKFLOWID) != null) { Long flexibleWorkFlowID =
	 * json.get(FLEXIBLEWORKFLOWID).asLong();
	 * commonGridFlexibleWorkFlow.setFlexibleWorkFlowID(flexibleWorkFlowID); }
	 * 
	 * commonGridFlexibleWorkFlow.setModuleId(moduleId);
	 * commonGridFlexibleWorkFlow.setSubModuleId(subModuleId);
	 * commonGridFlexibleWorkFlow.setFlexibleWorkFlowJSON(mainJson);
	 * commonGridFlexibleWorkFlow.setQuickLink(quickLink);
	 * commonGridFlexibleWorkFlow.setDescription(description);
	 * commonGridFlexibleWorkFlow.setPractice(practice);
	 * commonGridFlexibleWorkFlow.setUsers(users);
	 * commonGridFlexibleWorkFlow.setIsCommonGrid(isCommonGrid);
	 * commonGridFlexibleWorkFlow.setDateModified(new Date());
	 * 
	 * commonGridFlexibleWorkFlow =
	 * this.commonGridFlexibleWorkFlowDao.save(commonGridFlexibleWorkFlow);
	 * 
	 * return commonGridFlexibleWorkFlow; }
	 */

	/*
	 * @Override public List<CommonGridFlexibleWorkFlow>
	 * getCommonGrid(ObjectNode json) throws Exception { Integer isCommonGrid =
	 * 0; List<QueryParams> queryParamList = new ArrayList<>();
	 * 
	 * if (json.get(FLEXIBLEWORKFLOWID) != null) { Long flexibleWorkFlowID =
	 * json.get(FLEXIBLEWORKFLOWID).asLong(); queryParamList.add(new
	 * QueryParams(FLEXIBLEWORKFLOWID, QueryConstants.EQ, flexibleWorkFlowID));
	 * 
	 * } else { if (json.get(ISCommonGrid) != null) { isCommonGrid =
	 * json.get(ISCommonGrid).asInt(); } queryParamList.add(new
	 * QueryParams(ISCommonGrid, QueryConstants.EQ, isCommonGrid));
	 * 
	 * if (json.get(SUBMODULEID) != null) { Long subModuleId =
	 * json.get(SUBMODULEID).asLong(); queryParamList.add(new
	 * QueryParams(SUBMODULEID, QueryConstants.EQ, subModuleId)); }
	 * 
	 * if (json.get(MODULEID) != null) { Long moduleId =
	 * json.get(MODULEID).asLong(); queryParamList.add(new QueryParams(MODULEID,
	 * QueryConstants.EQ, moduleId)); } }
	 * 
	 * return this.commonGridFlexibleWorkFlowDao.getListByCriteria(
	 * CommonGridFlexibleWorkFlow.class, null, queryParamList); }
	 */

	/*
	 * @Override public Boolean updateCommonGrid(ObjectNode json) throws
	 * Exception { Map<String, Object> map = new HashMap<>(); Long
	 * flexibleWorkFlowID = json.get(FLEXIBLEWORKFLOWID).asLong(); Integer
	 * isCommonGrid = json.get(ISCommonGrid).asInt();
	 * 
	 * map.put(ISCommonGrid, isCommonGrid); map.put(FLEXIBLEWORKFLOWID,
	 * flexibleWorkFlowID);
	 * 
	 * StringBuilder query = new StringBuilder(
	 * "UPDATE CommonGridFlexibleWorkFlow set isCommonGrid = :isCommonGrid where flexibleWorkFlowID = :flexibleWorkFlowID"
	 * );
	 * 
	 * Integer i =
	 * this.commonGridFlexibleWorkFlowDao.executeUpdate(query.toString(), map);
	 * 
	 * if (i > 0) { return true; }
	 * 
	 * return false; }
	 */

	@Override
	public Map<String, Object> loadCommonDataGridJson(ObjectNode objectNode) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<QueryParams> queryParamList;

		Long subModuleId = objectNode.get(SUBMODULEID).asLong();
		Long moduleId = objectNode.get(MODULEID).asLong();
		// Integer isCommonGrid = objectNode.get(ISCommonGrid).asInt();

		/* Start : for fetch setup level json and from clause */
		queryParamList = new ArrayList<>();
		queryParamList.add(new QueryParams(SUBMODULEID, QueryConstants.EQ, subModuleId));
		queryParamList.add(new QueryParams(MODULEID, QueryConstants.EQ, moduleId));

		CommonGridSetUp commonGridSetUp = this.commonGridSetUpDao
				.getListByCriteria(CommonGridSetUp.class, null, queryParamList, false).get(0);
		/* End : for fetch setup level json and from clause */

		/* Start : for fetch commonGrid level json */
		/*
		 * queryParamList = new ArrayList<>(); queryParamList.add(new
		 * QueryParams(SUBMODULEID, QueryConstants.EQ, subModuleId));
		 * queryParamList.add(new QueryParams(MODULEID, QueryConstants.EQ,
		 * moduleId)); queryParamList.add(new QueryParams(ISCommonGrid,
		 * QueryConstants.EQ, isCommonGrid));
		 * 
		 * CommonGridFlexibleWorkFlow flexibleWorkFlow =
		 * this.commonGridFlexibleWorkFlowDao
		 * .getListByCriteria(CommonGridFlexibleWorkFlow.class, null,
		 * queryParamList).get(0);
		 */
		/* End : for fetch setup level json and from clause */

		String fromClause = commonGridSetUp.getFromClause();

		// JsonNode commonGridNode = getJsonNodeFromString(json);
		JsonNode commonGridSetUpNode = getJsonNodeFromString(commonGridSetUp.getJson());
		JsonNode commonGridSetupItemsNode = commonGridSetUpNode.get("gridSetup");

		Boolean distinctResult = commonGridSetupItemsNode != null
				&& commonGridSetupItemsNode.get(DISTINCTRESULT) != null
						? commonGridSetupItemsNode.get(DISTINCTRESULT).asBoolean() : false;

		StringBuilder selectClause = createSelectClause(commonGridSetUpNode.get(COLUMNS),
				commonGridSetUpNode.get(ADDITIONALCOLUMNS), distinctResult);
		StringBuilder query = new StringBuilder();

		if (distinctResult) {
			query.append(selectClause.toString().replace("SELECT ", "Select Count(")).append(") ");

		} else {
			query.append("select count(1) as totalCount ");
		}
		query.append(fromClause);

		StringBuilder whereClause = createWhereClause(objectNode, commonGridSetUpNode);
		if (whereClause.length() > 0) {
			query.append(WHERE).append(whereClause);
		}

		List<Map<String, Object>> totalCount = this.commonGridSetUpDao.executeSQLSelectMap(query.toString(), null);

		map.put("selectClause", selectClause);
		map.put(FROMCLAUSE, fromClause);
		map.put("orderClause", "");
		map.put("whereClause", whereClause);
		map.put("columns", commonGridSetUpNode.get(COLUMNS));
		map.put("gridSetUp", commonGridSetUpNode.get("gridSetup"));
		// map.put("setUpColumns", commonGridSetUpNode.get(COLUMNS));
		map.put("additionalColumns", commonGridSetUpNode.get(ADDITIONALCOLUMNS));
		map.put("totalCount", totalCount.get(0).get("totalCount"));
		// map.put("actions", commonGridSetUpNode.get("Actions"));

		return map;
	}

	@Override
	public Map<String, Object> getDataForCommonDataGrid(ObjectNode objectNode) throws Exception {
		List<QueryParams> queryParamList;
		Long subModuleId = objectNode.get(SUBMODULEID).asLong();
		Long moduleId = objectNode.get(MODULEID).asLong();
		Boolean forDashboard = objectNode.get("forDashboard") != null ? objectNode.get("forDashboard").asBoolean()
				: false;

		queryParamList = new ArrayList<>();
		queryParamList.add(new QueryParams(SUBMODULEID, QueryConstants.EQ, subModuleId));
		queryParamList.add(new QueryParams(MODULEID, QueryConstants.EQ, moduleId));

		CommonGridSetUp commonGridSetUp = this.commonGridSetUpDao
				.getListByCriteria(CommonGridSetUp.class, null, queryParamList, false).get(0);

		String fromClause = commonGridSetUp.getFromClause();

		JsonNode commonGridSetUpNode = getJsonNodeFromString(commonGridSetUp.getJson());
		JsonNode commonGridSetupItemsNode = commonGridSetUpNode.get("gridSetup");
		Boolean distinctResult = commonGridSetupItemsNode != null
				&& commonGridSetupItemsNode.get(DISTINCTRESULT) != null
						? commonGridSetupItemsNode.get(DISTINCTRESULT).asBoolean() : false;

		StringBuilder selectClause = createSelectClause(commonGridSetUpNode.get(COLUMNS),
				commonGridSetUpNode.get(ADDITIONALCOLUMNS), distinctResult);

		Map<String, Object> gridMap = new HashMap<>();
		Map<String, Object> column = null;
		List<Map<String, Object>> gridData;

		StringBuilder whereClause = createWhereClause(objectNode, commonGridSetUpNode);
		Integer start = objectNode.get("start").asInt();
		Integer max = objectNode.get("max").asInt();
		String globalFilter = objectNode.get("globalFilter").asText();

		StringBuilder query = new StringBuilder();
		query.append(selectClause).append(" ").append(fromClause);

		if (whereClause.length() > 0) {
			query.append(WHERE).append(whereClause);
		}

		if (globalFilter.trim().length() > 0) {
			JsonNode colArray = objectNode.get(COLUMNS);

			StringBuilder orClause = createOrClause(colArray);

			column = new HashMap<>();
			column.put("globalFilter", "%" + globalFilter + "%");

			if (orClause.length() > 0) {
				if (whereClause.length() <= 0) {
					query.append(WHERE).append(orClause);
				} else {
					query.append(" and (").append(orClause).append(")");
				}
			}

			StringBuilder countQuery = new StringBuilder("select count(1) as totalFilterCount ");
			countQuery.append(fromClause).append(" ").append(query.substring(query.indexOf(WHERE), query.length()));

			List<Map<String, Object>> totalFilterCount = this.commonGridSetUpDao
					.executeSQLSelectMap(countQuery.toString(), column);
			gridMap.put("totalFilterCount", totalFilterCount.get(0).get("totalFilterCount"));
		}

		query = appendOrderClause(objectNode, query);

		if (forDashboard) {
			query.append(" LIMIT ").append(max).append(" OFFSET ").append(start);
		}

		gridData = this.commonGridSetUpDao.executeSQLSelectMap(query.toString(), column);

		gridMap.put("gridData", gridData);

		return gridMap;
	}

	private StringBuilder appendOrderClause(ObjectNode objectNode, StringBuilder query) {
		String orderClause = objectNode.get("orderClause") != null ? objectNode.get("orderClause").asText() : "";
		String groupBy = objectNode.get("groupBy") != null ? objectNode.get("groupBy").asText() : "";
		String orderCol = objectNode.get("orderCol") != null ? objectNode.get("orderCol").asText() : "";

		if (orderCol.length() > 0) {
			String orderDir = objectNode.get("orderDir").asText();

			if (!"both".equals(orderDir)) {
				query.append(ORDER_BY).append(orderCol).append(" ").append(orderDir);
			}

		} else {
			query.append(" ").append(orderClause);
		}

		if (groupBy.length() > 0) {
			if (query.indexOf(ORDER_BY) > 0) {
				String orderBy = query.substring(query.indexOf(ORDER_BY) + 10).toLowerCase();

				if (orderBy.indexOf(groupBy.toLowerCase() + " desc") >= 0) {
					query = new StringBuilder(query.substring(0, query.indexOf(ORDER_BY)));
					query.append(ORDER_BY).append(groupBy).append(" desc");

				} else if (orderBy.indexOf(groupBy.toLowerCase() + " asc") >= 0) {
					query = new StringBuilder(query.substring(0, query.indexOf(ORDER_BY)));
					query.append(ORDER_BY).append(groupBy).append(" asc");

				} else {
					query = new StringBuilder(query.toString().replace(ORDER_BY, ORDER_BY + " " + groupBy + " asc, "));
				}
			} else {
				query.append(ORDER_BY).append(groupBy).append(" asc");
			}
		}

		if (query.indexOf(ORDER_BY) <= 0) {
			query.append(" ORDER BY 1 ");
		}

		return query;
	}

	private StringBuilder createOrClause(JsonNode colArray) {
		StringBuilder orClause = new StringBuilder();

		for (int i = 0; i < colArray.size(); i++) {
			JsonNode column = colArray.get(i);

			String columnStr = getColumn(column).toString();

			int indexofAs = columnStr.indexOf(" as ");
			if (indexofAs > 0) {
				columnStr = columnStr.substring(0, indexofAs);
			}

			orClause.append(" or ").append(columnStr).append(" like :globalFilter").append(" ");
		}

		if (orClause.length() > 0) {
			orClause = new StringBuilder(" 1 = 2 ").append(orClause);
		}

		return orClause;
	}

	private StringBuilder createSelectClause(JsonNode columnArray, JsonNode additionalColumnArray,
			boolean distinctResult) throws JsonMappingException, IOException {

		StringBuilder columnString = new StringBuilder("SELECT ");
		columnString = distinctResult ? columnString.append(" Distinct ") : columnString;
		StringBuilder orderString = new StringBuilder();

		SortedMap<Integer, String> orderMap = new TreeMap<>();

		for (int i = 0; i < columnArray.size(); i++) {
			JsonNode column = columnArray.get(i);

			if (i > 0) {
				columnString.append(", ");
			}

			columnString.append(getColumn(column));

			if (column.get("Order") != null) {

				String order = column.get("Order").asText();
				if (order.trim().length() > 0) {
					String columnOrder = column.get("ColumnName").asText() + " " + column.get(ORDER).asText();
					orderMap.put(column.get("orderNo").asInt(), columnOrder);
				}
			}

		}

		for (Map.Entry<Integer, String> entry : orderMap.entrySet()) {
			orderString.append(entry.getValue()).append(",");
		}

		if (orderString.length() > 0) {
			int j = orderString.lastIndexOf(",");
			j = j <= 0 ? 0 : j;

			orderString = new StringBuilder();
			orderString.append("ORDER BY ").append(orderString.substring(0, j));
		}

		for (int i = 0; i < additionalColumnArray.size(); i++) {
			JsonNode column = additionalColumnArray.get(i);

			columnString.append(", ");
			columnString.append(getColumn(column));
		}

		return columnString;
	}

	private StringBuilder createWhereClause(ObjectNode objectNode, JsonNode commonGridSetUpNode) throws Exception {
		StringBuilder whereClause = this.commonGridCommon.createPreDefinedCondition(objectNode);
		return whereClause;
	}

	private StringBuilder getColumn(JsonNode column) {
		StringBuilder columnString = new StringBuilder();
		String type = "";
		String tableName = "";
		String columnName = column.get("ColumnName").asText();

		if (column.get("Type") != null) {
			type = column.get("Type").asText().trim();
		}

		if ("ManualConfig".equals(type)) {
			columnString.append(column.get("ManualConfig").asText()).append(" as ").append(columnName);
		} else {
			if (column.get("TableName") != null) {
				tableName = column.get("TableName").asText().trim();
			}

			if (tableName.length() > 0) {
				columnString.append(tableName).append(".").append(columnName);
			} else {
				columnString.append(columnName);
			}
		}

		return columnString;
	}

	public JsonNode getJsonNodeFromString(String json) throws JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readTree(json);
	}

	public JsonNode getJsonNodeArrayString(String json, String key) throws JsonMappingException, IOException {
		JsonNode jsonNode = getJsonNodeFromString(json);

		return jsonNode.get(key);
	}

	@Override
	public List<Module> getModuleList(ObjectNode objectNode) throws Exception {
		return this.moduleDao.getListByCriteria(Module.class, null, null, false);
	}

	@Override
	public List<SubModule> getSubModuleList(ObjectNode objectNode) throws Exception {
		List<QueryParams> queryParamList = new ArrayList<>();
		Long moduleId = objectNode.get(MODULEID).asLong();
		queryParamList.add(new QueryParams(MODULEID, QueryConstants.EQ, moduleId));

		return this.subModuleDao.getListByCriteria(SubModule.class, null, queryParamList, false);
	}

	@Override
	public void export(ObjectNode objectNode, HttpServletResponse response) throws Exception {

		List<QueryParams> queryParamList;
		Long subModuleId = objectNode.get(SUBMODULEID).asLong();
		Long moduleId = objectNode.get(MODULEID).asLong();
		String export = objectNode.get("export").asText();

		queryParamList = new ArrayList<>();
		queryParamList.add(new QueryParams(SUBMODULEID, QueryConstants.EQ, subModuleId));
		queryParamList.add(new QueryParams(MODULEID, QueryConstants.EQ, moduleId));

		CommonGridSetUp commonGridSetUp = this.commonGridSetUpDao
				.getListByCriteria(CommonGridSetUp.class, null, queryParamList, false).get(0);

		String fromClause = commonGridSetUp.getFromClause();

		JsonNode commonGridSetUpNode = getJsonNodeFromString(commonGridSetUp.getJson());
		JsonNode commonGridSetupItemsNode = commonGridSetUpNode.get("gridSetup");
		Boolean distinctResult = commonGridSetupItemsNode != null
				&& commonGridSetupItemsNode.get(DISTINCTRESULT) != null
						? commonGridSetupItemsNode.get(DISTINCTRESULT).asBoolean() : false;

		StringBuilder selectClause = createSelectClause(commonGridSetUpNode.get(COLUMNS),
				commonGridSetUpNode.get(ADDITIONALCOLUMNS), distinctResult);

		Map<String, Object> column = null;
		List<Map<String, Object>> gridData;

		StringBuilder whereClause = createWhereClause(objectNode, commonGridSetUpNode);

		StringBuilder query = new StringBuilder();
		query.append(selectClause).append(" ").append(fromClause);

		if (whereClause.length() > 0) {
			query.append(WHERE).append(whereClause);
		}

		query = appendOrderClause(objectNode, query);

		gridData = this.commonGridSetUpDao.executeSQLSelectMap(query.toString(), column);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("response", response);
		map.put("listObj", gridData);
		map.put("columnArray", commonGridSetUpNode.get(COLUMNS));
		map.put("moduleName", "Export");

		if("PDF".equals(export)){
			this.commonGridExportManager.exportToPdf(map);
			
		}else if("EXCEL".equals(export)){
			this.commonGridExportManager.exportToExcel(map);
		
		}else if("CSV".equals(export)){
			this.commonGridExportManager.exportToCsv(map);
		}

	}
}
