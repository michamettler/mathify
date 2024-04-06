package ch.zhaw.mathify.model.exercise;

/**
 * Enum for exercise sub types.
 */
public enum ExerciseSubType {
    ADDITION,
    DIVISION,
    MULTIPLICATION,
    SUBTRACTION,
    SORTING,
    NEIGHBORS;

    public static ExerciseSubType valueOfIgnoreCase(String value){
        return ExerciseSubType.valueOf(value.toUpperCase());
    }


}
