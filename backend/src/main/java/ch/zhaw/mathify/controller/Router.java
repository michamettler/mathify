package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class responsible for creating endpoints to communicate between Front- and Backend
 */
public class Router {
    private final ObjectMapper mapper = new ObjectMapper();
    private List<User> userList;
    private Javalin app;
    private final static Logger LOG = LogManager.getLogger(Router.class);

    /**
     * Read the users.json file
     */
    public Router() {
        try {
            File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("users.json")).getFile());
            userList = mapper.readValue(file, new TypeReference<>() {
            });
            LOG.info("users.json was read successfully");
        } catch (IOException e) {
            LOG.error("Could not read users.json!", e);
        }
    }

    /**
     * Starts the Javalin instance including the Endpoints
     */
    public void startApplication() {

        app = Javalin.create()
                .start("127.0.0.1", 7000);
        app.get("/welcome", ctx -> {
            ctx.result("Welcome to Mathify!");
            LOG.info("welcome page was accessed");
        });
        app.get("/users", ctx -> {
            ctx.result(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getUsers()));
            LOG.info("user list was retrieved via GET /users endpoint");
        });
        app.get("/users/{username}", this::retrieveUserByID);
        app.get("/page-not-found", ctx -> {
            ctx.result("Page " + ctx.queryParam("invalid-endpoint") + " not found!");
            LOG.error("Page " + ctx.queryParam("invalid-endpoint") +  " not found!");
        });
        app.error(404, ctx -> ctx.redirect("/page-not-found?invalid-endpoint=" + ctx.path()));
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
     *
     * @return userlist containing every user in the users.json file
     */
    public List<User> getUsers() {
        return userList;
    }

    /**
     * Returns a specific user from the userlist via GET-Call
     *
     * @param ctx Context-specific parameter (username)
     */
    void retrieveUserByID(Context ctx) {
        String username = ctx.pathParam("username");
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                try {
                    LOG.info("User " + username + " was retrieved");
                    ctx.result(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
                    ctx.status(200);
                } catch (IOException e) {
                    LOG.error("Could not retrieve user with given username found!", e);
                }
                return;
            }
        }
        ctx.result("User " + username + " not found!");
        LOG.warn("No user with username " + username + " found");
        ctx.status(404);
    }
}