package ch.zhaw.mathify.model.exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Represents an exercise dto
 *
 * @param exercise        the exercise as a string
 * @param result          the result of the exercise
 * @param exerciseSubType the exercise subtype
 */
public record ExerciseDto(String result, String userResult, String exercise, String calculationValues,
                          String exerciseSubType) {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseDto.class);

    /**
     * Converts a dto to an exercise
     *
     * @return the exercise
     * @throws IllegalArgumentException if the exercise subtype is invalid
     */
    public Exercise fromDto() throws IllegalArgumentException {
        LOG.info("Converting dto to exercise");
        try {
            return new MathsExercise(
                    Arrays.stream(this.result().substring(1, this.result().length() - 1).split(",")).mapToDouble(Double::parseDouble).toArray(),
                    Arrays.stream(this.userResult.substring(1, this.userResult.length() - 1).split(",")).mapToDouble(Double::parseDouble).toArray(),
                    this.exercise(),
                    Arrays.stream(this.calculationValues().substring(1, this.calculationValues().length() - 1).split(",")).mapToDouble(Double::parseDouble).toArray(),
                    ExerciseSubType.valueOfIgnoreCase(this.exerciseSubType())
            );
        } catch (IllegalArgumentException e) {
            LOG.error("Could not parse exercise from dto - {}", e.getMessage());
            throw new IllegalArgumentException("Could not parse exercise from dto");
        }
    }
}
