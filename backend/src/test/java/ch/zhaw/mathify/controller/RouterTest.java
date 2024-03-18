package ch.zhaw.mathify.controller;

import io.javalin.http.Context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests the Backbone of the class for Frontend <> Backend
 */
public class RouterTest {
    private static Router backbone;
    private Context ctx;

    /**
     * Start Javalin instance
     */
    @BeforeAll
    public static void setUpBeforeAll() {
        backbone = new Router();
        backbone.startApplication();
    }

    /**
     * Create Testmock
     */
    @BeforeEach
    public void setUp() {
        ctx = mock(Context.class);
    }


    /**
     * Fail to retrieve a specific user because they don't exist
     */
    @Test
    public void testRetrieveUserByIDUnsuccessful() {
        when(ctx.pathParam("username")).thenReturn("john");
        backbone.retrieveUserByID(ctx);

        verify(ctx).status(404);
        verify(ctx).result("User john not found!");
    }

    /**
     * Successfully retrieve an existing user
     */
    @Test
    public void testRetrieveUserByIDSuccessful() {
        when(ctx.pathParam("username")).thenReturn("john_doe");

        backbone.retrieveUserByID(ctx);

        verify(ctx).pathParam("username");

        ArgumentCaptor<String> resultCaptor = ArgumentCaptor.forClass(String.class);
        verify(ctx).result(resultCaptor.capture());
        String resultJson = resultCaptor.getValue();
        String expectedUserJson = "{\"username\":\"john_doe\",\"level\":10,\"experience\":55}";

        assertEquals(expectedUserJson, resultJson.replaceAll("\\s+", ""));
        verify(ctx).status(200);
    }
}