package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.Exercise;
import ch.zhaw.mathify.model.ExerciseSubType;
import ch.zhaw.mathify.model.Grade;

import java.util.Random;

public class ArithmeticGenerator {
    private static final Random random = new Random();
    public static Exercise generate(Grade grade, ExerciseSubType subType) {
        return switch (subType) {
            case ADDITION -> generateAddition(grade);
            case SUBTRACTION -> generateSubtraction(grade);
            case MULTIPLICATION -> generateMultiplication(grade);
            case DIVISION -> generateDivision(grade);
            case MIXED -> generateMixed(grade);
        };
    }
}
