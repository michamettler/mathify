package ch.zhaw.mathify.util;

import ch.zhaw.mathify.model.Settings;
import ch.zhaw.mathify.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class is used to map json strings to objects and vice versa.
 */
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
     * @throws JsonProcessingException if the mapping fails
     */
    public static <T> List<T> map(String jsonString, Class<T> clazz) throws JsonProcessingException {
        LOG.debug("Mapping json to object...");
        return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    /**
     * @param file  file to map
     * @param users list of users
     */
    public static void writeUsersToJson(File file, List<User> users) {
        LOG.debug("Writing users.json...");
        try {
            objectMapper.writeValue(file, users);
            LOG.info("users.json was written successfully");
        } catch (Exception e) {
            LOG.error("Could not write users.json!", e);
        }
    }

    /**
     * @param file file to map
     * @return new settings object
     */
    public static Settings readSettingsFromJson(File file) throws IOException {
        LOG.debug("Reading settings.json...");
        return objectMapper.readValue(file, Settings.class);
    }

    /**
     * @param object object to map
     * @return json string
     * @throws JsonProcessingException
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
