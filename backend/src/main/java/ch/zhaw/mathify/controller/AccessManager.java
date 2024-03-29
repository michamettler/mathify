package ch.zhaw.mathify.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.RouteRole;

public class AccessManager {
    public enum Role implements RouteRole {
        @JsonProperty("ANONYMOUS")
        ANONYMOUS,
        @JsonProperty("USER")
        USER,
        @JsonProperty("SYSTEM_READ")
        SYSTEM_READ,
        @JsonProperty("SYSTEM_WRITE")
        SYSTEM_WRITE,
        @JsonProperty("ADMIN")
        ADMIN
    }

    public static void handleAccess(Context ctx) {
        var permittedRole = ctx.routeRoles();
        if (permittedRole.contains((Role.ANONYMOUS))) {
            return;
        }
        if (ctx.attribute("role") != null && permittedRole.contains(ctx.attribute("role"))) {
            return;
        }
        ctx.header(Header.WWW_AUTHENTICATE, "Basic");
        throw new UnauthorizedResponse();
    }
}
