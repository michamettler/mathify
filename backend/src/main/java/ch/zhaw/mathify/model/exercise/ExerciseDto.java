package ch.zhaw.mathify.model.exercise;

/**
 * Represents an exercise dto
 * @param exercise the exercise as a string
 * @param result the result of the exercise
 * @param exerciseSubType the exercise subtype
 */
public record ExerciseDto(String exercise, String result, String exerciseSubType) {
}
