package ch.zhaw.mathify.api.security;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.repository.UserRepository;
import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;

/**
 * This class handles the authentication process for users including the creation of user tokens
 */
public final class AuthenticationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationHandler.class);
    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final SessionHandler sessionHandler = SessionHandler.getInstance();

    private AuthenticationHandler() {
    }

    /**
     * Logs in the user
     * <ul>
     *     <li>Checks if the credentials are empty</li>
     *     <li>Authenticates the user</li>
     *     <li>Creates a user token</li>
     *     <li>Sets the role of the user</li>
     * </ul>
     *
     * @param ctx The context of the request
     */
    public static void login(Context ctx) {
        LOG.info("Trying to login user...");
        BasicAuthCredentials credentials = ctx.basicAuthCredentials();

        if (credentials == null || credentials.getUsername().isEmpty() || credentials.getPassword().isEmpty()) {
            String message = "Credentials are empty";
            LOG.info(message);
            ctx.result(message);
            ctx.status(400);
            return;
        }


        LOG.info("Username and password were provided. Authenticating user...");
        try {
            if (authenticate(credentials)) {
                LOG.info("User authenticated successfully");

                createTokenAndStoreToSession(ctx);
            } else {
                LOG.info("User could not be authenticated");
                ctx.result("Invalid credentials");
                ctx.status(401);

            }
        } catch (NoSuchElementException e) {
            LOG.error("User does not exist", e);
            ctx.result("Invalid credentials - User does not exist");
            ctx.status(401);
        } catch (Exception e) {
            LOG.error("Error while authenticating user", e);
            ctx.status(500);
        }
    }

    /**
     * Registers a new user if it doesn't already exist
     * @param ctx The context of the request
     */
    public static void register(Context ctx) {
        LOG.info("Trying to register user...");

        User user = ctx.bodyAsClass(User.class);

        if(user == null || user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getGrade() == null) {
            String message = "User data is empty";
            LOG.info(message);
            ctx.result(message);
            ctx.status(400);
            return;
        }

        if(userRepository.checkIfUserExists(user.getUsername())) {
            String message = "User already exists";
            LOG.info(message);
            ctx.result(message);
            ctx.status(400);
            return;
        }

        try {
            userRepository.add(user);
            ctx.status(200);
            LOG.info("User registered successfully");
        } catch (Exception e) {
            LOG.error("Error while registering user", e);
            ctx.status(500);
        }
    }

    /**
     * Creates a user token and adds it to the user session
     *
     * @param ctx The context of the request
     */
    private static void createTokenAndStoreToSession(Context ctx) {
        LOG.info("Creating user token...");

        try {
            User user = userRepository.getByUserName(ctx.basicAuthCredentials().getUsername());

            String token = createToken();
            sessionHandler.createSession(user, token);
            ctx.header("Authorization", token);

            ctx.status(200);

            LOG.info("User token and session created successfully");
        } catch (NoSuchElementException e) {
            LOG.error("Error while creating user token", e);
            ctx.status(500);
        }
    }

    /**
     * Authenticates the user
     *
     * @param credentials The credentials to authenticate
     * @return true if the user is authenticated, false otherwise
     * @throws NoSuchElementException if the user does not exist
     */
    private static boolean authenticate(BasicAuthCredentials credentials) throws NoSuchElementException {
        User user = userRepository.getByUserName(credentials.getUsername());
        return User.verifyPassword(credentials.getPassword(), user.getPassword());
    }

    /**
     * Creates a new token for the user with a length of 24 bytes
     *
     * @return a new token
     */
    @NotNull
    private static String createToken() {
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
