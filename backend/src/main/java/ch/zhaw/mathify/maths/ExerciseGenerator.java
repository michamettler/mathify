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
     * @return A randomly generated exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType exerciseSubType) {
        LOG.info("Generating exercise for grade {} and exercise subtype {}", grade, exerciseSubType);
        if (grade == null || exerciseSubType == null) {
            LOG.error("Grade and exerciseSubType must not be null");
            throw new IllegalArgumentException("Grade and exerciseSubType must not be null");
        }
        return MathsGenerator.generate(grade, exerciseSubType);
    }
}
