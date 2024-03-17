package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for creating endpoints to communicate between Front- and Backend
 */
public class Backbone {
    ObjectMapper mapper = new ObjectMapper();
    List<User> userList;
    Javalin app;

    /**
     * Read the users.json file
     */
    public Backbone() {
        try {
            Path file = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("users.json")).toURI());
            userList = mapper.readValue(file.toFile(), new TypeReference<>() {
            });
        } catch (IOException | URISyntaxException e) {
            //TODO: Replace with LOG4J
            e.printStackTrace();
        }
    }

    /**
     * Starts the Javalin instance including the Endpoints
     */
    public void startApplication() {

        app = Javalin.create()
                .start("127.0.0.1", 7000);
        app.get("/welcome", ctx -> ctx.result("Welcome to Mathify!"));
        app.get("/users", ctx -> ctx.result(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getUsers())));
        app.get("/users/{username}", this::retrieveUserByID);
    }

    /**
     * Shuts the Javalin instance including the Endpoints down
     */
    public void closeApplication() {
        if (app != null) {
            app = Javalin.create().stop();
        }
    }

    /**
     * Retrieves the userlist
     * @return  userlist containing every user in the users.json file
     */
    public List<User> getUsers() {
        return userList;
    }

    /**
     * Returns a specific user from the userlist via GET-Call
     * @param ctx   Context-specific parameter (username)
     */
    void retrieveUserByID(Context ctx) {
        String username = ctx.pathParam("username");
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                try {
                    ctx.status(200);
                    ctx.result(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        ctx.status(404);
        ctx.result("User " + username + " not found!");
    }
}