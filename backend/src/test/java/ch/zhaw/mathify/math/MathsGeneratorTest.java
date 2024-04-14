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
        String prompt = mathsExercise.toString();
        String[] values = prompt.split(":")[1].trim().split(", ");
        int a = Integer.parseInt(values[0]);
        int b = Integer.parseInt(values[1]);
        int c = Integer.parseInt(values[2]);

        assertTrue(mathsExercise.result()[0] == a || mathsExercise.result()[0] == b || mathsExercise.result()[0] == c);
        assertTrue(a != b && a != c && b != c);
        assertEquals(mathsExercise.result()[0], Math.max(a, Math.max(b, c)));
    }

    @Test
    void testNumberCompletion() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.NUMBERCOMPLETION);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        String[] parts = mathsExercise.exercise().split(" to make ");
        String[] prompt = parts[0].split(" add to ");
        int a = Integer.parseInt(prompt[1]);
        int b = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
        int c = b - a;
        assertTrue(b > a);
        assertEquals(c, mathsExercise.result()[0]);
    }

    @Test
    void testTensComparison() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.TENSCOMPARISON);
        MathsExercise mathsExercise = (MathsExercise) exercise;

        String[] prompt = mathsExercise.exercise().split(" is bigger: ");
        String[] numbers = prompt[1].split(" or ");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1].replace("?", ""));

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
        int number = Integer.parseInt(MathsExercise.exercise().replaceAll("[^0-9]+", "")) * 2;
        assertEquals(MathsExercise.result()[0], number);
    }

    @Test
    void testGenerateHalvingFourthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FOURTH, ExerciseSubType.HALVING);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("Halve the following number: \\d+"));
        int number = Integer.parseInt(MathsExercise.exercise().replaceAll("[^0-9]+", "")) / 2;
        assertEquals(MathsExercise.result()[0], number);
    }

    @Test
    void testGenerateThreeStepAdditionFifthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIFTH, ExerciseSubType.THREESTEPADDITION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ \\+ \\d+ \\+ \\d+"));
        String[] numbers = ((MathsExercise) exercise).exercise().split("\\s*\\+\\s*");
        int result = Integer.parseInt(numbers[0]) + Integer.parseInt(numbers[1]) + Integer.parseInt(numbers[2]);
        assertEquals(MathsExercise.result()[0], result);
    }

    @Test
    void testGenerateThreeStepSubtractionSixthGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.SIXTH, ExerciseSubType.THREESTEPSUBTRACTION);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("\\d+ - \\d+ - \\d+"));
        String[] numbers = ((MathsExercise) exercise).exercise().split("\\s*-\\s*");
        int result = Integer.parseInt(numbers[0]) - Integer.parseInt(numbers[1]) - Integer.parseInt(numbers[2]);
        assertEquals(MathsExercise.result()[0], result);
    }

    @Test
    void testGenerateMultiplicationTableFirstGrade() {
        Exercise exercise = MathsGenerator.generate(Grade.FIRST, ExerciseSubType.MULTIPLICATIONTABLE);
        assertInstanceOf(MathsExercise.class, exercise);
        MathsExercise MathsExercise = (MathsExercise) exercise;
        assertTrue(MathsExercise.exercise().matches("Generate the multiplication table for \\d+"));
        double[] result = new double[10];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(MathsExercise.exercise().replaceAll("[^0-9]+", "")) * (i + 1);
        }
        for (int i = 0; i < result.length; i++) {
            assertEquals(MathsExercise.result()[i], result[i]);
        }
    }
}
