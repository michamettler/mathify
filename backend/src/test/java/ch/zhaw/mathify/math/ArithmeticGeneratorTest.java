package ch.zhaw.mathify.math;

import ch.zhaw.mathify.maths.ArithmeticGenerator;
import ch.zhaw.mathify.model.exercise.ArithmeticExercise;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the arithmetic generator
 */
class ArithmeticGeneratorTest {
    @Test
    void testGenerateAdditionFirstGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FIRST, ExerciseSubType.ADDITION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(arithmeticExercise.result() <= 20);
    }

    @Test
    void testGenerateAdditionSecondGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.SECOND, ExerciseSubType.ADDITION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(arithmeticExercise.result() <= 100);
    }

    @Test
    void testGenerateAdditionThirdGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.THIRD, ExerciseSubType.ADDITION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000);
    }

    @Test
    void testGenerateAdditionFourthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FOURTH, ExerciseSubType.ADDITION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(arithmeticExercise.result() <= 10000);
    }

    @Test
    void testGenerateAdditionFifthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FIFTH, ExerciseSubType.ADDITION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(arithmeticExercise.result() <= 100000);
    }

    @Test
    void testGenerateAdditionSixthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.SIXTH, ExerciseSubType.ADDITION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000000);
    }

    @Test
    void testGenerateSubtractionFirstGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FIRST, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(arithmeticExercise.result() <= 20 && arithmeticExercise.result() >= 0);
    }

    @Test
    void testGenerateSubtractionSecondGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.SECOND, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(arithmeticExercise.result() <= 100 && arithmeticExercise.result() >= 0);
    }

    @Test
    void testGenerateSubtractionThirdGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.THIRD, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000 && arithmeticExercise.result() >= 0);
    }

    @Test
    void testGenerateSubtractionFourthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FOURTH, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(arithmeticExercise.result() <= 10000 && arithmeticExercise.result() >= 0);
    }

    @Test
    void testGenerateSubtractionFifthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FIFTH, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(arithmeticExercise.result() <= 100000 && arithmeticExercise.result() >= 0);
    }

    @Test
    void testGenerateSubtractionSixthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.SIXTH, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000000 && arithmeticExercise.result() >= 0);
    }

    @Test
    void testGenerateMultiplicationSecondGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.SECOND, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(arithmeticExercise.result() <= 10000);
    }

    @Test
    void testGenerateMultiplicationThirdGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.THIRD, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000);
    }

    @Test
    void testGenerateMultiplicationFourthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FOURTH, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(arithmeticExercise.result() <= 10000);
    }

    @Test
    void testGenerateMultiplicationFifthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FIFTH, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(arithmeticExercise.result() <= 100000);
    }

    @Test
    void testGenerateMultiplicationSixthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.SIXTH, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000000);
    }

    @Test
    void testGenerateDivisionThirdGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.THIRD, ExerciseSubType.DIVISION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000);
    }

    @Test
    void testGenerateDivisionFourthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FOURTH, ExerciseSubType.DIVISION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(arithmeticExercise.result() <= 10000);
    }

    @Test
    void testGenerateDivisionFifthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.FIFTH, ExerciseSubType.DIVISION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(arithmeticExercise.result() <= 100000);
    }

    @Test
    void testGenerateDivisionSixthGrade() {
        Exercise exercise = ArithmeticGenerator.generate(Grade.SIXTH, ExerciseSubType.DIVISION);
        assertInstanceOf(ArithmeticExercise.class, exercise);
        ArithmeticExercise arithmeticExercise = (ArithmeticExercise) exercise;
        assertTrue(arithmeticExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(arithmeticExercise.result() <= 1000000);
    }
}