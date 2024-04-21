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
            case DOUBLING -> generateDoubling(grade);
            case HALVING -> generateHalving(grade);
            case THREESTEPADDITION -> generateThreeStepAddition(grade);
            case THREESTEPSUBTRACTION -> generateThreeStepSubtraction(grade);
            case MULTIPLICATIONTABLE -> generateMultiplicationTable(grade);
            case ROUNDING_TEN -> generateRounding(grade);
            default -> throw new IllegalArgumentException("Sub type " + subType + " is not supported!");
        };
    }

    private static Exercise generateRounding(Grade grade) {
        LOG.info("Generating rounding exercise");

        if(grade != Grade.THIRD) {
            LOG.error("Rounding is only supported for grade three");
            throw new IllegalArgumentException("Rounding is only supported for grade three");
        }

        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        double roundedNumber = Math.round(a / 10.0) * 10.0;
        double[] result = {roundedNumber};
        return new MathsExercise(result, new double[result.length], "Round the following number to the next ten: " + a, result, ExerciseSubType.ROUNDING_TEN);
    }

    private static Exercise generateSorting(Grade grade) {
        LOG.info("Generating sorting exercise");
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        int c = random.nextInt(max + 1);
        double[] result = {a, b, c};
        double[] calculationValues = {a, b, c};
        String arrayUnsorted = Arrays.toString(result);
        Arrays.sort(result);
        return new MathsExercise(result, new double[result.length], arrayUnsorted, calculationValues, ExerciseSubType.SORTING);
    }

    private static Exercise generateNeighbors(Grade grade) {
        LOG.info("Generating neighbors exercise");
        int max = grade.getMax();
        int a = random.nextInt(1, max + 1);
        double[] result = {a - 1, a + 1};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Find the neighbors of " + a + "!", calculationValues, ExerciseSubType.NEIGHBORS);
    }

    private static Exercise generateComparison(Grade grade) {
        LOG.info("Generating comparison exercise");
        int max = grade.getMax();
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

    private static Exercise generateNumberCompletion(Grade grade) {
        LOG.info("Generating number completion exercise");
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b;
        do {
            b = random.nextInt(max + 1);
        } while (b <= a);
        double[] result = {b - a};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], "What number do you add to " + a + " to make " + b + "?", calculationValues, ExerciseSubType.NUMBERCOMPLETION);
    }

    private static Exercise generateTensComparison() {
        LOG.info("Generating tens comparison exercise");
        int a = random.nextInt(9) * 10 + 10;
        int b;
        do {
            b = random.nextInt(9) * 10 + 10;
        } while (b == a);
        double[] result = {Math.max(a, b)};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], "Which tens number is bigger: " + a + " or " + b + "?", calculationValues, ExerciseSubType.TENSCOMPARISON);
    }

    private static Exercise generateDivision(Grade grade) {
        LOG.info("Generating division exercise");
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Division is not supported for grade one!");
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = getRandomFactor(a);
        double[] result = {(double) a / b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " / " + b, calculationValues, ExerciseSubType.DIVISION);
    }

    private static Exercise generateMultiplication(Grade grade) {
        LOG.info("Generating multiplication exercise");
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Multiplication is not supported for grade one!");
        int max = (int) Math.sqrt(grade.getMax());
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        double[] result = {a * b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " * " + b, calculationValues, ExerciseSubType.MULTIPLICATION);
    }

    private static Exercise generateSubtraction(Grade grade) {
        LOG.info("Generating subtraction exercise");
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(a + 1);
        double[] result = {a - b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " - " + b, calculationValues, ExerciseSubType.SUBTRACTION);
    }

    private static Exercise generateAddition(Grade grade) {
        LOG.info("Generating addition exercise");
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max - a + 1);
        double[] result = {a + b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " + " + b, calculationValues, ExerciseSubType.ADDITION);
    }

    private static Exercise generateDoubling(Grade grade) {
        LOG.info("Generating doubling exercise");
        int max = grade.getMax() / 2;
        int a = random.nextInt(max + 1) * 2;
        double[] result = {a};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Double the following number: " + (a / 2), calculationValues, ExerciseSubType.DOUBLING);
    }

    private static Exercise generateHalving(Grade grade) {
        LOG.info("Generating halving exercise");
        int max = grade.getMax();
        int a;
        do {
            a = random.nextInt(max + 1) / 2;
        } while (a % 2 != 0);
        double[] result = {a};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Halve the following number: " + (a * 2), calculationValues, ExerciseSubType.HALVING);
    }

    private static Exercise generateThreeStepAddition(Grade grade) {
        LOG.info("Generating three step addition exercise");
        int max = grade.getMax();
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

    private static Exercise generateThreeStepSubtraction(Grade grade) {
        LOG.info("Generating three step subtraction exercise");
        int max = grade.getMax();
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

    private static Exercise generateMultiplicationTable(Grade grade) {
        LOG.info("Generating multiplication table exercise");
        int max = grade.getMax();
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
}
