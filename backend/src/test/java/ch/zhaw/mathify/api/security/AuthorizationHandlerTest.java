package ch.zhaw.mathify.api.security;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.Role;
import ch.zhaw.mathify.model.User;
import io.javalin.http.Context;
import io.javalin.http.Header;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.BasicAuthCredentials;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * This class tests the AccessManager class
 */
class AuthorizationHandlerTest {
    private final Context ctx = Mockito.mock(Context.class);
    private final BasicAuthCredentials credentials = new BasicAuthCredentials("zehndjon", "jonas");
    private final AuthorizationHandler authorizationHandler = new AuthorizationHandler();

    @Test
    void testSuccessfulAuthentication() {
        String token = "123";
        User user = new User("zehndjon", "mail", "jonas", Grade.FIRST);
        SessionHandler.getInstance().createSession(user, token);
        when(ctx.routeRoles()).thenReturn(Collections.singleton(Role.USER));
        when(ctx.attribute("role")).thenReturn(Role.USER);
        when(ctx.basicAuthCredentials()).thenReturn(credentials);
        when(ctx.header("Authorization")).thenReturn(token);

        authorizationHandler.validateEndpointAccess(ctx);

        verify(ctx, times(1)).status(200);
        verify(ctx, never()).status(401);
        verify(ctx, never()).header(eq(Header.WWW_AUTHENTICATE), anyString());
    }

    @Test
    void testUnsuccessfulAuthentication() {
        when(ctx.routeRoles()).thenReturn(Collections.singleton(Role.USER));
        when(ctx.attribute("role")).thenReturn(Role.ANONYMOUS);
        when(ctx.basicAuthCredentials()).thenReturn(credentials);

        try {
            authorizationHandler.validateEndpointAccess(ctx);
        } catch (UnauthorizedResponse e) {
            verify(ctx, never()).status(200);
            verify(ctx, times(1)).status(401);
            verify(ctx, times(1)).header(eq(Header.WWW_AUTHENTICATE), anyString());
        }
    }
}
