package ch.zhaw.mathify.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonMapper {
    private JsonMapper() {
    }

    /**
     * @param jsonString json string to map
     * @param clazz      class to map to
     * @param <T>        type of the class
     * @return           mapped object
     * @throws Exception if the mapping fails
     */
    public static <T> List<T> Map(String jsonString, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                        jsonString,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, clazz)
                );
    }
}
