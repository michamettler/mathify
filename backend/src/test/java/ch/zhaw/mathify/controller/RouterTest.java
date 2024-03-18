package ch.zhaw.mathify.controller;

import io.javalin.http.Context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        Mockito.verify(ctx).status(404);
        Mockito.verify(ctx).result("User john not found!");
    }

    /**
     * Successfully retrieve an existing user
     */
    @Test
    public void testRetrieveUserByIDSuccessful() {
        when(ctx.pathParam("username")).thenReturn("john_doe");

        backbone.retrieveUserByID(ctx);

        Mockito.verify(ctx).status(200);

        //TODO: Make sure it also returns the correct user (tricky...)
    }
}