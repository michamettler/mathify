package ch.zhaw.mathify.math.expression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mathematical expression.
 */
public class Expression {
    private static final Logger LOG = LoggerFactory.getLogger(Expression.class);
    private final List<Double> operands;
    private final List<Character> operators;

    /**
     * @param operands  list of operands
     * @param operators list of operators
     */
    public Expression(List<Double> operands, List<Character> operators) {
        validateExpression(operands, operators);
        this.operands = new ArrayList<>(operands);
        this.operators = new ArrayList<>(operators);
    }

    /**
     * @return the result of the expression
     */
    public double evaluate() {
        validateExpression(operands, operators);

        performMultiplicationAndDivision();

        double result = operands.get(0);
        for (int i = 0; i < operators.size(); i++) {
            char operator = operators.get(i);
            double operand = operands.get(i + 1);
            switch (operator) {
                case '+' -> result += operand;
                case '-' -> result -= operand;
                default -> {
                    LOG.error("Unknown operator: {}", operator);
                    throw new IllegalArgumentException("Unknown operator: " + operator);
                }
            }
        }
        return result;
    }

    private void performMultiplicationAndDivision() {
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '*' || operators.get(i) == '/') {
                double left = operands.get(i);
                double right = operands.get(i + 1);
                char operator = operators.get(i);
                if (right == 0 && operator == '/') {
                    LOG.error("Division by zero");
                    throw new ArithmeticException("Division by zero");
                }
                double temp = switch (operator) {
                    case '*' -> left * right;
                    case '/' -> left / right;
                    default -> throw new IllegalArgumentException("Unknown operator: " + operator);
                };
                operands.set(i, temp);
                operands.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
    }

    /**
     * @param firstOperator  the first operator
     * @param left           the left operand
     * @param right          the right operand
     * @param secondOperator the second operator
     */
    public void appendExpression(char firstOperator, double left, double right, char secondOperator) {
        operators.add(firstOperator);
        operands.add(left);
        operands.add(right);
        operators.add(secondOperator);
    }

    /**
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < operands.size(); i++) {
            sb.append(operands.get(i));
            if (i < operators.size()) {
                sb.append(" ").append(operators.get(i)).append(" ");
            }
        }
        return sb.toString();
    }

    private void validateExpression(List<Double> operands, List<Character> operators) {
        if (operands.size() != operators.size() + 1) {
            LOG.error("Invalid expression: operators.size() != operands.size() + 1");
            throw new IllegalArgumentException("Invalid expression: operators.size() != operands.size() + 1");
        }
    }
}