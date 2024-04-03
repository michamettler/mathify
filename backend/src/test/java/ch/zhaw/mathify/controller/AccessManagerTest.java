package ch.zhaw.mathify.controller;

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
class AccessManagerTest {
    private final Context ctx = Mockito.mock(Context.class);
    private final BasicAuthCredentials credentials = Mockito.mock(BasicAuthCredentials.class);
    @Test
    void testSuccessfulAuthentication() {
        when(ctx.routeRoles()).thenReturn(Collections.singleton(AccessManager.Role.USER));
        when(ctx.attribute("role")).thenReturn(AccessManager.Role.USER);
        when(ctx.basicAuthCredentials()).thenReturn(credentials);

        AccessManager.validateEndpointAccess(ctx);

        verify(ctx, never()).status(401);
        verify(ctx, never()).header(eq(Header.WWW_AUTHENTICATE), anyString());
    }

    @Test
    void testUnsuccessfulAuthentication() {
        when(ctx.routeRoles()).thenReturn(Collections.singleton(AccessManager.Role.USER));
        when(ctx.attribute("role")).thenReturn(AccessManager.Role.ANONYMOUS);
        when(ctx.basicAuthCredentials()).thenReturn(credentials);

        try {
            AccessManager.validateEndpointAccess(ctx);
        } catch (UnauthorizedResponse e) {
            verify(ctx, times(1)).status(401);
            verify(ctx, times(1)).header(eq(Header.WWW_AUTHENTICATE), anyString());
        }
    }
}
