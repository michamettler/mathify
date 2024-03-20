package ch.zhaw.mathify.math;

import ch.zhaw.mathify.maths.MathBasicsGenerator;
import ch.zhaw.mathify.model.Exercise;
import ch.zhaw.mathify.model.ExerciseSubType;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.MathBasicsExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MathBasicsGeneratorTest {
    @Test
    void testSorting(){
        Exercise exercise = MathBasicsGenerator.generate(Grade.FIRST, ExerciseSubType.SORTING);
        MathBasicsExercise mathBasicsExercise = (MathBasicsExercise) exercise;
        assertTrue(mathBasicsExercise.result()[0] <= mathBasicsExercise.result()[1]);
        assertTrue(mathBasicsExercise.result()[1] <= mathBasicsExercise.result()[2]);
    }

    @Test
    void testNeighbors(){
        Exercise exercise = MathBasicsGenerator.generate(Grade.FIRST, ExerciseSubType.NEIGHBORS);
        MathBasicsExercise mathBasicsExercise = (MathBasicsExercise) exercise;
        assertEquals(mathBasicsExercise.result()[0], mathBasicsExercise.result()[1] - 1);
        assertEquals(mathBasicsExercise.result()[1], mathBasicsExercise.result()[2] - 1);
    }
}
