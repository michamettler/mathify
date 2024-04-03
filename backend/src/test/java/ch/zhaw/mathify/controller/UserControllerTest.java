package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import io.javalin.http.Context;
import io.javalin.validation.BodyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserController userController;
    private Context contextMock;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        contextMock = Mockito.mock(Context.class);
    }

    @Test
    void testCreateUser() {
        User newUser = new User("testuser", "Test User", "testpassword", Grade.FIRST);
        when(contextMock.bodyAsClass(User.class)).thenReturn(newUser);
        when(contextMock.bodyValidator(User.class)).thenReturn(new BodyValidator<>("body", User.class, () -> newUser));

        userController.create(contextMock);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(contextMock).status(statusCaptor.capture());
        assertEquals(201, statusCaptor.getValue());

        ArgumentCaptor<String> resultCaptor = ArgumentCaptor.forClass(String.class);
        verify(contextMock).result(resultCaptor.capture());
        assertTrue(resultCaptor.getValue().contains("User created successfully!"));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = userController.getUsers();

        userController.getAll(contextMock);

        verify(contextMock).json(users);
    }

    @Test
    void testGetOneUser() {
        User testUser = new User("testuser", "Test User", "testpassword", Grade.FIRST);
        testUser.setGuid("testguid");
        userController.setUsers(List.of(testUser));
        when(contextMock.pathParam("s")).thenReturn("testuser");

        userController.getOne(contextMock, "testguid");

        verify(contextMock).json(testUser);
    }

    @Test
    void testDeleteUser() {
        User userToDelete = new User("testuser", "Test User", "testpassword", Grade.FIRST);
        userToDelete.setGuid("testguid");
        List<User> users = userController.getUsers();
        users.add(userToDelete);
        userController.setUsers(users);

        userController.delete(contextMock, "testguid");

        assertFalse(userController.getUsers().contains(userToDelete));
    }
}
