package ch.zhaw.mathify.math.expression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an operation with two operands and an operator.
 */
public class Operation implements Expression {
    private static final Logger LOG = LoggerFactory.getLogger(Operation.class);
    private Expression left;
    private Expression right;
    private char operator;

    /**
     * @param left left part of the operation
     * @param right right part of the operation
     * @param operator operator of the operation
     */
    public Operation(Expression left, Expression right, char operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    /**
     * @return the result of the operation
     */
    @Override
    public double calculate() {
        LOG.info("Calculating operation {} {} {}", left.calculate(), operator, right.calculate());
        return switch (operator) {
            case '+' -> left.calculate() + right.calculate();
            case '-' -> left.calculate() - right.calculate();
            case '*' -> left.calculate() * right.calculate();
            case '/' -> left.calculate() / right.calculate();
            default -> throw new UnsupportedOperationException("Operator not supported: " + operator);
        };
    }
}
