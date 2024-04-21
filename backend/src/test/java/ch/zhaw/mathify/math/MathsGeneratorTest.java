package ch.zhaw.mathify.math;

import ch.zhaw.mathify.maths.MathsGenerator;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.MathsExercise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * Tests for the MathBasicsGenerator class.
 */
class MathsGeneratorTest {
    @Test
    void testRoundingTen() {
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.ROUNDING_TEN);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise mathsExercise = (MathsExercise) exercise;
        double[] values = mathsExercise.calculationValues();
        double a = values[0];
        double roundedNumber = Math.round(a / 10.0) * 10.0;
        assertEquals(roundedNumber, mathsExercise.result()[0]);
    }

    @Test
    void testRoundingTenError(){
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.FIRST, ExerciseSubType.ROUNDING_TEN));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.SECOND, ExerciseSubType.ROUNDING_TEN));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.ROUNDING_TEN));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.ROUNDING_TEN));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.ROUNDING_TEN));
    }

    @Test
    void testSorting() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.SORTING);
        MathsExercise mathsExercise = (MathsExercise) exercise;
        assertTrue(mathsExercise.result()[0] <= mathsExercise.result()[1]);
        assertTrue(mathsExercise.result()[1] <= mathsExercise.result()[2]);
    }

    @Test
    void testNeighbors() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.NEIGHBORS);
        MathsExercise mathsExercise = (MathsExercise) exercise;
        assertEquals(mathsExercise.result()[0], mathsExercise.result()[1] - 2);
    }

    @Test
    void testComparison() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.COMPARISON);
        MathsExercise mathsExercise = (MathsExercise) exercise;
        double[] values = mathsExercise.calculationValues();
        double a = values[0];
        double b = values[1];
        double c = values[2];

        assertTrue(mathsExercise.result()[0] == a || mathsExercise.result()[0] == b || mathsExercise.result()[0] == c);
        assertTrue(a != b && a != c && b != c);
        assertEquals(mathsExercise.result()[0], Math.max(a, Math.max(b, c)));
    }

    @Test
    void testNumberCompletion() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.NUMBERCOMPLETION);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        double[] values = mathsExercise.calculationValues();
        double a = values[0];
        double b = values[1];
        double c = b - a;
        assertTrue(b > a);
        assertEquals(c, mathsExercise.result()[0]);
    }

    @Test
    void testTensComparison() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.TENSCOMPARISON);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        double[] values = mathsExercise.calculationValues();
        double a = values[0];
        double b = values[1];

        assertTrue(a != b);
        assertEquals(mathsExercise.result()[0], Math.max(a, b));
    }

    @Test
    void testGenerateAdditionFirstGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.ADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(MathsExercise.result()[0] <= 20);
    }

    @Test
    void testGenerateAdditionSecondGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SECOND, ExerciseSubType.ADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(MathsExercise.result()[0] <= 100);
    }

    @Test
    void testGenerateAdditionThirdGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.ADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000);
    }

    @Test
    void testGenerateAdditionFourthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.ADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateAdditionFifthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.ADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(MathsExercise.result()[0] <= 100000);
    }

    @Test
    void testGenerateAdditionSixthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.ADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000000);
    }

    @Test
    void testGenerateSubtractionFirstGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(MathsExercise.result()[0] <= 20 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionSecondGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SECOND, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(MathsExercise.result()[0] <= 100 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionThirdGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionFourthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(MathsExercise.result()[0] <= 10000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionFifthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(MathsExercise.result()[0] <= 100000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionSixthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.SUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateMultiplicationSecondGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SECOND, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateMultiplicationThirdGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000);
    }

    @Test
    void testGenerateMultiplicationFourthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateMultiplicationFifthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(MathsExercise.result()[0] <= 100000);
    }

    @Test
    void testGenerateMultiplicationSixthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.MULTIPLICATION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000000);
    }

    @Test
    void testGenerateDivisionThirdGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.DIVISION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000);
    }

    @Test
    void testGenerateDivisionFourthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.DIVISION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateDivisionFifthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.DIVISION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(MathsExercise.result()[0] <= 100000);
    }

    @Test
    void testGenerateDivisionSixthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.DIVISION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+"));
        assertTrue(MathsExercise.result()[0] <= 1000000);
    }

    @Test
    void testGenerateDoublingThirdGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.DOUBLING);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("Double the following number: \\d+"));
        assertEquals(MathsExercise.result()[0], MathsExercise.calculationValues()[0]);
    }

    @Test
    void testGenerateHalvingFourthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.HALVING);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("Halve the following number: \\d+"));
        assertEquals(MathsExercise.result()[0], MathsExercise.calculationValues()[0]);
    }

    @Test
    void testGenerateThreeStepAdditionFifthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.THREESTEPADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ \\+ \\d+"));
        double calculationValues = MathsExercise.calculationValues()[0] + MathsExercise.calculationValues()[1] + MathsExercise.calculationValues()[2];
        assertEquals(MathsExercise.result()[0], calculationValues);
    }

    @Test
    void testGenerateThreeStepSubtractionSixthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.THREESTEPSUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ - \\d+"));
        double calculationValues = MathsExercise.calculationValues()[0] - MathsExercise.calculationValues()[1] - MathsExercise.calculationValues()[2];
        assertEquals(MathsExercise.result()[0], calculationValues);
    }

    @Test
    void testGenerateMultiplicationTableFirstGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.MULTIPLICATIONTABLE);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("Generate the multiplication table for \\d+"));
        double[] result = new double[10];
        for (int i = 0; i < result.length; i++) {
            result[i] = MathsExercise.calculationValues()[0] * (i + 1);
        }
        for (int i = 0; i < result.length; i++) {
            assertEquals(MathsExercise.result()[i], result[i]);
        }
    }
}
