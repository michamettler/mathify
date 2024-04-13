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
        switch (exerciseSubType.getExerciseSubTypeCategory()) {
            case ExerciseSubType.ExerciseSubTypeCategory.ARITHMETIC -> {
                return ArithmeticGenerator.generate(grade, exerciseSubType);
            }
            case ExerciseSubType.ExerciseSubTypeCategory.MATHBASICS -> {
                return MathBasicsGenerator.generate(grade, exerciseSubType);
            }
            default -> throw new IllegalArgumentException("Unknown exercise subtype");
        }
    }
}
