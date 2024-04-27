package ch.zhaw.mathify.api;

import ch.zhaw.mathify.App;
import ch.zhaw.mathify.model.SettingsNotFoundException;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.util.JsonMapper;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.zhaw.mathify.repository.UserRepository.USERS_JSON_FILE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Router class.
 */
public class RouterTest {
    private String auth;

    @BeforeAll
    public static void startApplication() {
        try {
            App.run();
        } catch (SettingsNotFoundException e) {
            fail();
        }
        RestAssured.baseURI = "https://localhost:8443";
        RestAssured.config = RestAssuredConfig.newConfig().sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
    }

    @BeforeEach
    public void setup() {
        auth = given().auth().preemptive().basic("zehndjon", "jonas").get("/login").header("Authorization");
    }

    @Test
    void testWelcomePage() {
        String responseBody =
                given()
                        .header("Authorization", auth)
                        .when()
                        .get("/welcome")
                        .then()
                        .statusCode(200)
                        .extract().asString();

        assertThat(responseBody, equalTo("Welcome to Mathify!"));
    }

    @Test
    void testGETAllUsers() {
        List<User> users;
        try {
            users = JsonMapper.map(Files.readString(USERS_JSON_FILE.toPath()), User.class);
            List<String> expectedUserNames = new ArrayList<>();
            for (User user : users) {
                expectedUserNames.add(user.getUsername());
            }
            JsonNode fetchedUsers =
                    given()
                            .header("Authorization", auth)
                            .when()
                            .get("/users")
                            .then()
                            .statusCode(200)
                            .extract().body().as(JsonNode.class);

            assertThat(fetchedUsers.findValuesAsText("username"), containsInAnyOrder(expectedUserNames.toArray()));

        } catch (IOException e) {
            System.out.println("Error reading user list! " + e.getMessage());
            fail();
        }
    }

    @Test
    void testGETSingleUser() {
        String expectedUsername = "john_doe";
        String guid = "5e6a7b8c-7543-454c-b28b-2761c07fb5b7";
        User fetchedUser =
                given()
                        .header("Authorization", auth)
                        .when()
                        .get("/users/{guid}", guid)
                        .then()
                        .statusCode(200)
                        .extract().body().as(User.class);

        assertEquals(expectedUsername, fetchedUser.getUsername());
    }

    @Test
    void testSubtypeReturn() {
        String[] fetchedSubtypes =
                given()
                        .header("Authorization", auth)
                        .when()
                        .get("/exercise/subtypes")
                        .then()
                        .statusCode(200)
                        .extract().body().as(String[].class);

        for (ExerciseSubType expectedSubType : ExerciseSubType.values()) {
            assertTrue(Arrays.asList(fetchedSubtypes).contains(expectedSubType.name()));
        }
    }
}
