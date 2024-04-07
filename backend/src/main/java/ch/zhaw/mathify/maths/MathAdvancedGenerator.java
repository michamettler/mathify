package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.exercise.MathAdvancedExercise;

import java.util.Random;

/**
 * Generates exercises for the math advanced category
 * (tens comparison, ...)
 */
public class MathAdvancedGenerator {

    private static final Random random = new Random();

    private MathAdvancedGenerator() {
    }

    /**
     * @param grade   The grade of the exercise
     * @param subType The sub type of the exercise
     * @return A new exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType subType) {
        return switch (subType) {
            case TENSCOMPARISON -> generateTensComparison(grade);
            default -> throw new IllegalArgumentException("Sub type " + subType + " is not supported!");
        };
    }

    private static Exercise generateTensComparison(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(9) * 10 + 10;
        int b;
        do {
            b = random.nextInt(9) * 10 + 10;
        } while (b == a);
        int[] result = {Math.max(a, b)};
        return new MathAdvancedExercise(result, "Which tens number is bigger: " + a + " or " + b + "?");
    }
}
