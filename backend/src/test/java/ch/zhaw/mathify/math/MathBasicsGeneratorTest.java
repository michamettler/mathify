package ch.zhaw.mathify.math;

import ch.zhaw.mathify.maths.MathBasicsGenerator;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.MathBasicsExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the MathBasicsGenerator class.
 */
class MathBasicsGeneratorTest {
    @Test
    void testSorting() {
        Exercise exercise = MathBasicsGenerator.generate(Grade.FIRST, ExerciseSubType.SORTING);
        MathBasicsExercise mathBasicsExercise = (MathBasicsExercise) exercise;
        assertTrue(mathBasicsExercise.result()[0] <= mathBasicsExercise.result()[1]);
        assertTrue(mathBasicsExercise.result()[1] <= mathBasicsExercise.result()[2]);
    }

    @Test
    void testNeighbors() {
        Exercise exercise = MathBasicsGenerator.generate(Grade.FIRST, ExerciseSubType.NEIGHBORS);
        MathBasicsExercise mathBasicsExercise = (MathBasicsExercise) exercise;
        assertEquals(mathBasicsExercise.result()[0], mathBasicsExercise.result()[1] - 1);
        assertEquals(mathBasicsExercise.result()[1], mathBasicsExercise.result()[2] - 1);
    }

    @Test
    void testComparison() {
        Exercise exercise = MathBasicsGenerator.generate(Grade.FIRST, ExerciseSubType.COMPARISON);
        MathBasicsExercise mathBasicsExercise = (MathBasicsExercise) exercise;
        String prompt = mathBasicsExercise.toString();
        String[] values = prompt.split(":")[1].trim().split(", ");
        int a = Integer.parseInt(values[0]);
        int b = Integer.parseInt(values[1]);
        int c = Integer.parseInt(values[2]);

        assertTrue(mathBasicsExercise.result()[0] == a || mathBasicsExercise.result()[0] == b || mathBasicsExercise.result()[0] == c);
        assertTrue(a != b && a != c && b != c);
        assertEquals(mathBasicsExercise.result()[0], Math.max(a, Math.max(b, c)));
    }

    @Test
    void testNumberCompletion() {
        Exercise exercise = MathBasicsGenerator.generate(Grade.FIRST, ExerciseSubType.NUMBERCOMPLETION);
        MathBasicsExercise mathBasicsExercise = (MathBasicsExercise) exercise;

        String[] parts = mathBasicsExercise.exercise().split(" to make ");
        String[] prompt = parts[0].split(" add to ");
        int a = Integer.parseInt(prompt[1]);
        int b = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
        int c = b - a;
        assertTrue(b > a);
        assertEquals(c, mathBasicsExercise.result()[0]);
    }

    @Test
    void testTensComparison() {
        Exercise exercise = MathBasicsGenerator.generate(Grade.FIRST, ExerciseSubType.TENSCOMPARISON);
        MathBasicsExercise mathBasicsExercise = (MathBasicsExercise) exercise;

        String[] prompt = mathBasicsExercise.exercise().split(" is bigger: ");
        String[] numbers = prompt[1].split(" or ");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1].replace("?", ""));

        assertTrue(a != b);
        assertEquals(mathBasicsExercise.result()[0], Math.max(a, b));
    }
}
