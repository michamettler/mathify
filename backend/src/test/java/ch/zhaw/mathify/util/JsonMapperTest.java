package ch.zhaw.mathify.util;

import ch.zhaw.mathify.UserSampleData;
import ch.zhaw.mathify.model.Settings;
import ch.zhaw.mathify.model.User;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the JsonMapper
 */
class JsonMapperTest {
    private List<User> jsonUsers;
    private UserSampleData userSampleData;

    @BeforeEach
    void setup() {
        userSampleData = new UserSampleData();
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("users.json")).getFile());
            String jsonString = Files.readString(file.toPath());
            jsonUsers = JsonMapper.map(jsonString, User.class);
        } catch (IOException e) {
            System.err.println("TEST-ERROR!");
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUserMapNotNull() {
        assertFalse(jsonUsers.isEmpty());
        assertTrue(jsonUsers.stream().allMatch(Objects::nonNull));
    }

    @Test
    void testUserObjectsCreated() {
        for (int i = 0; i < userSampleData.getSampleUsers().size(); i++) {
            assertEquals(userSampleData.getSampleUsers().get(i).getUsername(), jsonUsers.get(i).getUsername());
            assertEquals(userSampleData.getSampleUsers().get(i).getLevel(), jsonUsers.get(i).getLevel());
            assertEquals(userSampleData.getSampleUsers().get(i).getExperience(), jsonUsers.get(i).getExperience());
        }
    }

    @Test
    void testUserToJson() {
        for (int i = 0; i < userSampleData.getSampleUsers().size(); i++) {
            assertEquals(userSampleData.getSampleUsers().get(i).getUsername(), jsonUsers.get(i).getUsername());
            assertEquals(userSampleData.getSampleUsers().get(i).getLevel(), jsonUsers.get(i).getLevel());
            assertEquals(userSampleData.getSampleUsers().get(i).getExperience(), jsonUsers.get(i).getExperience());
        }
    }

    @Test
    void testGuids() {
        for (int i = 0; i < userSampleData.getSampleUsers().size(); i++) {
            assertTrue(jsonUsers.get(i).getGuid().matches("^[a-z0-9-]{36}$"));
        }
    }

    @Test
    void readSettingsFromJson() throws IOException {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("settings.json")).getFile());
        Settings settings = JsonMapper.readSettingsFromJson(file);
        assertNotNull(settings);
        assertEquals(8080, settings.getHttp().port());
        assertEquals(8443, settings.getHttps().port());
        assertEquals("/etc/ssl/mathifyCert.pem", settings.getHttps().cert());
        assertEquals("/etc/ssl/mathifyCertKey.pem", settings.getHttps().key());
    }

    @Test
    void readSettingsThrows(){
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("settings_empty.json")).getFile());
        assertThrows(JsonMappingException.class, () -> JsonMapper.readSettingsFromJson(file));
    }
}
