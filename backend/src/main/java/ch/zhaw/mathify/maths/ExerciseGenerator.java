package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates exercises based on the given grade and exercise subtype.
 * It serves as a factory for the different exercise generators.
 */
public class ExerciseGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(ExerciseGenerator.class);

    private ExerciseGenerator() {
    }

    /**
     * @param grade           The grade of the exercise
     * @param exerciseSubType The subtype of the exercise
     * @param technicalScore  The technical score of the exercise
     * @return A randomly generated exercise
     * @throws IllegalArgumentException If the grade or exerciseSubType is null or the technical score is not between 1 and 10
     */
    public static Exercise generate(Grade grade, ExerciseSubType exerciseSubType, int technicalScore) {
        LOG.info("Generating exercise for grade {}, exercise subtype {} and technical score {}", grade, exerciseSubType, technicalScore);
        if (grade == null || exerciseSubType == null || technicalScore < 1 || technicalScore > 10) {
            LOG.error("Grade and exerciseSubType must not be null and technical score must be between 1 and 10");
            throw new IllegalArgumentException("Grade and exerciseSubType must not be null and technical score must be between 1 and 10");
        }
        return MathsGenerator.generate(grade, exerciseSubType, technicalScore);
    }
}
