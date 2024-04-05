package ch.zhaw.mathify.repository;

import ch.zhaw.mathify.controller.UserController;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public final class UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    public static final File USERS_JSON_FILE = new File(Objects.requireNonNull(UserController.class.getClassLoader().getResource("users.json")).getFile());
    private static UserRepository instance = new UserRepository();
    private List<User> users;

    private UserRepository() {
        load();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }

    public void add(User user) {
        this.users.add(user);
    }

    public void remove(User user) {
        this.users.remove(user);
    }

    public void save() {
        JsonMapper.writeUsersToJson(USERS_JSON_FILE, users);
    }

    public void load() {
        try {
            if (!USERS_JSON_FILE.exists()) {
                if (!USERS_JSON_FILE.createNewFile()) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void destroy() {
        instance = null;
    }
}
