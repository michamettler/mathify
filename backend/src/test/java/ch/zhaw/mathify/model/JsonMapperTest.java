package ch.zhaw.mathify.model;

import ch.zhaw.mathify.util.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the JsonMapper
 */
class JsonMapperTest {
    private final List<User> users = List.of(
            new User("john_doe", "jd@gmail.com", "password"),
            new User("jane_smith", "js@mail.com", "admin123"),
            new User("alex_jones", "abc@xmail.com", "password123"),
            new User("sarah_jackson", "sjackson@mail.com", "password123"),
            new User("michael_brown", "mail@mail.com", "password123")
    );
    private List<User> jsonUsers;
    @BeforeEach
    void setup() {
        users.get(0).setLevel(10);
        users.get(0).setExp(55);
        users.get(1).setLevel(8);
        users.get(1).setExp(44);
        users.get(2).setLevel(12);
        users.get(2).setExp(33);
        users.get(3).setLevel(6);
        users.get(3).setExp(22);
        users.get(4).setLevel(15);
        users.get(4).setExp(11);
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
        assertEquals(5, jsonUsers.size());
        assertTrue(jsonUsers.stream().allMatch(Objects::nonNull));
    }

    @Test
    void testUserObjectsCreated(){
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getUsername(), jsonUsers.get(i).getUsername());
            assertEquals(users.get(i).getLevel(), jsonUsers.get(i).getLevel());
            assertEquals(users.get(i).getExp(), jsonUsers.get(i).getExp());
        }
    }

    @Test
    void testUserToJson() {
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getUsername(), jsonUsers.get(i).getUsername());
            assertEquals(users.get(i).getLevel(), jsonUsers.get(i).getLevel());
            assertEquals(users.get(i).getExp(), jsonUsers.get(i).getExp());
        }
    }

    @Test
    void testGuids() {
        for (int i = 0; i < users.size(); i++) {
            assertTrue(jsonUsers.get(i).getGuid().matches("^[a-z0-9-]{36}$"));
        }
    }
}
