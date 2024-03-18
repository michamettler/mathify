package ch.zhaw.mathify.maths;

import ch.zhaw.mathify.model.ArithmeticExercise;
import ch.zhaw.mathify.model.Exercise;
import ch.zhaw.mathify.model.ExerciseSubType;
import ch.zhaw.mathify.model.Grade;

import java.util.Random;

/**
 * This class generates arithmetic exercises based on the given grade and sub type.
 */
public class ArithmeticGenerator {
    private static final Random random = new Random();

    private ArithmeticGenerator() {
    }

    /**
     * Generates a new exercise based on the given grade and sub type.
     * @param grade  The grade of the exercise
     * @param subType The sub type of the exercise
     * @return A new exercise
     */
    public static Exercise generate(Grade grade, ExerciseSubType subType) {
        return switch (subType) {
            case ADDITION -> generateAddition(grade);
            case SUBTRACTION -> generateSubtraction(grade);
            case MULTIPLICATION -> generateMultiplication(grade);
            case DIVISION -> generateDivision(grade);
        };
    }

    private static Exercise generateDivision(Grade grade) {
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Division is not supported for grade one!");
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = getRandomFactor(a);
        return new ArithmeticExercise((double) a / b, a + " / " + b);
    }

    private static Exercise generateMultiplication(Grade grade) {
        if (grade == Grade.FIRST) throw new IllegalArgumentException("Multiplication is not supported for grade one!");
        int max = (int) Math.sqrt(grade.getMax());
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max + 1);
        return new ArithmeticExercise(a * b, a + " * " + b);
    }

    private static Exercise generateSubtraction(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(a + 1);
        return new ArithmeticExercise(a - b, a + " - " + b);
    }

    private static Exercise generateAddition(Grade grade) {
        int max = grade.getMax();
        int a = random.nextInt(max + 1);
        int b = random.nextInt(max - a + 1);
        return new ArithmeticExercise(a + b, a + " + " + b);
    }

    private static int getRandomFactor(int num) {
        int factor = num;
        while (factor == num) {
            factor = random.nextInt(num) + 1;
            while (num % factor != 0) {
                factor--;
            }
        }
        return factor;
    }
}
