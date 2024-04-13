package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;

/**
 * This class generates exercises based on the given grade and exercise subtype.
 * It serves as a factory for the different exercise generators.
 */
public class ExerciseGenerator {
    private ExerciseGenerator() {
    }

    /**
     * @param grade           The grade of the exercise
     * @param exerciseSubType The subtype of the exercise
     * @return A randomly generated exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType exerciseSubType) {
        if (grade == null || exerciseSubType == null) {
            throw new IllegalArgumentException("Grade and exerciseSubType must not be null");
        }
        return MathsGenerator.generate(grade, exerciseSubType);
    }
}
