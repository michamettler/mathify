package ch.zhaw.mathify.model.exercise;

import org.slf4j.Logger;

import java.util.Arrays;

/**
 * Represents an exercise dto
 * @param exercise the exercise as a string
 * @param result the result of the exercise
 * @param exerciseSubType the exercise subtype
 */
public record ExerciseDto(String result, String userResult, String exercise, String exerciseSubType) {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ExerciseDto.class);
    /**
     * Converts a dto to an exercise
     * @return the exercise
     * @throws IllegalArgumentException if the exercise subtype is invalid
     */
    public Exercise fromDto() throws IllegalArgumentException {
        LOG.info("Converting dto to exercise");
        try{
            return new MathsExercise(
                    Arrays.stream(this.result().split(",")).mapToDouble(Double::parseDouble).toArray(),
                    Arrays.stream(this.userResult.split(",")).mapToDouble(Double::parseDouble).toArray(),
                    this.exercise(),
                    ExerciseSubType.valueOfIgnoreCase(this.exerciseSubType())
            );
        } catch (IllegalArgumentException e) {
            LOG.error("Invalid exercise subtype - {}", e.getMessage());
            throw new IllegalArgumentException("Invalid exercise subtype");
        }
    }
}
