package ch.zhaw.mathify.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonMapper {
    private JsonMapper() {
    }

    public static <T> List<T> Map(String jsonString, Class<T> clazz) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                        jsonString,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
                );
    }
}
