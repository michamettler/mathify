package ch.zhaw.mathify.model.exercise;

/**
 * Represents an arithmetic exercise
 * @param result The result of the exercise
 * @param exercise The exercise as a string
 */
public record ArithmeticExercise(double result, String exercise, ExerciseSubType exerciseSubType) implements Exercise {
    /**
     * Converts the exercise to a string
     * @return The exercise as a string
     */
    public ExerciseDto toDto(){
        return new ExerciseDto(exercise, String.valueOf(result), exerciseSubType.toString());
    }
}
