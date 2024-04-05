package ch.zhaw.mathify.controller;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.repository.UserRepository;
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
    private UserRepository userRepository;
    private Context contextMock;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        contextMock = Mockito.mock(Context.class);
        userRepository = UserRepository.getInstance();
    }

    @Test
    void testCreateUser() {
        userRepository.destroy();
        User newUser = new User("t", "Test User", "testpassword", Grade.FIRST);
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
        userRepository.destroy();
        userController.getAll(contextMock);

        verify(contextMock).json(userRepository.getUsers());
    }

    @Test
    void testGetOneUser() {
        userRepository.destroy();
        User testUser = new User("testuser", "Test User", "testpassword", Grade.FIRST);
        testUser.setGuid("testguid");
        userRepository.setUsers(List.of(testUser));
        when(contextMock.pathParam("s")).thenReturn("testuser");

        userController.getOne(contextMock, "testguid");

        verify(contextMock).json(testUser);
    }

    @Test
    void testDeleteUser() {
        userRepository.destroy();
        User userToDelete = new User("testuser", "Test User", "testpassword", Grade.FIRST);
        userToDelete.setGuid("testguid");
        List<User> users = userRepository.getUsers();
        users.add(userToDelete);
        userRepository.setUsers(users);

        userController.delete(contextMock, "testguid");

        assertFalse(userRepository.getUsers().contains(userToDelete));
    }
}
