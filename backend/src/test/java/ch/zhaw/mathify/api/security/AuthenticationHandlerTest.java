package ch.zhaw.mathify.api.security;

import io.javalin.http.Context;
import io.javalin.security.BasicAuthCredentials;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class AuthenticationHandlerTest {
    private AuthenticationHandler authenticationHandler;

    @Mock
    private Context ctx;

    @BeforeEach
    void setUp() {
        authenticationHandler = new AuthenticationHandler();
        ctx = mock(Context.class);
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("zehndjon", "jonas"));
    }

    @Test
    void login_WithValidCredentials_ShouldAuthenticateSuccessfully() {
        authenticationHandler.login(ctx);
        when(ctx.res()).thenReturn(mock(HttpServletResponseWrapper.class));

        verify(ctx, times(1)).status(200);
    }

    @Test
    void login_WithEmptyCredentials_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("", ""));

        authenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }

    @Test
    void login_WithIncorrectPassword_ShouldReturnUnauthorized() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("zehndjon", "wrongPassword"));
        authenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Invalid credentials");
        verify(ctx, times(1)).status(401);
    }

    @Test
    void login_WhenUserNotFound_ShouldReturnUnauthorized() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("nonExistingUser", "password"));

        authenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Invalid credentials - User does not exist");
        verify(ctx, times(1)).status(401);
    }

    @Test
    void login_WhenCredentialsAreNull_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(null);

        authenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }

    @Test
    void login_WhenCredentialsUsernameIsEmpty_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("", "password"));

        authenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }

    @Test
    void login_WhenCredentialsPasswordIsEmpty_ShouldReturnBadRequest() {
        when(ctx.basicAuthCredentials()).thenReturn(new BasicAuthCredentials("username", ""));

        authenticationHandler.login(ctx);

        verify(ctx, times(1)).result("Credentials are empty");
        verify(ctx, times(1)).status(400);
    }
}
