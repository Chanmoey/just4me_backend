package com.gxu.just4me.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxu.just4me.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chanmoey
 */
public class MapAndJson implements AttributeConverter<Map<String, Object>, String> {

    @Autowired
    ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {

        try {
            return this.mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException(9999);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null) {
                return null;
            }
            return this.mapper.readValue(dbData, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException(9999);
        }
    }
}
