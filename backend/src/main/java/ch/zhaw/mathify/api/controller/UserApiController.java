package ch.zhaw.mathify.api.controller;

import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.repository.UserRepository;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationError;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class responsible for handling user CRUD operations
 */
public class UserApiController implements CrudHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UserApiController.class);
    private final UserRepository userRepository = UserRepository.getInstance();

    /**
     * @param context the context of the request
     */
    @Override
    public void create(@NotNull Context context) {
        validateUser(context);

        User user = context.bodyAsClass(User.class);
        user.setPassword(User.hashPassword(user.getPassword()));
        if (userRepository.get().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))) {
            String responseMessage = "Username already exists!";
            context.status(409);
            context.result(responseMessage);
            LOG.error(responseMessage);
        } else {
            String responseMessage = "User created successfully! GUID: " + user.getGuid();
            userRepository.add(user);
            userRepository.save();
            context.status(201);
            context.result(responseMessage);
            LOG.info(responseMessage);
        }
    }

    /**
     * @param context the context of the request
     * @param guid    the guid of the user to delete
     */
    @Override
    public void delete(@NotNull Context context, @NotNull String guid) {
        if (userRepository.get().stream().anyMatch(u -> u.getGuid().equals(guid))) {
            userRepository.remove(userRepository.get().stream()
                    .filter(u -> u.getGuid().equals(guid))
                    .findFirst()
                    .orElse(null));
            String responseMessage = "User deleted successfully!";
            userRepository.save();
            context.status(204);
            context.result(responseMessage);
            LOG.info(responseMessage);
        } else {
            String responseMessage = "User not found!";
            context.status(404);
            context.result(responseMessage);
            LOG.error(responseMessage);
        }
    }

    /**
     * @param context the context of the request
     */
    @Override
    public void getAll(@NotNull Context context) {
        context.json(userRepository.get());
        LOG.info("user list was retrieved via GET /users endpoint");
    }

    /**
     * @param context the context of the request
     * @param guid    the guid of the user to retrieve
     */
    @Override
    public void getOne(@NotNull Context context, @NotNull String guid) {
        User user = userRepository.get().stream().filter(u -> u.getGuid().equals(guid)).findFirst().orElse(null);
        if (user != null) {
            context.json(user);
            LOG.info("user {} was retrieved via GET /users/{} endpoint", guid, guid);
        } else {
            context.status(404);
            context.result("User not found!");
            LOG.error("User not found!");
        }
    }

    /**
     * @param context the context of the request
     * @param guid    the guid of the user to update
     */
    @Override
    public void update(@NotNull Context context, @NotNull String guid) {
        validateUser(context);

        User user = context.bodyAsClass(User.class);
        user.setPassword(User.hashPassword(user.getPassword()));

        if (userRepository.get().stream().anyMatch(u -> u.getGuid().equals(guid))) {
            userRepository.remove(userRepository.get().stream()
                    .filter(u -> u.getGuid().equals(guid))
                    .findFirst()
                    .orElse(null));
            userRepository.add(user);
            userRepository.save();
            context.status(204);
            context.result("User updated successfully!");
            LOG.info("User updated successfully!");
        } else {
            context.status(404);
            context.result("User not found!");
            LOG.error("User not found!");
        }
    }

    void validateUser(@NotNull Context context) {
        context.bodyValidator(User.class)
                .check(user -> user.getUsername() != null && user.getPassword() != null && user.getEmail() != null
                                && !user.getUsername().isBlank() && !user.getPassword().isBlank() && !user.getEmail().isBlank(),
                        new ValidationError<>("username, password and email must not be null!"))
                .get();
    }
}
