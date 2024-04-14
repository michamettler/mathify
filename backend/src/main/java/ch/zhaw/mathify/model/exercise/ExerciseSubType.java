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
    NEIGHBORS,
    COMPARISON,
    NUMBERCOMPLETION,
    TENSCOMPARISON;


    /**
     * @param value the value to get the enum for
     * @return the enum for the given value
     */
    public static ExerciseSubType valueOfIgnoreCase(String value){
        return ExerciseSubType.valueOf(value.toUpperCase());
    }
}
