package ch.zhaw.mathify.service;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.User;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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

    @Test
    void testVerifyResult() {
        Exercise exercise = mock(Exercise.class);
        when(exercise.verifyResult()).thenReturn(true);
        when(exercise.exerciseSubType()).thenReturn(ExerciseSubType.ADDITION);
        exerciseService.verifyResult(exercise, user);

        verify(user, times(1)).addExp(10);
        verify(user, times(1)).addTechnicalScore(ExerciseSubType.ADDITION, 1);
    }
}