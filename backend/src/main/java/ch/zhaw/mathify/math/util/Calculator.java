package ch.zhaw.mathify.math.util;

import java.util.List;

public class Calculator {
    private Calculator() {}
    public static double calculateArithmeticResult(List<Double> operands, List<Character> operators) {
        if (operands.size() != operators.size() + 1) {
            throw new IllegalArgumentException("Invalid input: Operand and operator lists should have equal length.");
        }

        double result = operands.get(0);

        for (int i = 0; i < operators.size(); i++) {
            char operator = operators.get(i);
            double nextOperand = operands.get(i + 1);

            switch (operator) {
                case '+':
                    result += nextOperand;
                    break;
                case '-':
                    result -= nextOperand;
                    break;
                case '*':
                    result *= nextOperand;
                    break;
                case '/':
                    result /= nextOperand;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        }
        return result;
    }
}
