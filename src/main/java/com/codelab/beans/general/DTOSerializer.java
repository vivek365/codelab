package com.codelab.beans.general;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codelab.common.CommonUtility;
import com.codelab.common.DTOEntityMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

public class DTOSerializer extends JsonDeserializer<AbstractDTO> {
	private final Logger logger = LoggerFactory.getLogger(DTOSerializer.class);

	@Override
	public AbstractDTO deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		final DTOEntityMapper dtoEntityMapper = CommonUtility.getDTOEntityMapperInstance();
		this.logger.error("Into serializer ");
		final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		node.toString();
		final Boolean hasType = node.has("mapTo");
		if (!hasType) {
			throw new IOException("Incorrect input. attribute 'mapTo' is mandatory");
		}
		final String c = node.get("mapTo").asText();
		@SuppressWarnings("rawtypes")
		final Class classType = dtoEntityMapper.getDTOMapping(c);
		final Gson gson = CommonUtility.getGson();
		@SuppressWarnings("unchecked")
		final AbstractDTO dto = (AbstractDTO) gson.fromJson(node.toString(), classType);
		dto.setMapTo(c);
		return dto;
	}
}
