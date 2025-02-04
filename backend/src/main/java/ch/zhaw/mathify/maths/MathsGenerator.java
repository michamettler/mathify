package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.exercise.MathsExercise;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            case ROUNDINGTEN -> generateRounding(grade, technicalScore);
            case LONGADDITION -> generateLongAddition(grade, technicalScore);
            case LONGSUBTRACTION -> generateLongSubtraction(grade, technicalScore);
            case LONGMULTIPLICATION -> generateLongMultiplication(grade, technicalScore);
            case ORDEROFOPERATIONS -> generateOrderOfOperations(grade, technicalScore);
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
        Arrays.sort(result);
        return new MathsExercise(result, new double[result.length], "Sort the following numbers: " + a + ", " + b + ", " + c, calculationValues,
                "Think about putting things in order from smallest to largest or vice versa. How would you arrange a line of toys or a set of cards?",
                ExerciseSubType.SORTING);
    }

    private static Exercise generateNeighbors(Grade grade, int technicalScore) {
        LOG.info("Generating neighbors exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(1, max + 1);
        double[] result = {a - 1, a + 1};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Find the neighbors of " + a + "!", calculationValues,
                "Look at the numbers or objects next to each other. What comes before and after? How are they related?",
                ExerciseSubType.NEIGHBORS);
    }

    private static Exercise generateComparison(Grade grade, int technicalScore) {
        LOG.info("Generating comparison exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a;
        int b;
        int c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a == b || a == c || b == c);
        double[] result = {Math.max(a, Math.max(b, c))};
        double[] calculationValues = {a, b, c};
        return new MathsExercise(result, new double[result.length], "Find the biggest number: " + a + ", " + b + ", " + c, calculationValues,
                "Compare things to see which one is bigger, smaller, or the same. How would you decide which toy has more blocks or which fruit is heavier?",
                ExerciseSubType.COMPARISON);
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
        return new MathsExercise(result, new double[result.length], "What number do you add to " + a + " to make " + b + "?", calculationValues,
                "Look for patterns or sequences in numbers. What comes next after 2, 4, 6, ...?",
                ExerciseSubType.NUMBERCOMPLETION);
    }

    private static Exercise generateTensComparison(int technicalScore) {
        LOG.info("Generating tens comparison exercise");
        int a = random.nextInt((int) Math.round(9 * getDifficultyFactor(technicalScore))) * 10 + 10;
        int b;
        do {
            b = random.nextInt((int) Math.round(9 * getDifficultyFactor(technicalScore))) * 10 + 10;
        } while (b == a);
        double[] result = {Math.max(a, b)};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], "Which tens number is bigger: " + a + " or " + b + "?", calculationValues,
                "Look at the tens place in numbers. Which number has more tens? How many tens are in 20 or 30?",
                ExerciseSubType.TENSCOMPARISON);
    }

    private static Exercise generateAddition(Grade grade, int technicalScore) {
        LOG.info("Generating addition exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max - a + 1);
        double[] result = {a + b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " + " + b + " = ?", calculationValues,
                "Put two groups of things together. If you have 3 apples and get 2 more, how many apples do you have now?",
                ExerciseSubType.ADDITION);
    }

    private static Exercise generateSubtraction(Grade grade, int technicalScore) {
        LOG.info("Generating subtraction exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(a + 1);
        double[] result = {a - b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " - " + b + " = ?", calculationValues,
                "Take away some things from a group. If you have 5 candies and eat 2, how many candies do you have left?",
                ExerciseSubType.SUBTRACTION);
    }

    private static Exercise generateMultiplication(Grade grade, int technicalScore) {
        LOG.info("Generating multiplication exercise");
        int max = (int) Math.sqrt(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        double[] result = {a * b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " * " + b + " = ?", calculationValues,
                "Make groups of things. If you have 3 groups of 4 blocks, how many blocks do you have in total?",
                ExerciseSubType.MULTIPLICATION);
    }

    private static Exercise generateDivision(Grade grade, int technicalScore) {
        LOG.info("Generating division exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1) + 1;
        int b = getRandomFactor(a);
        double[] result = {(double) a / b};
        double[] calculationValues = {a, b};
        return new MathsExercise(result, new double[result.length], a + " / " + b + " = ?", calculationValues,
                "Share things equally into groups. If you have 12 candies and want to share them equally among 4 friends, how many candies does each friend get?",
                ExerciseSubType.DIVISION);
    }

    private static Exercise generateDoubling(Grade grade, int technicalScore) {
        LOG.info("Generating doubling exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1) * 2;
        double[] result = {a};
        double[] calculationValues = {a};
        return new MathsExercise(result, new double[result.length], "Double the following number: " + (a / 2), calculationValues,
                "Double means to have twice as many. If you have 4 cookies and double them, how many cookies do you have now?",
                ExerciseSubType.DOUBLING);
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
        return new MathsExercise(result, new double[result.length], "Halve the following number: " + (a * 2), calculationValues,
                "Halving means to split something into two equal parts. If you have 8 candies and halve them, how many candies does each person get?",
                ExerciseSubType.HALVING);
    }

    private static Exercise generateThreeStepAddition(Grade grade, int technicalScore) {
        LOG.info("Generating three step addition exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a;
        int b;
        int c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a + b + c > max);
        double[] result = {a + b + c};
        double[] calculationValues = {a, b, c};
        return new MathsExercise(result, new double[result.length], a + " + " + b + " + " + c + " = ?", calculationValues,
                "To add three numbers, first add the first two, then add the result to the third number.",
                ExerciseSubType.THREESTEPADDITION);
    }

    private static Exercise generateThreeStepSubtraction(Grade grade, int technicalScore) {
        LOG.info("Generating three step subtraction exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a;
        int b;
        int c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a - b - c < 0);
        double[] result = {a - b - c};
        double[] calculationValues = {a, b, c};
        return new MathsExercise(result, new double[result.length], a + " - " + b + " - " + c + " = ?", calculationValues,
                "To subtract three numbers, first subtract the second from the first, then subtract the third from the result.",
                ExerciseSubType.THREESTEPSUBTRACTION);
    }

    private static Exercise generateMultiplicationTable(Grade grade, int technicalScore) {
        LOG.info("Generating multiplication table exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(1, max + 1);
        double[] result = new double[10];
        double[] calculationValues = {a};
        for (int i = 0; i < result.length; i++) {
            result[i] = (double) a * (i + 1);
        }
        return new MathsExercise(result, new double[result.length], "Generate the multiplication table for " + a, calculationValues,
                "To make a multiplication table, multiply a number by the numbers from 1 to 10.",
                ExerciseSubType.MULTIPLICATIONTABLE);
    }

    private static Exercise generateRounding(Grade grade, int technicalScore) {
        LOG.info("Generating rounding exercise");

        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        double roundedNumber = Math.round(a / 10.0) * 10.0;
        double[] result = {roundedNumber};
        return new MathsExercise(result, new double[result.length], "Round the following number to the next ten: " + a, result,
                "Round numbers to the nearest ten. If you have 17 apples, how many tens do you have?",
                ExerciseSubType.ROUNDINGTEN);
    }

    private static Exercise generateLongAddition(Grade grade, int technicalScore) {
        LOG.info("Generating long addition exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        double[] calculationValues = {a, b};

        int amountOfDigits = countDigits(a + b);
        int amountOfCarryOvers = amountOfDigits - 1;
        int additionalCarryOver = 0;
        if ((a + b) % 10 == 0) additionalCarryOver = 1;

        double[] result = new double[amountOfDigits + amountOfCarryOvers + additionalCarryOver + 1];
        int carryOver = 0;
        int tempA = a;
        int tempB = b;

        for (int i = 0; i < amountOfDigits; i++) {
            result[2 * i] = (tempA % 10 + tempB % 10 + carryOver) % 10;
            carryOver = (tempA % 10 + tempB % 10 + carryOver >= 10) ? 1 : 0;
            if (carryOver == 1 || tempA / 10 != 0 || tempB / 10 != 0) result[2 * i + 1] = carryOver;
            tempA /= 10;
            tempB /= 10;
        }

        result[result.length - 1] = (double) a + b;

        return new MathsExercise(result, new double[result.length], "Calculate " + a + " + " + b + " using long addition", calculationValues,
                "When adding large numbers, start from the right and work your way left. Add each column separately, carrying over any excess to the next column if needed.",
                ExerciseSubType.LONGADDITION);
    }

    private static Exercise generateLongSubtraction(Grade grade, int technicalScore) {
        LOG.info("Generating long subtraction exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b;
        do {
            b = random.nextInt(max + 1);
        } while (a < b);
        double[] calculationValues = {a, b};

        int amountOfDigits = countDigits(a);
        int amountOfCarryOvers = amountOfDigits - 1;

        double[] result = new double[amountOfDigits + amountOfCarryOvers + 1];
        int carryOver = 0;
        int tempA = a;
        int tempB = b;

        for (int i = 0; i < countDigits(a - b); i++) {
            int difference = tempA % 10 - tempB % 10 - carryOver;
            if (difference < 0) {
                result[2 * i] = (double) 10 - Math.abs(difference);
            } else {
                result[2 * i] = Math.abs(difference);
            }
            carryOver = (tempA % 10 - tempB % 10 - carryOver < 0) ? 1 : 0;
            if (carryOver == 1 || tempA / 10 != 0 || tempB / 10 != 0) result[2 * i + 1] = carryOver;
            tempA /= 10;
            tempB /= 10;
        }

        result[result.length - 1] = (double) a - b;

        return new MathsExercise(result, new double[result.length], "Calculate " + a + " - " + b + " using long subtraction", calculationValues,
                "When subtracting large numbers, start from the right and work your way left. If the digit on top is smaller than the one below, borrow from the next digit to the left. Subtract each digit and remember to carry over if needed.",
                ExerciseSubType.LONGSUBTRACTION);
    }

    private static Exercise generateLongMultiplication(Grade grade, int technicalScore) {
        LOG.info("Generating long multiplication exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        double[] calculationValues = {a, b};

        List<Integer> tempList = getIntegerList(a, b);

        double[] result = tempList.stream()
                .mapToDouble(Integer::doubleValue)
                .toArray();

        return new MathsExercise(result, new double[result.length], "Calculate " + a + " * " + b + " using long multiplication", calculationValues,
                "Multiply each digit of one number by each digit of the other number, starting from the right and working your way left. Then, add up all the partial products to get the final result.",
                ExerciseSubType.LONGMULTIPLICATION);
    }

    private static @NotNull List<Integer> getIntegerList(int a, int b) {
        int tempA = a;
        int tempB = b;
        List<Integer> tempList = new ArrayList<>();

        if (a != 0 && b != 0) {
            int carryOver = 0;
            do {
                do {
                    tempList.add(((tempA % 10) * (tempB % 10) + carryOver) % 10);
                    carryOver = (((tempA % 10) * (tempB % 10)) + carryOver) / 10;
                    if (carryOver > 0 || tempB / 10 != 0) tempList.add(carryOver);
                    tempB /= 10;
                } while (tempB != 0);
                if (carryOver != 0) tempList.add(carryOver);
                carryOver = 0;
                tempB = b;
                tempA /= 10;
            } while (tempA != 0);
        }

        tempList.add(a * b);
        return tempList;
    }

    private static Exercise generateOrderOfOperations(Grade grade, int technicalScore) {
        LOG.info("Generating order of operations exercise");
        int max = (int) Math.round(grade.getMax() * getDifficultyFactor(technicalScore));
        int a = random.nextInt(max + 1) + 1;
        int b = getRandomFactor(a);
        int c = getRandomFactor(b);
        int operator1 = random.nextInt(2);
        int operator2 = random.nextInt(2) + 2;
        char operatorSymbol1 = getRandomOperator(operator1);
        char operatorSymbol2 = getRandomOperator(operator2);
        double[] result = new double[1];

        if (operatorSymbol1 == '+') {
            if (operatorSymbol2 == '*') {
                result[0] = (double) a + b * c;
            } else {
                result[0] = a + (double) b / c;
            }
        } else if (operatorSymbol1 == '-') {
            if (operatorSymbol2 == '*') {
                while (a < b * c) {
                    if (b > c) b--;
                    else c--;
                }
                result[0] = (double) a - b * c;
            } else {
                result[0] = a - (double) b / c;
            }
        }

        double[] calculationValues = {a, b, c, operator1, operator2};

        return new MathsExercise(result, new double[result.length], "Solve the following exercise following the order of operations: " + a + " " + operatorSymbol1 + " " + b + " " + operatorSymbol2 + " " + c, calculationValues,
                "Perform multiplication and division before addition and subtraction, and from left to right.",
                ExerciseSubType.ORDEROFOPERATIONS);
    }

    private static int getRandomFactor(int num) {
        if (num == 1) return 1;
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

    private static int countDigits(int num) {
        if (num == 0) return 1;
        int count = 0;
        while (num != 0) {
            num /= 10;
            count++;
        }
        return count;
    }

    private static char getRandomOperator(int num) {
        char operatorSymbol;
        switch (num) {
            case 1 -> operatorSymbol = '-';
            case 2 -> operatorSymbol = '*';
            case 3 -> operatorSymbol = '/';
            default -> operatorSymbol = '+';
        }
        return operatorSymbol;
    }
}
