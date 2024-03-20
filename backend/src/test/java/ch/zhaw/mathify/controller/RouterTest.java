package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests the Router of the class for Frontend <> Backend
 */
public class RouterTest {
    private static Router router;
    private Context ctx;
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<User> users = List.of(
            new User("john_doe", "jd@gmail.com", "password"),
            new User("jane_smith", "js@mail.com", "admin123"),
            new User("alex_jones", "abc@xmail.com", "password123"),
            new User("sarah_jackson", "sjackson@mail.com", "password123"),
            new User("michael_brown", "mail@mail.com", "password123")
    );

    /**
     * Start Javalin instance
     */
    @BeforeAll
    public static void setUpBeforeAll() {
        router = new Router();
        router.startApplication();
    }

    /**
     * Create Testmock
     */
    @BeforeEach
    public void setUp() {
        ctx = mock(Context.class);
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
    }


    /**
     * Fail to retrieve a specific user because they don't exist
     */
    @Test
    public void testRetrieveUserByIDUnsuccessful() {
        when(ctx.pathParam("username")).thenReturn("john");
        router.retrieveUserByID(ctx);

        verify(ctx).status(404);
        verify(ctx).result("User john not found!");
    }

    /**
     * Successfully retrieve an existing user
     */
    @Test
    public void testRetrieveUserByIDSuccessful() {
        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            when(ctx.pathParam("username")).thenReturn(currentUser.getUsername());

            router.retrieveUserByID(ctx);

            verify(ctx, times(i + 1)).pathParam("username");

            ArgumentCaptor<String> resultCaptor = ArgumentCaptor.forClass(String.class);
            verify(ctx, times(i + 1)).result(resultCaptor.capture());
            String resultJson = resultCaptor.getValue();

            try {
                JsonNode jsonNode = mapper.readTree(resultJson);

                String username = jsonNode.get("username").asText();
                int level = jsonNode.get("level").asInt();
                int experience = jsonNode.get("experience").asInt();
                String email = jsonNode.get("email").asText();
                assertEquals(currentUser.getUsername(), username);
                assertEquals(currentUser.getLevel(), level);
                assertEquals(currentUser.getExperience(), experience);
                assertEquals(currentUser.getEmail(), email);
            } catch (Exception e) {
                throw new RuntimeException("Error reading users.json!");
            }

            verify(ctx, times(i + 1)).status(200);
        }
    }
}