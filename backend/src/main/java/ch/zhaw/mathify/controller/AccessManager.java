package ch.zhaw.mathify.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.BasicAuthCredentials;
import io.javalin.security.RouteRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AccessManager {
    private static final Logger LOG = LoggerFactory.getLogger(Router.class);
    public enum Role implements RouteRole {
        @JsonProperty("ANONYMOUS")
        ANONYMOUS,
        @JsonProperty("USER")
        USER,
        @JsonProperty("SYSTEM_CRUD")
        SYSTEM_CRUD,
        @JsonProperty("ADMIN")
        ADMIN
    }

    public static void handleAccess(Context ctx) {
        var permittedRole = ctx.routeRoles();
        Optional<BasicAuthCredentials> credentials = Optional.ofNullable(ctx.basicAuthCredentials());
        if (permittedRole.contains((Role.ANONYMOUS))) {
            return;
        }
        if (ctx.attribute("role") != null && permittedRole.contains(ctx.attribute("role"))) {
            return;
        }
        ctx.header(Header.WWW_AUTHENTICATE, "Basic");
        credentials.ifPresentOrElse(
                user -> LOG.error(user.getUsername() + " is not allowed to access this resource or entered wrong credentials!"),
                () -> LOG.error("Anonymous user is not allowed to access this resource!")
        );
        throw new UnauthorizedResponse();
    }
}
