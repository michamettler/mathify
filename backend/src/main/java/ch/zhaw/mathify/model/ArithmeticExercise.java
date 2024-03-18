package ch.zhaw.mathify.model;

public class ArithmeticExercise implements  Exercise {
    private final double result;
    private final String exercise;

    public ArithmeticExercise(double result, String exercise) {
        this.result = result;
        this.exercise = exercise;
    }

    public double getResult() {
        return result;
    }

    public String getExercise() {
        return exercise;
    }
}
