package ch.zhaw.mathify.api.security;

import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class AuthenticationHandlerTest {

    @Mock
    private Context ctx;

    @BeforeEach
    void setUp() {
        ctx = mock(Context.class);
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("zehndjon", "jonas"));
    }

    @Test
    void login_WithValidCredentials_ShouldAuthenticateSuccessfully() {
        AuthenticationHandler.login(ctx);

        verify(ctx, times(1)).status(200);
    }

    @Test
    void login_WithEmptyCredentials_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("", ""));

        AuthenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }

    @Test
    void login_WithIncorrectPassword_ShouldReturnUnauthorized() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("zehndjon", "wrongPassword"));
        AuthenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Invalid credentials");
        verify(ctx, times(1)).status(401);
    }

    @Test
    void login_WhenUserNotFound_ShouldReturnUnauthorized() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("nonExistingUser", "password"));

        AuthenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Invalid credentials - User does not exist");
        verify(ctx, times(1)).status(401);
    }

    @Test
    void login_WhenCredentialsAreNull_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(null);

        AuthenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }

    @Test
    void login_WhenCredentialsUsernameIsEmpty_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("", "password"));

        AuthenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }

    @Test
    void login_WhenCredentialsPasswordIsEmpty_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("username", ""));

        AuthenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }
}
