package ch.zhaw.mathify.model;

import io.javalin.security.RouteRole;

/**
 * Enum containing all the possible roles a user may have
 */
public enum Role implements RouteRole {
    ANONYMOUS,
    USER,
    SYSTEM_CRUD,
    ADMIN
}