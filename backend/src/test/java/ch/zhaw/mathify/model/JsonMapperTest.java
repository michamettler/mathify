package ch.zhaw.mathify.model;

import ch.zhaw.mathify.util.JsonMapper;
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
    private final List<User> users = List.of(
            new User("john_doe", "jd@gmail.com", "$2a$12$r88eOP26jtTyTcPYPyhbdOEZfkHS.2cqvSoZpDRp97eLix86bZOsW"),
            new User("jane_smith", "js@mail.com", "$2a$12$GYcDJSLSQtuyyGqL3Ksuh.w14csWK5V4HIsDSiaXAhl0s4d3u2yke"),
            new User("alex_jones", "abc@xmail.com", "$2a$12$gllQ3ezTJf22FBzmAwSx6e0w0NsKVia6vetDL99TG6X4SjtOgLtfi"),
            new User("sarah_jackson", "sjackson@mail.com", "$2a$12$MS/3FWRSmL1zhc2ryLeXz.eOSSnFvMtVohuiNA05cpS/cquKnH9SW"),
            new User("michael_brown", "mail@mail.com", "$2a$12$Nu.X7I1eq4lBWa9u6soIO.OLRbTzQXpztgNvNRjn9f6ZTu1gAq26a")
    );
    private List<User> jsonUsers;
    @BeforeEach
    void setup() {
        users.get(0).setLevel(10);
        users.get(0).setExperience(55);
        users.get(1).setLevel(8);
        users.get(1).setExperience(44);
        users.get(2).setLevel(12);
        users.get(2).setExperience(33);
        users.get(3).setLevel(6);
        users.get(3).setExperience(22);
        users.get(4).setLevel(15);
        users.get(4).setExperience(11);
        try{
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("users.json")).getFile());
            String jsonString = Files.readString(file.toPath());
            jsonUsers = JsonMapper.Map(jsonString, User.class);
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
    void testUserObjectsCreated(){
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getUsername(), jsonUsers.get(i).getUsername());
            assertEquals(users.get(i).getLevel(), jsonUsers.get(i).getLevel());
            assertEquals(users.get(i).getExperience(), jsonUsers.get(i).getExperience());
        }
    }

    @Test
    void testUserToJson() {
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getUsername(), jsonUsers.get(i).getUsername());
            assertEquals(users.get(i).getLevel(), jsonUsers.get(i).getLevel());
            assertEquals(users.get(i).getExperience(), jsonUsers.get(i).getExperience());
        }
    }

    @Test
    void testGuids() {
        for (int i = 0; i < users.size(); i++) {
            assertTrue(jsonUsers.get(i).getGuid().matches("^[a-z0-9-]{36}$"));
        }
    }
}
