package ch.zhaw.mathify.model;

/**
 * Represents an arithmetic exercise
 * @param result The result of the exercise
 * @param exercise The exercise as a string
 */
public record ArithmeticExercise(double result, String exercise) implements Exercise {
}
