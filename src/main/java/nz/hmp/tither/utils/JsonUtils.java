package nz.hmp.tither.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {
	
	public static String convertToJson(Object obj) 
			throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
	
	public static Object convertFromJson(String json, Class<?> clazz) 
			throws IOException {
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
	            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
	            .readValue(json, clazz);
	}
}
