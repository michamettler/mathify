package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.*;
import ch.zhaw.mathify.model.exercise.*;

import java.util.Arrays;
import java.util.Random;

/**
 * Generates exercises for the math basics category
 * (sorting, neighbors, ...)
 */
public class MathBasicsGenerator {

    private static final Random random = new Random();

    private MathBasicsGenerator() {
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
            case TENSCOMPARISON -> generateTensComparison(grade);
            default -> throw new IllegalArgumentException("Sub type " + subType + " is not supported!");
        };
    }

    private static Exercise generateSorting(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        int c = random.nextInt(max + 1);
        int[] res = {a, b, c};
        String arrayUnsorted = Arrays.toString(res);
        Arrays.sort(res);
        return new MathBasicsExercise(res, arrayUnsorted);
    }

    private static Exercise generateNeighbors(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        return new MathBasicsExercise(new int[]{a - 1, a, a + 1}, "Find the neighbors of " + a + "!");
    }

    private static Exercise generateComparison(Grade grade) {
        int max = grade.getMax();
        int a, b, c;
        do {
            a = random.nextInt(max + 1);
            b = random.nextInt(max + 1);
            c = random.nextInt(max + 1);
        } while (a == b || a == c || b == c);
        int[] result = {Math.max(a, Math.max(b, c))};
        return new MathBasicsExercise(result, "Find the biggest number in the list: " + a + ", " + b + ", " + c);
    }

    private static Exercise generateNumberCompletion(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b;
        do {
            b = random.nextInt(max + 1);
        } while (b <= a);
        int[] result = {b - a};
        return new MathBasicsExercise(result, "What number do you add to " + a + " to make " + b + "?");
    }

    private static Exercise generateTensComparison(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(9) * 10 + 10;
        int b;
        do {
            b = random.nextInt(9) * 10 + 10;
        } while (b == a);
        int[] result = {Math.max(a, b)};
        return new MathBasicsExercise(result, "Which tens number is bigger: " + a + " or " + b + "?");
    }
}
