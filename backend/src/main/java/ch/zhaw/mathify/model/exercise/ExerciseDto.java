package ch.zhaw.mathify.model.exercise;

import java.util.Arrays;

/**
 * Represents an exercise dto
 * @param exercise the exercise as a string
 * @param result the result of the exercise
 * @param exerciseSubType the exercise subtype
 */
public record ExerciseDto(String exercise, String result, String exerciseSubType) {
    /**
     * Converts a dto to an exercise
     * @return the exercise
     */
    Exercise fromDto() {
        return new MathsExercise(
                Arrays.stream(this.result().split(",")).mapToDouble(Double::parseDouble).toArray(),
                this.exercise(),
                ExerciseSubType.valueOf(this.exerciseSubType())
        );
    }
}
