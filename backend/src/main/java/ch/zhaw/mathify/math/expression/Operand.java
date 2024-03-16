package ch.zhaw.mathify.math.expression;

public class Operand implements Expression{
    private double value;

    public Operand(double value) {
        this.value = value;
    }

    @Override
    public double calculate() {
        return value;
    }
}
