package ch.zhaw.mathify.model.exercise;

import java.util.Arrays;

/**
 * Enum for exercise sub types.
 */
public enum ExerciseSubType {
    ADDITION("Addition"),
    DIVISION("Division"),
    MULTIPLICATION("Multiplication"),
    SUBTRACTION("Subtraction"),
    SORTING("Sorting"),
    NEIGHBORS("Neighbors"),
    COMPARISON("Comparison"),
    NUMBERCOMPLETION("Completion"),
    TENSCOMPARISON("Tens Comparison"),
    DOUBLING("Doubling"),
    HALVING("Halving"),
    THREESTEPADDITION("Adding in three steps"),
    THREESTEPSUBTRACTION("Subtracting in three steps"),
    MULTIPLICATIONTABLE("Multiplying in three steps"),
    ROUNDINGTEN("Rounding to tens"),
    LONGADDITION("Long addition"),
    LONGSUBTRACTION("Long subraction"),
    LONGMULTIPLICATION("Long Multiplication"),
    ORDEROFOPERATIONS("Order of operations");
    private final String displayName;

    ExerciseSubType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param value the value to get the enum for
     * @return the enum for the given value
     */
    public static ExerciseSubType valueOfIgnoreCase(String value) throws IllegalArgumentException {
        return ExerciseSubType.valueOf(value.toUpperCase());
    }

    /**
     * @param displayName Name to find in display names
     * @return the found display name
     * @throws IllegalArgumentException if not found
     */
    public static ExerciseSubType getSubTypeFromDisplayName(String displayName) throws IllegalArgumentException {
        return Arrays.stream(ExerciseSubType.values())
                .filter(subType -> subType.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid exercise subtype"));
    }
}