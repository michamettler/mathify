package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.*;
import ch.zhaw.mathify.model.exercise.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Generates exercises for the math basics category
 * (sorting, neighbors, ...)
 */
public class MathsGenerator {

    private static final Random random = new Random();

    private MathsGenerator() {
    }

    /**
     * @param grade   The grade of the exercise
     * @param subType The sub type of the exercise
     * @return A new exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType subType) {
        return switch (subType) {
            case SORTING -> generateSorting(grade);
            case NEIGHBORS -> generateNeighbors(grade);
            case COMPARISON -> generateComparison(grade);
            case NUMBERCOMPLETION -> generateNumberCompletion(grade);
            case TENSCOMPARISON -> generateTensComparison();
            case ADDITION -> generateAddition(grade);
            case SUBTRACTION -> generateSubtraction(grade);
            case MULTIPLICATION -> generateMultiplication(grade);
            case DIVISION -> generateDivision(grade);
            default -> throw new IllegalArgumentException("Sub type " + subType + " is not supported!");
        };
    }

    private static Exercise generateSorting(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        int c = random.nextInt(max + 1);
        double[] result = {a, b, c};
        String arrayUnsorted = Arrays.toString(result);
        Arrays.sort(result);
        return new MathsExercise(result, new double[result.length], arrayUnsorted, ExerciseSubType.SORTING);
    }

    private static Exercise generateNeighbors(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(1,max + 1);
        double[] result = {a - 1, a + 1};
        return new MathsExercise(result, new double[result.length], "Find the neighbors of " + a + "!", ExerciseSubType.NEIGHBORS);
    }

    private static Exercise generateComparison(Grade grade) {
        int max = grade.getMax();
        int a, b, c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a == b || a == c || b == c);
        double[] result = {Math.max(a, Math.max(b, c))};
        return new MathsExercise(result, new double[result.length],"Find the biggest number in the list: " + a + ", " + b + ", " + c, ExerciseSubType.COMPARISON);
    }

    private static Exercise generateNumberCompletion(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b;
        do {
            b = random.nextInt(max + 1);
        } while (b <= a);
        double[] result = {b - a};
        return new MathsExercise(result, new double[result.length],"What number do you add to " + a + " to make " + b + "?", ExerciseSubType.NUMBERCOMPLETION);
    }

    private static Exercise generateTensComparison() {
        int a = random.nextInt(9) * 10 + 10;
        int b;
        do {
            b = random.nextInt(9) * 10 + 10;
        } while (b == a);
        double[] result = {Math.max(a, b)};
        return new MathsExercise(result, new double[result.length], "Which tens number is bigger: " + a + " or " + b + "?", ExerciseSubType.TENSCOMPARISON);
    }

    private static Exercise generateDivision(Grade grade) {
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Division is not supported for grade one!");
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = getRandomFactor(a);
        double[] result = {(double) a / b};
        return new MathsExercise(result, new double[result.length], a + " / " + b, ExerciseSubType.DIVISION);
    }

    private static Exercise generateMultiplication(Grade grade) {
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Multiplication is not supported for grade one!");
        int max = (int) Math.sqrt(grade.getMax());
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        double[] result = {a * b};
        return new MathsExercise(result, new double[result.length], a + " * " + b, ExerciseSubType.MULTIPLICATION);
    }

    private static Exercise generateSubtraction(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(a + 1);
        double[] result = {a - b};
        return new MathsExercise(result, new double[result.length], a + " - " + b, ExerciseSubType.SUBTRACTION);
    }

    private static Exercise generateAddition(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max - a + 1);
        double[] result = {a + b};
        return new MathsExercise(result, new double[result.length], a + " + " + b, ExerciseSubType.ADDITION);
    }

    private static int getRandomFactor(int num) {
        int factor = num;
        while (factor == num) {
            factor = random.nextInt(num) + 1;
            while (num % factor != 0) {
                factor--;
            }
        }
        return factor;
    }
}
