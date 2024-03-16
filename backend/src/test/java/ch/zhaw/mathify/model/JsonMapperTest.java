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
            new User("john_doe", 10, 55),
            new User("jane_smith", 8, 44),
            new User("alex_jones", 12, 33),
            new User("sarah_jackson", 6, 22),
            new User("michael_brown", 15, 11)
    );
    private List<User> jsonUsers;
    @BeforeEach
    void setup() throws Exception {
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
            assertEquals(users.get(i).getExp(), jsonUsers.get(i).getExp());
        }
    }
}
