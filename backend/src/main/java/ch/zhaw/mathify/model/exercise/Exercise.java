package ch.zhaw.mathify.model.exercise;

/**
 * Interface for exercises.
 */
public interface Exercise {
    /** Generates the exercise
     * @return the exercise as a DTO
     */
    ExerciseDto toDto();

    /** Verifies the result of the exercise
     * @return the exercise as a string
     */
    boolean verifyResult();

    /** Returns the exercise type
     * @return the exercise type
     */
    ExerciseSubType exerciseSubType();
}
