package ch.zhaw.mathify.api.security;

import ch.zhaw.mathify.model.User;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class handles the sessions for the users.
 * The frontend doesn't need to keep track of the session.
 */
public final class  SessionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SessionHandler.class);
    private static SessionHandler instance;
    private final Map<User, String> sessions;

    /**
     * Creates a new instance of the SessionHandler, instantiates the SESSIONS map.
     */
    private SessionHandler() {
        LOG.info("Creating SessionHandler instance");
        sessions = new HashMap<>();
    }

    /**
     * @return the singleton instance of the SessionHandler
     */
    public static SessionHandler getInstance() {
        LOG.debug("Getting SessionHandler instance");
        if (instance == null) {
            instance = new SessionHandler();
        }

        return instance;
    }

    /**
     * Destroys the singleton instance of the SessionHandler.
     */
    public static void destroy() {
        LOG.debug("Destroying SessionHandler instance");
        instance = null;
    }


    /**
     * Checks if the session is valid for the given user
     *
     * @param user  The user to verify the session for
     * @param token The token to verify
     * @return true if the session is valid, false otherwise
     */
    public boolean verifySession(@NotNull User user, @NotNull String token) {
        LOG.info("Verifying session for user: {}", user);
        if(!sessions.containsKey(user)){
            return false;
        }
        return sessions.get(user).equals(token);
    }

    /**
     * Checks if the session is valid for the given token
     *
     * @param user The user to verify the session for
     */
    public void destroySession(@NotNull User user) {
        LOG.info("Destroying session for user: {}", user);
        sessions.remove(user);
    }

    /**
     * Destroys the session for the given token
     *
     * @param token The token to verify
     */
    public void destroySession(@NotNull String token) {
        LOG.info("Destroying session for token: {}", token);
        sessions.entrySet().removeIf(entry -> entry.getValue().equals(token));
    }

    /**
     * Creates a new session for the given user
     *
     * @param user  The user to create the session for
     * @param token The token to create the session with
     */
    public void createSession(@NotNull User user, @NotNull String token) {
        LOG.info("Creating session for user: {}", user.getUsername());
        sessions.put(user, token);
    }

    /**
     * @param token The token to get the user for
     * @return The user for the given token
     * @throws NoSuchElementException if the token is not found in the sessions
     */
    public User getUserByToken(String token) throws NoSuchElementException {
        return sessions.
                keySet()
                .stream()
                .filter(user -> sessions.get(user).equals(token))
                .findFirst()
                .orElseThrow();
    }

    /**
     * @param ctx The context of the request
     * @return The user for the given token in the session
     * @throws NoSuchElementException if the token is not found in the session
     */
    public User getUserFromContext(Context ctx) throws NoSuchElementException {
        String token = ctx.sessionAttribute("token");

        if (token == null) {
            throw new NoSuchElementException("No token found in session");
        }

        return getUserByToken(token);
    }
}
