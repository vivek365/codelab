package com.codelab.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codelab.beans.CommonGridSetUp;
import com.codelab.common.QueryParams.QueryConstants;
import com.codelab.service.GenericDao;
import com.codelab.service.impl.CommonGridServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class CommonGridCommon implements Constant {
	static final Logger LOGGER = LoggerFactory.getLogger(CommonGridServiceImpl.class);

	@Autowired
	GenericDao<CommonGridSetUp> commonGridSetUpDao;

	public StringBuilder createPreDefinedCondition(ObjectNode objectNode) throws Exception {
		JsonNode moduleData = objectNode.get("moduleData");
		JsonNode otherData = objectNode.get("otherData");
		JsonNode orData = objectNode.get("orData");
		Long subModuleId = objectNode.get(SUBMODULEID).asLong();

		StringBuilder predefinedCondition = new StringBuilder();
		StringBuilder moduleDataCondition;
		StringBuilder orDataCondition;

		if (otherData != null && otherData.size() > 0) {
			predefinedCondition = createWhereClauseFromOtherData(otherData, subModuleId);
		}

		if (moduleData != null && moduleData.size() > 0) {
			moduleDataCondition = createWhereClauseFromModuleData(moduleData);

			if (moduleDataCondition.length() > 0 && predefinedCondition.length() > 0) {
				predefinedCondition.append(" and ").append(moduleDataCondition);
			} else {
				predefinedCondition = moduleDataCondition;
			}
		}

		if (orData != null && orData.size() > 0) {
			orDataCondition = createWhereClauseFromOrData(orData);

			if (orDataCondition.length() > 0 && predefinedCondition.length() > 0) {
				predefinedCondition.append(" and ").append(orDataCondition);
			} else {
				predefinedCondition = orDataCondition;
			}
		}

		if (predefinedCondition.length() > 0) {
			return predefinedCondition;
		}

		return new StringBuilder("");
	}

	private StringBuilder createWhereClauseFromOtherData(JsonNode otherData, Long subModuleId) throws Exception {
		StringBuilder predefinedCondition = new StringBuilder();

		StringBuilder query = new StringBuilder("");
		query.append("Select m.moduleName as moduleName, sm.subModuleName as subModuleName ")
				.append("from SubModule sm inner join sm.module as m where sm.subModuleId = :subModuleId");

		Map<String, Object> column = new HashMap<>();
		column.put(SUBMODULEID, subModuleId);
		column.put(ALIAS, CommonGridSetUp.class);

		List<CommonGridSetUp> list = this.commonGridSetUpDao.executeHQLSelect(query.toString(), column);
		if (list != null && !list.isEmpty()) {
			String moduleName = list.get(0).getModuleName();
			String subModuleName = list.get(0).getSubModuleName();

			predefinedCondition = callModuleLevelMethod(otherData, moduleName, subModuleName);

		}

		return predefinedCondition;
	}

	private StringBuilder createWhereClauseFromModuleData(JsonNode moduleData) {
		StringBuilder predefinedCondition = new StringBuilder(" 1=1 ");

		for (JsonNode data : moduleData) {

			String columnName = data.get(0).asText();
			String condition = data.get(1).asText();
			JsonNode value = data.get(2);
			predefinedCondition.append(AND).append(columnName);
			QueryConstants con = QueryConstants.valueOf(condition);

			switch (con) {
			case EQ:
				predefinedCondition.append(" = '").append(value.asText()).append(QUOTES);
				break;

			case NE:
				predefinedCondition.append(" <> '").append(value.asText()).append(QUOTES);
				break;

			case STARTS_WITH:
				predefinedCondition.append(" like '").append(value.asText()).append("%'");
				break;

			case LIKE:
				predefinedCondition.append(" like '%").append(value.asText()).append("%'");
				break;

			case GT:
				predefinedCondition.append(" > '").append(value.asText()).append(QUOTES);
				break;

			case LT:
				predefinedCondition.append(" < '").append(value.asText()).append(QUOTES);
				break;

			case GE:
				predefinedCondition.append(" >= '").append(value.asText()).append(QUOTES);
				break;

			case LE:
				predefinedCondition.append(" <= '").append(value.asText()).append(QUOTES);
				break;
			case BETWEEN:
				predefinedCondition.append(" between '").append(value.get(0).asText()).append("' and '")
						.append(value.get(1).asText()).append(QUOTES);
				break;

			case IN:
			case NOT_IN:
				List<String> array = new ArrayList<>();
				String condtionType = "IN".equals(condition) ? " in " : " not in ";

				for (JsonNode js : value) {
					array.add(QUOTES + js.asText() + QUOTES);
				}

				String v = String.join(", ", array);

				predefinedCondition.append(condtionType).append(" (").append(v).append(")");
				break;

			default:
				break;
			}

		}

		return predefinedCondition;
	}

	private StringBuilder createWhereClauseFromOrData(JsonNode orData) {
		StringBuilder orDataCondition = new StringBuilder(" (1=2 ");

		for (JsonNode data : orData) {
			orDataCondition.append(" OR ").append(" (").append(createWhereClauseFromModuleData(data)).append(")");
		}
		orDataCondition.append(")");

		return orDataCondition;
	}

	private StringBuilder callModuleLevelMethod(JsonNode otherData, String moduleName, String subModuleName) {
		StringBuilder predefinedCondition = new StringBuilder("");

		if ("Appointment".equals(moduleName)) {
			predefinedCondition = appointmentWhereClause(otherData);

		} else if ("Search Custom Drug".equals(subModuleName)) {
			predefinedCondition = searchDrugWhereClause(otherData);
		}
		return predefinedCondition;
	}

	private StringBuilder appointmentWhereClause(JsonNode otherData) {
		StringBuilder predefinedCondition = new StringBuilder("");
		String clinicCode = otherData.get(CLINICCODE).asText();

		predefinedCondition.append(" AppointmentTable.ClinicCode='").append(clinicCode).append(QUOTES);
		return predefinedCondition;
	}

	private StringBuilder searchDrugWhereClause(JsonNode otherData) {
		StringBuilder predefinedCondition = new StringBuilder("");
		String clinicCode = otherData.get(CLINICCODE).asText();

		predefinedCondition.append(" AppointmentTable.ClinicCode='").append(clinicCode).append(QUOTES);
		return predefinedCondition;
	}

}
