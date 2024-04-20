package ch.zhaw.mathify.api.security;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import io.javalin.http.Context;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionHandlerTest {
    private static SessionHandler sessionHandler;
    private static User user;
    private static final String token = "123";

    @BeforeEach
    public void setup() {
        SessionHandler.destroy();
        sessionHandler = SessionHandler.getInstance();
        user = new User("test", "test", "test", Grade.FIRST);
        sessionHandler.createSession(user, token);
    }

    @Test
    void testSessionCreated(){
        assertTrue(sessionHandler.verifySession(user, token));
    }

    @Test
    void testSessionDestroyedUser(){
        sessionHandler.destroySession(user);
        assertFalse(sessionHandler.verifySession(user, token));
    }

    @Test
    void testSessionDestroyedToken(){
        sessionHandler.destroySession("123");
        assertFalse(sessionHandler.verifySession(user, token));
    }

    @Test
    void testGetUserByToken(){
        assertEquals(sessionHandler.getUserByToken(token), user);
    }

    @Test
    void testGetUserFromContext(){
        Context ctx = mock(Context.class);
        when(ctx.sessionAttribute("token")).thenReturn(token);
        assertEquals(sessionHandler.getUserFromContext(ctx), user);
    }
}
