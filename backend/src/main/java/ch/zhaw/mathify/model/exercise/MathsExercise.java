package ch.zhaw.mathify.model.exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Represents a maths exercise
 *
 * @param result          the result of the exercise
 *                        (e.g. the sorted array)
 * @param userResult      the result of the user
 * @param exercise        the exercise as a string
 * @param exerciseSubType the subtype of the exercise
 */
public record MathsExercise(double[] result, double[] userResult, String exercise,
                            double[] calculationValues, String hint,
                            ExerciseSubType exerciseSubType) implements Exercise {
    private static final Logger LOG = LoggerFactory.getLogger(MathsExercise.class);

    /**
     * Verifies the result of the user
     *
     * @return true if the user result is correct
     */
    public boolean verifyResult() {
        LOG.info("Verifying result...");
        return Arrays.equals(result, userResult);
    }

    /**
     * Converts the exercise to a dto
     *
     * @return the exercise as a dto
     */
    @Override
    public ExerciseDto toDto() {
        LOG.info("Converting exercise to dto...");
        return new ExerciseDto(Arrays.toString(result), Arrays.toString(userResult), exercise, Arrays.toString(calculationValues), hint, exerciseSubType.toString(), exerciseSubType.getDisplayName());
    }

    /**
     * @return the exercise as a string
     */
    @Override
    public String toString() {
        return exercise;
    }

    /**
     * @param obj the reference object with which to compare. If this is null, return false
     * @return true if the result and the exercise are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MathsExercise other)) {
            return false;
        }
        return Arrays.equals(result, other.result) && exercise.equals(other.exercise);
    }

    /**
     * @return the hash code of the result and the exercise
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(result) + exercise.hashCode();
    }
}
