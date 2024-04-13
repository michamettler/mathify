package ch.zhaw.mathify.model.exercise;

import static ch.zhaw.mathify.model.exercise.ExerciseSubType.ExerciseSubTypeCategory.*;

/**
 * Enum for exercise sub types.
 */
public enum ExerciseSubType {
    ADDITION(ARITHMETIC),
    DIVISION(ARITHMETIC),
    MULTIPLICATION(ARITHMETIC),
    SUBTRACTION(ARITHMETIC),
    SORTING(MATHBASICS),
    NEIGHBORS(MATHBASICS),
    COMPARISON(MATHBASICS),
    NUMBERCOMPLETION(MATHBASICS),
    TENSCOMPARISON(MATHBASICS);

    ExerciseSubType(ExerciseSubTypeCategory exerciseSubTypeCategory) {
        this.exerciseSubTypeCategory = exerciseSubTypeCategory;
    }

    /**
     * @param value the value to get the enum for
     * @return the enum for the given value
     */
    public static ExerciseSubType valueOfIgnoreCase(String value){
        return ExerciseSubType.valueOf(value.toUpperCase());
    }

    public final ExerciseSubTypeCategory exerciseSubTypeCategory;
    public ExerciseSubTypeCategory getExerciseSubTypeCategory() {
        return exerciseSubTypeCategory;
    }

    public enum ExerciseSubTypeCategory {
        ARITHMETIC,
        MATHBASICS
    }
}
