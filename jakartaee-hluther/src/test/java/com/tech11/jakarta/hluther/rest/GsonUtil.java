package com.tech11.jakarta.hluther.rest;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonUtil {

	public static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDate> {
		@Override
		public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return LocalDate.parse(json.getAsString());
		}
	}
	
	public static class LocalDateTimeSerializer implements JsonSerializer<LocalDate> {

		@Override
		public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	public static Gson createGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDate.class, new LocalDateTimeDeserializer());
		builder.registerTypeAdapter(LocalDate.class, new LocalDateTimeSerializer());
		return builder.create();
	}

	public static String toJson(Object object) {
		return createGson().toJson(object);
	}

	public static <T> T fromJson(String jsonString, Type responseType) {
		return createGson().fromJson(jsonString, responseType);

	}

}
