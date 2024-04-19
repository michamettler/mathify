package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.repository.UserRepository;
import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

public class AuthenticationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationHandler.class);
    private final UserRepository userRepository = UserRepository.getInstance();

    private void createUserToken(Context ctx) {
        LOG.info("Creating user token...");
        Optional<BasicAuthCredentials> credentials = Optional.ofNullable(ctx.basicAuthCredentials());
        if (credentials.isPresent() && authenticateUser(credentials.get(), ctx)) {
            SecureRandom secureRandom = new SecureRandom();
            Base64.Encoder base64Encoder = Base64.getUrlEncoder();
            byte[] randomBytes = new byte[24];
            secureRandom.nextBytes(randomBytes);
            ctx.result(base64Encoder.encodeToString(randomBytes));
            LOG.info("{} logged in successfully", credentials.get().getUsername());
            ctx.status(200);
            return;
        }
        ctx.result("Please provide valid credentials!");
        LOG.error("invalid credentials have been provided during the login attempt");
        ctx.status(401);
    }

    public void login(Context ctx) {
        LOG.info("Trying to login user...");
        BasicAuthCredentials credentials = ctx.basicAuthCredentials();
        if(credentials == null) {
            String message = "Credentials are empty";
            LOG.info(message);
            ctx.result(message);
            ctx.status(400);
            return;
        }

        if (!credentials.getUsername().isEmpty() && !credentials.getPassword().isEmpty()) {
            LOG.info("Username and password were provided. Authenticating user...");
            if (authenticate(credentials.getUsername(), credentials.getPassword())) {

            }
        }
    }

    private boolean authenticate(String username, String password) {
        User user = userRepository.get().stream().filter();
        if () {

        }
    }
}
