package ch.zhaw.mathify.controller;

import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.BasicAuthCredentials;
import io.javalin.security.RouteRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * This class handles the authentication process for users to access endpoints
 */
public class AccessManager {
    private static final Logger LOG = LoggerFactory.getLogger(Router.class);

    /**
     * Enum containing all the possible roles a user may have
     */
    public enum Role implements RouteRole {
        ANONYMOUS,
        USER,
        SYSTEM_CRUD,
        ADMIN
    }

    /**
     * This method checks if the user is allowed to access the endpoint
     * @param ctx   The context of the request
     */
    public static void validateEndpointAccess(Context ctx) {
        var permittedRole = ctx.routeRoles();
        Optional<BasicAuthCredentials> credentials = Optional.ofNullable(ctx.basicAuthCredentials());
        if (permittedRole.contains((Role.ANONYMOUS))) {
            return;
        }
        if (ctx.attribute("role") != null && permittedRole.contains(ctx.attribute("role"))) {
            return;
        }
        ctx.header(Header.WWW_AUTHENTICATE, "Basic");
        ctx.status(401);
        credentials.ifPresentOrElse(
                user -> LOG.error(user.getUsername() + " is not allowed to access this resource or entered wrong credentials!"),
                () -> LOG.error("Anonymous user is not allowed to access this resource!")
        );
        throw new UnauthorizedResponse();
    }
}
