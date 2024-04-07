package ch.zhaw.mathify.math;

import ch.zhaw.mathify.maths.MathBasicsGenerator;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.MathBasicsExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
