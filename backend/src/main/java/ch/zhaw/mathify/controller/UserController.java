package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.util.JsonMapper;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

/**
 * Class responsible for handling user CRUD operations and persisting them to a JSON file
 */
public class UserController implements CrudHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    public static final File USERS_JSON_FILE = new File(
            Objects.requireNonNull(
                    UserController.class.getClassLoader().getResource("users.json")
            ).getFile());
    private List<User> users;

    /**
     * Constructor to read users from JSON file
     */
    public UserController() {
        try {
            if(!USERS_JSON_FILE.exists()) {
                if(!USERS_JSON_FILE.createNewFile()) {
                    LOG.error("Could not create users.json!");
                    throw new IOException("Could not create users.json!");
                }
                JsonMapper.writeUsersToJson(USERS_JSON_FILE, List.of());
            }
            this.users = JsonMapper.map(Files.readString(USERS_JSON_FILE.toPath()), User.class);
            LOG.info("users.json was read successfully");
        } catch (IOException e) {
            LOG.error("Could not read users.json!", e);
        }
    }

    /**
     * @param context the context of the request
     */
    @Override
    public void create(@NotNull Context context) {
        User user = context.bodyAsClass(User.class);
        if (users.stream().anyMatch(u -> u.getGuid().equals(user.getGuid()))) {
            context.status(409);
            context.result("Username already exists!");
            LOG.error("Username already exists!");
        } else {
            users.add(user);
            JsonMapper.writeUsersToJson(USERS_JSON_FILE, users);
            context.status(201);
            context.result("User created successfully!");
            LOG.info("User created successfully!");
        }
    }

    /**
     * @param context the context of the request
     * @param s      the username of the user to delete
     */
    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        if (users.removeIf(u -> u.getGuid().equals(s))) {
            JsonMapper.writeUsersToJson(USERS_JSON_FILE, users);
            context.status(204);
            context.result("User deleted successfully!");
            LOG.info("User deleted successfully!");
        } else {
            context.status(404);
            context.result("User not found!");
            LOG.error("User not found!");
        }
    }

    /**
     * @param context the context of the request
     */
    @Override
    public void getAll(@NotNull Context context) {
        context.json(users);
        LOG.info("user list was retrieved via GET /users endpoint");
    }

    /**
     * @param context the context of the request
     * @param s     the username of the user to retrieve
     */
    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        User user = users.stream().filter(u -> u.getGuid().equals(s)).findFirst().orElse(null);
        if (user != null) {
            context.json(user);
            LOG.info("user {} was retrieved via GET /users/{} endpoint", s, s);
        } else {
            context.status(404);
            context.result("User not found!");
            LOG.error("User not found!");
        }
    }

    /**
     * @param context the context of the request
     * @param s     the username of the user to update
     */
    @Override
    public void update(@NotNull Context context, @NotNull String s) {
        User user = context.bodyAsClass(User.class);
        if (users.removeIf(u -> u.getGuid().equals(s))) {
            users.add(user);
            JsonMapper.writeUsersToJson(USERS_JSON_FILE, users);
            context.status(204);
            context.result("User updated successfully!");
            LOG.info("User updated successfully!");
        } else {
            context.status(404);
            context.result("User not found!");
            LOG.error("User not found!");
        }
    }

    void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
