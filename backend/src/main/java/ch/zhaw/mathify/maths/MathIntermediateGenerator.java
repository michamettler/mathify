package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import ch.zhaw.mathify.model.exercise.MathIntermediateExercise;

import java.util.Random;

/**
 * Generates exercises for the math basics category
 * (number completion, ...)
 */
public class MathIntermediateGenerator {
    private static final Random random = new Random();

    private MathIntermediateGenerator() {
    }

    /**
     * @param grade   The grade of the exercise
     * @param subType The sub type of the exercise
     * @return A new exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType subType) {
        return switch (subType) {
            case NUMBERCOMPLETION -> generateNumberCompletion(grade);
            default -> throw new IllegalArgumentException("Sub type " + subType + " is not supported!");
        };
    }

    private static Exercise generateNumberCompletion(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b;
        do {
            b = random.nextInt(max + 1);
        } while (b <= a);
        int[] result = {b - a};
        return new MathIntermediateExercise(result, "What number do you add to " + a + " to make " + b + "?");
    }
}
