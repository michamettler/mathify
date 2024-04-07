package ch.zhaw.mathify.math;

import ch.zhaw.mathify.maths.MathAdvancedGenerator;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.exercise.MathAdvancedExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the MathAdvancedGenerator class.
 */
class MathAdvancedGeneratorTest {
    @Test
    void testTensComparison() {
        Exercise exercise = MathAdvancedGenerator.generate(Grade.FIRST, ExerciseSubType.TENSCOMPARISON);
        MathAdvancedExercise mathAdvancedExercise = (MathAdvancedExercise) exercise;

        String[] prompt = mathAdvancedExercise.exercise().split(" is bigger: ");
        String[] numbers = prompt[1].split(" or ");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1].replace("?", ""));

        assertTrue(a != b);
        assertEquals(mathAdvancedExercise.result()[0], Math.max(a, b));
    }
}
