package ch.zhaw.mathify.math;

import ch.zhaw.mathify.math.util.Calculator;

import java.util.List;

public class ArithmeticExercise implements Exercise{
    private static final ExerciseType EXERCISE_TYPE = ExerciseType.ARITHMETIC;
    private final Grade grade;
    private List<Double> operands;
    private List<Character> operators;

    public ArithmeticExercise(Grade grade) {
        this.grade = grade;
        createExercise();
    }

    public Grade getGrade() {
        return grade;
    }

    private void createExercise() {
        switch (grade){
            case ONE -> {
                operands = List.of(1.0, 1.0);
                operators = List.of('+');
            }
        }
    }

    public double getResult(){
        return Calculator.calculateArithmeticResult(operands, operators);
    }
}
