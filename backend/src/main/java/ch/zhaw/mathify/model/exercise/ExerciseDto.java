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
        return switch (ExerciseSubType.valueOf(this.exerciseSubType()).getExerciseSubTypeCategory()) {
            case ExerciseSubType.ExerciseSubTypeCategory.ARITHMETIC ->
                    new ArithmeticExercise(
                            Double.parseDouble(this.result()),
                            this.exercise(),
                            ExerciseSubType.valueOf(this.exerciseSubType())
                    );
            case ExerciseSubType.ExerciseSubTypeCategory.MATHBASICS ->
                    new MathBasicsExercise(
                            Arrays.stream(this.result().split(",")).mapToInt(Integer::parseInt).toArray(),
                            this.exercise(),
                            ExerciseSubType.valueOf(this.exerciseSubType())
                    );
        };
    }
}
