package ch.zhaw.mathify.api.controller;

import ch.zhaw.mathify.api.security.SessionHandler;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.repository.UserRepository;
import io.javalin.http.Context;
import io.javalin.validation.BodyValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserApiControllerTest {

    private UserApiController userApiController;
    private UserRepository userRepository;
    private Context contextMock;

    @BeforeEach
    void setUp() {
        userApiController = new UserApiController();
        contextMock = Mockito.mock(Context.class);
        userRepository = UserRepository.getInstance();
    }

    @Test
    void testCreateUser() {
        UserRepository.destroy();
        User newUser = new User("abcd", "Test User", "testpassword", Grade.FIRST);
        when(contextMock.bodyAsClass(User.class)).thenReturn(newUser);
        when(contextMock.bodyValidator(User.class)).thenReturn(new BodyValidator<>("body", User.class, () -> newUser));

        userApiController.create(contextMock);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(contextMock).status(statusCaptor.capture());
        assertEquals(201, statusCaptor.getValue());

        ArgumentCaptor<String> resultCaptor = ArgumentCaptor.forClass(String.class);
        verify(contextMock).result(resultCaptor.capture());
        assertTrue(resultCaptor.getValue().contains("User created successfully!"));
    }

    @Test
    void testGetAllUsers() {
        UserRepository.destroy();
        userApiController.getAll(contextMock);

        verify(contextMock).json(userRepository.get());
    }

    @Test
    void testGetOneUser() {
        UserRepository.destroy();
        User testUser = new User("testuser", "Test User", "testpassword", Grade.FIRST);
        userRepository.add(testUser);

        SessionHandler.getInstance().createSession(testUser, "testtokentestuser");

        userApiController.getOne(contextMock, "testtokentestuser");

        verify(contextMock).json(testUser);
    }

    @Test
    void testDeleteUser() {
        UserRepository.destroy();
        User userToDelete = new User("abcd", "Test User", "testpassword", Grade.FIRST);
        userRepository.add(userToDelete);

        SessionHandler.getInstance().createSession(userToDelete, "testtokenabcd");

        userApiController.delete(contextMock, "testtokenabcd");

        assertFalse(userRepository.get().contains(userToDelete));
    }

    @Test
    void testUpdateUser() {
        UserRepository.destroy();
        User newUser = new User("abcd", "Test User", "testpassword", Grade.FIRST);
        when(contextMock.bodyAsClass(User.class)).thenReturn(newUser);
        when(contextMock.bodyValidator(User.class)).thenReturn(new BodyValidator<>("body", User.class, () -> newUser));

        User userToBeUpdated = userRepository.getByUserName("zehndjon");
        SessionHandler.getInstance().createSession(userToBeUpdated, "testtokenzehndjon");
        String guid = userToBeUpdated.getGuid();

        userApiController.update(contextMock, "testtokenzehndjon");

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(contextMock).status(statusCaptor.capture());
        assertEquals(204, statusCaptor.getValue());

        ArgumentCaptor<String> resultCaptor = ArgumentCaptor.forClass(String.class);
        verify(contextMock).result(resultCaptor.capture());
        assertTrue(resultCaptor.getValue().contains("User updated successfully!"));

        User updatedUser = userRepository.getByUserName("abcd");
        assertEquals(guid, updatedUser.getGuid());
    }
}
