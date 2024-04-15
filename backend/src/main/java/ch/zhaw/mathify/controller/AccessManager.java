package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.Role;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.security.RouteRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * This class handles the authentication process for users to access endpoints
 */
public class AccessManager {
    private static final Logger LOG = LoggerFactory.getLogger(AccessManager.class);
    private AccessManager() {
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
        if (ctx.attribute("role") != null && permittedRole.contains((RouteRole) ctx.attribute("role"))) {
            ctx.status(200);
            return;
        }
        LOG.warn("Unauthorized access to endpoint: {}", ctx.path());
        ctx.header(Header.WWW_AUTHENTICATE, "Basic");
        ctx.status(401);
    }
}
