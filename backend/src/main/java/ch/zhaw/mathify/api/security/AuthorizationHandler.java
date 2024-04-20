package ch.zhaw.mathify.api.security;

import ch.zhaw.mathify.model.Role;
import ch.zhaw.mathify.model.User;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * This class is responsible for handling the authorization of the user
 */
public final class AuthorizationHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationHandler.class);
    private static final SessionHandler sessionHandler = SessionHandler.getInstance();

    private AuthorizationHandler() {
    }

    /**
     * This method checks if the user is allowed to access the endpoint
     *
     * @param ctx The context of the request
     */
    public static void validateEndpointAccess(Context ctx) {
        Set<RouteRole> permittedRole = ctx.routeRoles();

        if (permittedRole.contains((Role.ANONYMOUS))) {
            ctx.status(200);
            return;
        }

        try {
            User user = sessionHandler.getUserFromContext(ctx);

            if (permittedRole.contains(user.getRole())) {
                ctx.status(200);
                return;
            }


        } catch (Exception e) {
            LOG.error("Could not get user from context: {}", e.getMessage());
        }
        LOG.warn("Unauthorized access to endpoint: {}", ctx.path());
        ctx.header(Header.WWW_AUTHENTICATE, "Basic");
        ctx.status(401);
        throw new UnauthorizedResponse();
    }
}
