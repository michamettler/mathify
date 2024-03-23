package ch.zhaw.mathify.util;

import ch.zhaw.mathify.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class JsonMapper {
    private static final Logger LOG = LoggerFactory.getLogger(JsonMapper.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonMapper() {
    }

    /**
     * @param jsonString json string to map
     * @param clazz      class to map to
     * @param <T>        type of the class
     * @return mapped object
     * @throws Exception if the mapping fails
     */
    public static <T> List<T> Map(String jsonString, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static void writeJson(File file, List<User> users) {
        try {
            objectMapper.writeValue(file, users);
            LOG.info("users.json was written successfully");
        } catch (Exception e) {
            LOG.error("Could not write users.json!", e);
        }
    }
}
