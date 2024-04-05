package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.*;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.exercise.MathBasicsExercise;

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
     * @param grade The grade of the exercise
     * @param subType The sub type of the exercise
     * @return A new exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType subType) {
        return switch (subType) {
            case SORTING -> generateSorting(grade);
            case NEIGHBORS -> generateNeighbors(grade);
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

    private static Exercise generateNeighbors(Grade grade){
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        return new MathBasicsExercise(new int[]{a - 1, a, a + 1}, "Find the neighbors of " + a + "!");
    }
}
