package ch.zhaw.mathify.service;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.model.exercise.Exercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExerciseServiceTest {

    private ExerciseService exerciseService;
    private User user;

    @BeforeEach
    void setUp() {
        exerciseService = new ExerciseService();
        user = mock(User.class);
        when(user.getGrade()).thenReturn(Grade.FIRST);
    }

    @Test
    void testCreateExerciseForUser() {
        Exercise exercise = exerciseService.createExerciseForUser(user);
        assertNotNull(exercise);
    }
}