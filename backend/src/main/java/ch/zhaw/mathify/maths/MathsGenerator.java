package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.*;
import ch.zhaw.mathify.model.exercise.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;

/**
 * Generates exercises for the math basics category
 * (sorting, neighbors, ...)
 */
public class MathsGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(MathsGenerator.class);
    private static final Random random = new Random();

    private MathsGenerator() {
    }

    /**
     * @param grade   The grade of the exercise
     * @param subType The subtype of the exercise
     * @return A new exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType subType, int technicalScore) {
        return switch (subType) {
            case SORTING -> generateSorting(grade, technicalScore);
            case NEIGHBORS -> generateNeighbors(grade, technicalScore);
            case COMPARISON -> generateComparison(grade, technicalScore);
            case NUMBERCOMPLETION -> generateNumberCompletion(grade, technicalScore);
            case TENSCOMPARISON -> generateTensComparison(technicalScore);
            case ADDITION -> generateAddition(grade, technicalScore);
            case SUBTRACTION -> generateSubtraction(grade, technicalScore);
            case MULTIPLICATION -> generateMultiplication(grade, technicalScore);
            case DIVISION -> generateDivision(grade, technicalScore);
            case DOUBLING -> generateDoubling(grade, technicalScore);
            case HALVING -> generateHalving(grade, technicalScore);
            case THREESTEPADDITION -> generateThreeStepAddition(grade, technicalScore);
            case THREESTEPSUBTRACTION -> generateThreeStepSubtraction(grade, technicalScore);
            case MULTIPLICATIONTABLE -> generateMultiplicationTable(grade, technicalScore);
            default -> throw new IllegalArgumentException("Sub type " + subType + " is not supported!");
        };
    }

    private static Exercise generateSorting(Grade grade, int technicalScore) {
        LOG.info("Generating sorting exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        int c = random.nextInt(max + 1);
        double[] result = {a, b, c};
        double[] calculationValues = {a, b, c};
        String arrayUnsorted = Arrays.toString(result);
        Arrays.sort(result);
        return new MathsExercise(result, new double[result.length], arrayUnsorted, calculationValues, ExerciseSubType.SORTING);
    }

    private static Exercise generateNeighbors(Grade grade, int technicalScore) {
        LOG.info("Generating neighbors exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(1, max + 1);
        double[] result = {a - 1, a + 1};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Find the neighbors of " + a + "!", calculationValues, ExerciseSubType.NEIGHBORS);
    }

    private static Exercise generateComparison(Grade grade, int technicalScore) {
        LOG.info("Generating comparison exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a, b, c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a == b || a == c || b == c);
        double[] result = {Math.max(a, Math.max(b, c))};
        double[] calculationValues = {a, b, c};
        return new MathsExercise(result, new double[result.length], "Find the biggest number in the list: " + a + ", " + b + ", " + c, calculationValues, ExerciseSubType.COMPARISON);
    }

    private static Exercise generateNumberCompletion(Grade grade, int technicalScore) {
        LOG.info("Generating number completion exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max);
        int b;
        do {
            b = random.nextInt(max + 1);
        } while (b <= a);
        double[] result = {b - a};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], "What number do you add to " + a + " to make " + b + "?", calculationValues, ExerciseSubType.NUMBERCOMPLETION);
    }

    private static Exercise generateTensComparison(int technicalScore) {
        LOG.info("Generating tens comparison exercise");
        int a = (int) Math.round((random.nextInt(9) * 10 + 10) * getDifficultyFactor(technicalScore));
        int b;
        do {
            b = (int) Math.round((random.nextInt(9) * 10 + 10) * getDifficultyFactor(technicalScore));
        } while (b == a);
        double[] result = {Math.max(a, b)};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], "Which tens number is bigger: " + a + " or " + b + "?", calculationValues, ExerciseSubType.TENSCOMPARISON);
    }

    private static Exercise generateDivision(Grade grade, int technicalScore) {
        LOG.info("Generating division exercise");
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Division is not supported for grade one!");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = getRandomFactor(a);
        double[] result = {(double) a / b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " / " + b, calculationValues, ExerciseSubType.DIVISION);
    }

    private static Exercise generateMultiplication(Grade grade, int technicalScore) {
        LOG.info("Generating multiplication exercise");
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Multiplication is not supported for grade one!");
        int max = (int) Math.sqrt(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        double[] result = {a * b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " * " + b, calculationValues, ExerciseSubType.MULTIPLICATION);
    }

    private static Exercise generateSubtraction(Grade grade, int technicalScore) {
        LOG.info("Generating subtraction exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(a + 1);
        double[] result = {a - b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " - " + b, calculationValues, ExerciseSubType.SUBTRACTION);
    }

    private static Exercise generateAddition(Grade grade, int technicalScore) {
        LOG.info("Generating addition exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max - a + 1);
        double[] result = {a + b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " + " + b, calculationValues, ExerciseSubType.ADDITION);
    }

    private static Exercise generateDoubling(Grade grade, int technicalScore) {
        LOG.info("Generating doubling exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1) * 2;
        double[] result = {a};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Double the following number: " + (a / 2), calculationValues, ExerciseSubType.DOUBLING);
    }

    private static Exercise generateHalving(Grade grade, int technicalScore) {
        LOG.info("Generating halving exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a;
        do {
            a = random.nextInt(max + 1) / 2;
        } while (a % 2 != 0);
        double[] result = {a};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Halve the following number: " + (a * 2), calculationValues, ExerciseSubType.HALVING);
    }

    private static Exercise generateThreeStepAddition(Grade grade, int technicalScore) {
        LOG.info("Generating three step addition exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a, b, c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a + b + c > max);
        double[] result = {a + b + c};
        double[] calculationValues = {a, b, c};
        return new MathsExercise(result, new double[result.length], a + " + " + b + " + " + c, calculationValues, ExerciseSubType.THREESTEPADDITION);
    }

    private static Exercise generateThreeStepSubtraction(Grade grade, int technicalScore) {
        LOG.info("Generating three step subtraction exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a, b, c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a - b - c < 0);
        double[] result = {a - b - c};
        double[] calculationValues = {a, b, c};
        return new MathsExercise(result, new double[result.length], a + " - " + b + " - " + c, calculationValues, ExerciseSubType.THREESTEPSUBTRACTION);
    }

    private static Exercise generateMultiplicationTable(Grade grade, int technicalScore) {
        LOG.info("Generating multiplication table exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(1, max + 1);
        double[] result = new double[10];
        double[] calculationValues = {a};
        for (int i = 0; i < result.length; i++) {
            result[i] = a * (i + 1);
        }
        return new MathsExercise(result, new double[result.length], "Generate the multiplication table for " + a, calculationValues, ExerciseSubType.MULTIPLICATIONTABLE);
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

    private static double getDifficultyFactor(int num) {
        if (num < 1 || num > 10) {
            throw new IllegalArgumentException("Number must be between 1 and 10.");
        }
        if (num <= 3) return 0.25;
        if (num <= 6) return 0.5;
        if (num <= 9) return 0.75;
        return 1;
    }
}
