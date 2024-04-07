package ch.zhaw.mathify.math;


import ch.zhaw.mathify.maths.MathIntermediateGenerator;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.exercise.MathIntermediateExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the MathIntermediateGenerator
 */
class MathIntermediateGeneratorTest {
    @Test
    void testNumberCompletion() {
        Exercise exercise = MathIntermediateGenerator.generate(Grade.FIRST, ExerciseSubType.NUMBERCOMPLETION);
        MathIntermediateExercise mathIntermediateExerciseExercise = (MathIntermediateExercise) exercise;

        String[] parts = mathIntermediateExerciseExercise.exercise().split(" to make ");
        String[] prompt = parts[0].split(" add to ");
        int a = Integer.parseInt(prompt[1]);
        int b = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
        int c = b - a;
        assertTrue(b > a);
        assertEquals(c, mathIntermediateExerciseExercise.result()[0]);
    }
}
