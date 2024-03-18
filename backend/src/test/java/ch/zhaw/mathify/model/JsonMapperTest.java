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
            new User("john_doe"),
            new User("jane_smith"),
            new User("alex_jones"),
            new User("sarah_jackson"),
            new User("michael_brown")
    );
    private List<User> jsonUsers;
    @BeforeEach
    void setup() throws Exception {
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
            System.out.println("TEST-ERROR!");
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
            assertEquals(users.get(i).getExperience(), jsonUsers.get(i).getExperience());
        }
    }
}
