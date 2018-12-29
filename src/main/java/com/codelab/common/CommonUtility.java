package com.codelab.common;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.codelab.beans.User;
import com.codelab.beans.UserContext;
import com.codelab.configuration.CodelabRuntimeException;
import com.codelab.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CommonUtility {
	static final Logger LOGGER = LoggerFactory.getLogger(CommonUtility.class);
	@Autowired
	private static Environment env;

	public static DTOEntityMapper dtoEntityMapper = null;
	static {
		if (dtoEntityMapper == null) {
			dtoEntityMapper = new DTOEntityMapper();
		}
	}

	public static DTOEntityMapper getDTOEntityMapperInstance() {
		return ObjUtillity.isNotBlank(dtoEntityMapper) ? dtoEntityMapper : new DTOEntityMapper();
	}

	/**
	 * Gets the gson.
	 *
	 * @return the gson
	 */
	public static Gson getGson() {
		final GsonBuilder builder = new GsonBuilder();
		final JsonSerializer<Date> ser = new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(final Date src, final Type typeOfSrc, final JsonSerializationContext context) {
				return src == null ? null : new JsonPrimitive(src.getTime());
			}
		};
		// Register an adapter to manage the date types as long values
		builder.registerTypeAdapter(Date.class, new JsonDeserializer<Object>() {
			@Override
			public Date deserialize(final JsonElement json, final Type typeOfT,
					final JsonDeserializationContext context) throws JsonParseException {
				return new Date(json.getAsJsonPrimitive().getAsLong());
			}
		}).setDateFormat(DateFormat.LONG).registerTypeAdapter(Date.class, ser);
		return builder.create();
	}

	public static User getContextUser() {
		LOGGER.debug("Execute method : getContextUser()");
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				UserContext userContext = (UserContext) authentication.getPrincipal();
				if (userContext != null) {
					return userContext.getUser();
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in getContextUser() method :: " + e);
			throw new CodelabRuntimeException("Error in getContextUser()", e);
		}
		return null;
	}
}
