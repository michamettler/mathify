package ch.zhaw.mathify.api.controller;

import ch.zhaw.mathify.api.security.SessionHandler;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.model.exercise.ExerciseDto;
import io.javalin.http.Context;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExerciseApiControllerTest {
    private Context ctxMock;
    private ExerciseApiController exerciseApiController;
    private User user;

    @BeforeEach
    public void setUp() {
        exerciseApiController = new ExerciseApiController();
        user = new User("abc", "abc", "abc", Grade.FIRST);
        SessionHandler.getInstance().createSession(user, "abc");
    }

    @AfterEach
    public void tearDown() {
        SessionHandler.getInstance().destroySession("abc");
    }

    @Test
    void testVerifyResultPositive() {
        ctxMock = mock(Context.class);
        when(ctxMock.bodyAsClass(ExerciseDto.class))
                .thenReturn(
                        new ExerciseDto(
                                "[1]",
                                "[1]",
                                "exercise",
                                "[1]",
                                "Put two groups of things together. If you have 3 apples and get 2 more, how many apples do you have now?",
                                "addition",
                                "Addition"
                        )
                );
        when(ctxMock.header("Authorization")).thenReturn("abc");

        int experienceBefore = user.getExperience();
        int levelBefore = user.getLevel();

        exerciseApiController.verifyResult(ctxMock);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(ctxMock).status(statusCaptor.capture());
        assertEquals(200, statusCaptor.getValue());

        verify(ctxMock).json(Map.of(
                "correct", true,
                "experience", 10,
                "experienceBefore", experienceBefore,
                "level", user.getLevel(),
                "levelBefore", levelBefore
        ));
    }

    @Test
    void testVerifyResultNegative() {
        ctxMock = mock(Context.class);
        when(ctxMock.bodyAsClass(ExerciseDto.class))
                .thenReturn(
                        new ExerciseDto(
                                "[1]",
                                "[2]",
                                "exercise",
                                "[1]",
                                "Put two groups of things together. If you have 3 apples and get 2 more, how many apples do you have now?",
                                "addition",
                                "Addition"
                        )
                );
        when(ctxMock.header("Authorization")).thenReturn("abc");

        int experienceBefore = user.getExperience();
        int levelBefore = user.getLevel();

        exerciseApiController.verifyResult(ctxMock);

        ArgumentCaptor<Integer> statusCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(ctxMock).status(statusCaptor.capture());
        assertEquals(200, statusCaptor.getValue());

        verify(ctxMock).json(Map.of(
                "correct", false,
                "experience", 1,
                "experienceBefore", experienceBefore,
                "level", user.getLevel(),
                "levelBefore", levelBefore
        ));
    }
}