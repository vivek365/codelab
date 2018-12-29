package com.codelab.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil implements Constant {
	static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);


	public static boolean hasRequiredParams(ObjectNode json, String[] params) throws Exception {
		LOGGER.debug("Execute method : hasRequiredParams()");
		boolean flag = true;
		for (String param : params) {
			if (null == json.get(param)) {
				flag = false;
				break;
			}

		}
		return flag;
	}

	/**
	 * For ex: { "bean":{"data":1,"data1":}} then you can check for data1 also, will return true of value is null or parameter is not passed.
	 * 
	 * @param json
	 * @param params
	 * @return
	 * @throws Exception
	 * @author BRanpariya
	 * @ModifiedBy BRanpariya
	 * @ModifiedDate Apr 21, 2017
	 */
	public static boolean hasRequiredParamsFromChild(ObjectNode json, String[] params) throws Exception {
		LOGGER.debug("Execute method : hasRequiredParams()");
		boolean flag = true;
		for (String param : params) {
			if (null == json.findValue(param) || "".equals(json.findValue(param).asText())) {
				flag = false;
				break;
			}
		}
		return flag;
	}
}
