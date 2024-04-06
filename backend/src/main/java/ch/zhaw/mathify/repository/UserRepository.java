package ch.zhaw.mathify.repository;

import ch.zhaw.mathify.controller.apicontroller.UserApiController;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for managing the users in the system.
 */
public final class UserRepository implements Repository<User>{
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);
    public static final File USERS_JSON_FILE = new File(Objects.requireNonNull(UserApiController.class.getClassLoader().getResource("users.json")).getFile());
    private static UserRepository instance;
    private List<User> users;

    private UserRepository() {
        load();
    }

    /**
     * Returns the singleton instance of the UserRepository.
     *
     * @return the singleton instance of the UserRepository
     */
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }

    /**
     * Destroys the singleton instance of the UserRepository.
     */
    public static void destroy() {
        instance = null;
    }

    /**
     * @param user the user to add
     */
    public void add(User user) {
        if(user != null) this.users.add(user);
    }

    /**
     * @param user the user to remove
     */
    public void remove(User user) {
        if(user != null) this.users.remove(user);
    }

    /**
     * Saves the users to the users.json file.
     */
    public void save() {
        JsonMapper.writeUsersToJson(USERS_JSON_FILE, users);
    }

    /**
     * Loads the users from the users.json file.
     */
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

    /**
     * @return an unmodifiable list of all users
     */
    public List<User> get() {
        return Collections.unmodifiableList(users);
    }
}
