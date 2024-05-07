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
    void testSorting() {
        int technicalScore = 1;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.SORTING, technicalScore);
        MathsExercise mathsExercise = (MathsExercise) exercise;
        assertTrue(mathsExercise.result()[0] <= mathsExercise.result()[1]);
        assertTrue(mathsExercise.result()[1] <= mathsExercise.result()[2]);
    }

    @Test
    void testNeighbors() {
        int technicalScore = 2;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.NEIGHBORS, technicalScore);
        MathsExercise mathsExercise = (MathsExercise) exercise;
        assertEquals(mathsExercise.result()[0], mathsExercise.result()[1] - 2);
    }

    @Test
    void testComparison() {
        int technicalScore = 3;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.COMPARISON, technicalScore);
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
        int technicalScore = 4;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.NUMBERCOMPLETION, technicalScore);
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
        int technicalScore = 5;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.TENSCOMPARISON, technicalScore);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        double[] values = mathsExercise.calculationValues();
        double a = values[0];
        double b = values[1];

        assertTrue(a != b);
        assertTrue(a % 10 == 0 && b % 10 == 0);
        assertEquals(mathsExercise.result()[0], Math.max(a, b));
    }

    @Test
    void testGenerateAdditionFirstGrade() {
        int technicalScore = 6;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.ADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 20);
    }

    @Test
    void testGenerateAdditionSecondGrade() {
        int technicalScore = 7;
        Exercise exercise = MathsGenerator.generate(Grade.SECOND, ExerciseSubType.ADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 100);
    }

    @Test
    void testGenerateAdditionThirdGrade() {
        int technicalScore = 8;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.ADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000);
    }

    @Test
    void testGenerateAdditionFourthGrade() {
        int technicalScore = 9;
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.ADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateAdditionFifthGrade() {
        int technicalScore = 10;
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.ADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 100000);
    }

    @Test
    void testGenerateAdditionSixthGrade() {
        int technicalScore = 1;
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.ADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000000);
    }

    @Test
    void testGenerateSubtractionFirstGrade() {
        int technicalScore = 2;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.SUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 20 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionSecondGrade() {
        int technicalScore = 3;
        Exercise exercise = MathsGenerator.generate(Grade.SECOND, ExerciseSubType.SUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 100 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionThirdGrade() {
        int technicalScore = 4;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.SUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionFourthGrade() {
        int technicalScore = 5;
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.SUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 10000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionFifthGrade() {
        int technicalScore = 6;
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.SUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 100000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateSubtractionSixthGrade() {
        int technicalScore = 9;
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.SUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000000 && MathsExercise.result()[0] >= 0);
    }

    @Test
    void testGenerateMultiplicationSecondGrade() {
        int technicalScore = 8;
        Exercise exercise = MathsGenerator.generate(Grade.SECOND, ExerciseSubType.MULTIPLICATION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateMultiplicationThirdGrade() {
        int technicalScore = 9;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.MULTIPLICATION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000);
    }

    @Test
    void testGenerateMultiplicationFourthGrade() {
        int technicalScore = 10;
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.MULTIPLICATION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateMultiplicationFifthGrade() {
        int technicalScore = 1;
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.MULTIPLICATION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 100000);
    }

    @Test
    void testGenerateMultiplicationSixthGrade() {
        int technicalScore = 9;
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.MULTIPLICATION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\* \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000000);
    }

    @Test
    void testGenerateDivisionThirdGrade() {
        int technicalScore = 3;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.DIVISION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000);
    }

    @Test
    void testGenerateDivisionFourthGrade() {
        int technicalScore = 4;
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.DIVISION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 10000);
    }

    @Test
    void testGenerateDivisionFifthGrade() {
        int technicalScore = 5;
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.DIVISION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 100000);
    }

    @Test
    void testGenerateDivisionSixthGrade() {
        int technicalScore = 6;
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.DIVISION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ / \\d+ = \\?"));
        assertTrue(MathsExercise.result()[0] <= 1000000);
    }

    @Test
    void testGenerateDoublingThirdGrade() {
        int technicalScore = 7;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.DOUBLING, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("Double the following number: \\d+"));
        assertEquals(MathsExercise.result()[0], MathsExercise.calculationValues()[0]);
    }

    @Test
    void testGenerateHalvingFourthGrade() {
        int technicalScore = 8;
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.HALVING, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("Halve the following number: \\d+"));
        assertEquals(MathsExercise.result()[0], MathsExercise.calculationValues()[0]);
    }

    @Test
    void testGenerateThreeStepAdditionFifthGrade() {
        int technicalScore = 9;
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.THREESTEPADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ \\+ \\d+ = \\?"));
        double calculationValues = MathsExercise.calculationValues()[0] + MathsExercise.calculationValues()[1] + MathsExercise.calculationValues()[2];
        assertEquals(MathsExercise.result()[0], calculationValues);
    }

    @Test
    void testGenerateThreeStepSubtractionSixthGrade() {
        int technicalScore = 10;
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.THREESTEPSUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ - \\d+ = \\?"));
        double calculationValues = MathsExercise.calculationValues()[0] - MathsExercise.calculationValues()[1] - MathsExercise.calculationValues()[2];
        assertEquals(MathsExercise.result()[0], calculationValues);
    }

    @Test
    void testGenerateMultiplicationTableFirstGrade() {
        int technicalScore = 1;
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.MULTIPLICATIONTABLE, technicalScore);
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

    @Test
    void testRoundingTen() {
        int technicalScore = 2;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.ROUNDINGTEN, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise mathsExercise = (MathsExercise) exercise;
        double[] values = mathsExercise.calculationValues();
        double a = values[0];
        double roundedNumber = Math.round(a / 10.0) * 10.0;
        assertEquals(roundedNumber, mathsExercise.result()[0]);
    }

    @Test
    void testRoundingTenError() {
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.FIRST, ExerciseSubType.ROUNDINGTEN, 3));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.SECOND, ExerciseSubType.ROUNDINGTEN, 4));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.ROUNDINGTEN, 5));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.ROUNDINGTEN, 6));
        assertThrows(IllegalArgumentException.class, () -> MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.ROUNDINGTEN, 7));
    }

    @Test
    void testLongAddition() {
        int technicalScore = 3;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.LONGADDITION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        assertEquals(mathsExercise.result()[mathsExercise.result().length - 1], mathsExercise.calculationValues()[0] + mathsExercise.calculationValues()[1]);

        for (int i = 0; i < mathsExercise.result().length - 1; i++) {
            if (i % 2 == 0) {
                assertTrue(mathsExercise.result()[i] >= 0 && mathsExercise.result()[i] <= 9);
            } else {
                assertTrue(mathsExercise.result()[i] == 0 || mathsExercise.result()[i] == 1);
            }
        }
    }

    @Test
    void testLongSubtraction() {
        int technicalScore = 4;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.LONGSUBTRACTION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        assertEquals(mathsExercise.result()[mathsExercise.result().length - 1], mathsExercise.calculationValues()[0] - mathsExercise.calculationValues()[1]);

        for (int i = 0; i < mathsExercise.result().length - 1; i++) {
            if (i % 2 == 0) {
                assertTrue(mathsExercise.result()[i] >= 0 && mathsExercise.result()[i] <= 9);
            } else {
                assertTrue(mathsExercise.result()[i] == 0 || mathsExercise.result()[i] == 1);
            }
        }
    }

    @Test
    void testLongMultiplication() {
        int technicalScore = 5;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.LONGMULTIPLICATION, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        assertEquals(mathsExercise.result()[mathsExercise.result().length - 1], mathsExercise.calculationValues()[0] * mathsExercise.calculationValues()[1]);

        for (int i = 0; i < mathsExercise.result().length - 1; i++) {
            assertTrue(mathsExercise.result()[i] >= 0 && mathsExercise.result()[i] <= 9);
        }
    }

    @Test
    void testOrderOfOperations() {
        int technicalScore = 6;
        Exercise exercise = MathsGenerator.generate(Grade.THIRD, ExerciseSubType.ORDEROFOPERATIONS, technicalScore);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        assertEquals(mathsExercise.result()[0], Math.floor(mathsExercise.result()[0]));
        assertTrue(mathsExercise.result()[0] >= 0);
    }
}
