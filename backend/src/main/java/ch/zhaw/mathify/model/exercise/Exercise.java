package ch.zhaw.mathify.model.exercise;

/**
 * Interface for exercises.
 */
public interface Exercise {
    ExerciseDto toDto();
    boolean verifyResult();
}
