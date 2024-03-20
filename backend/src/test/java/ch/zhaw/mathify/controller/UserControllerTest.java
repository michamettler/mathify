package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.User;
import io.javalin.http.Context;
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
        User newUser = new User("testuser", "Test User", "testpassword");
        when(contextMock.bodyAsClass(User.class)).thenReturn(newUser);

        userController.create(contextMock);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(contextMock).status(statusCaptor.capture());
        assertEquals(201, statusCaptor.getValue());

        ArgumentCaptor<String> resultCaptor = ArgumentCaptor.forClass(String.class);
        verify(contextMock).result(resultCaptor.capture());
        assertEquals("User created successfully!", resultCaptor.getValue());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = userController.getUsers();

        userController.getAll(contextMock);

        verify(contextMock).json(users);
    }

    @Test
    void testGetOneUser() {
        User testUser = new User("testuser", "Test User", "testpassword");
        testUser.setGuid("testguid");
        userController.setUsers(List.of(testUser));
        when(contextMock.pathParam("s")).thenReturn("testuser");

        userController.getOne(contextMock, "testguid");

        verify(contextMock).json(testUser);
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User("testuser", "Updated Test User", "testpassword");
        updatedUser.setGuid("testguid");
        when(contextMock.bodyAsClass(User.class)).thenReturn(updatedUser);

        userController.update(contextMock, "testguid");

        assertTrue(userController.getUsers().stream().anyMatch(u -> u.getUsername().equals("testuser") && u.getEmail().equals("Updated Test User")));
        verify(contextMock).status(204);
    }

    @Test
    void testDeleteUser() {
        User userToDelete = new User("testuser", "Test User", "testpassword");
        userToDelete.setGuid("testguid");
        List<User> users = userController.getUsers();
        users.add(userToDelete);
        userController.setUsers(users);

        userController.delete(contextMock, "testguid");

        assertFalse(userController.getUsers().contains(userToDelete));
    }
}
