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
    TENSCOMPARISON,
    DOUBLING,
    HALVING,
    THREESTEPADDITION,
    THREESTEPSUBTRACTION,
    MULTIPLICATIONTABLE,
    ROUNDINGTEN,
    LONGADDITION,
    LONGSUBTRACTION,
    LONGMULTIPLICATION,
    ORDEROFOPERATIONS;


    /**
     * @param value the value to get the enum for
     * @return the enum for the given value
     */
    public static ExerciseSubType valueOfIgnoreCase(String value) throws IllegalArgumentException {
        return ExerciseSubType.valueOf(value.toUpperCase());
    }
}
