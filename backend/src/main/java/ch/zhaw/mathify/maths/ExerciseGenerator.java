package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.Grade;
import ch.zhaw.mathify.model.exercise.Exercise;
import ch.zhaw.mathify.model.exercise.ExerciseSubType;

public abstract class ExerciseGenerator {
    private ExerciseGenerator() {
    }

    public static Exercise generate(Grade grade, ExerciseSubType exerciseSubType) {
        switch (exerciseSubType) {
            case ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION -> {
                return ArithmeticGenerator.generate(grade, exerciseSubType);
            }
            case NEIGHBORS, SORTING -> {
                return MathBasicsGenerator.generate(grade, exerciseSubType);
            }
            default -> throw new IllegalArgumentException("Unknown exercise subtype");
        }
    }
}
