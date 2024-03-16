package ch.zhaw.mathify.math.expression;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpressionTest {
    @Test
    void evaluateAddition() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(1.0, 2.0)), new ArrayList<>(Arrays.asList('+')));
        assertEquals(3.0, expression.evaluate());
    }

    @Test
    void evaluateMultipleOperators() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0)), new ArrayList<>(Arrays.asList('+', '*')));
        assertEquals(7.0, expression.evaluate());
    }

    @Test
    void evaluateMultipleOperators2() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(5.0, 2.0, 3.0)), new ArrayList<>(Arrays.asList('+', '*')));
        assertEquals(11.0, expression.evaluate());
    }

    @Test
    void evaluateSubtraction() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(5.0, 2.0)), new ArrayList<>(Arrays.asList('-')));
        assertEquals(3.0, expression.evaluate());
    }

    @Test
    void evaluateDivision() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(6.0, 3.0)), new ArrayList<>(Arrays.asList('/')));
        assertEquals(2.0, expression.evaluate());
    }

    @Test
    void evaluateDivisionByZero() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(6.0, 0.0)), new ArrayList<>(Arrays.asList('/')));
        assertThrows(ArithmeticException.class, expression::evaluate);
    }

    @Test
    void evaluateMixedOperations() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(5.0, 2.0, 3.0, 4.0)), new ArrayList<>(Arrays.asList('+', '-', '*')));
        assertEquals(-5.0, expression.evaluate());
    }

    @Test
    void testToString() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(5.0, 2.0, 3.0, 4.0)), new ArrayList<>(Arrays.asList('+', '-', '*')));
        assertEquals("5.0 + 2.0 - 3.0 * 4.0", expression.toString());
    }

    @Test
    void testToString2() {
        Expression expression = new Expression(new ArrayList<>(Arrays.asList(5.0, 2.0, 3.0, 4.0)), new ArrayList<>(Arrays.asList('+', '-', '*')));
        expression.appendExpression('+', 5.0, 2.0, '-');
        assertEquals("5.0 + 2.0 - 3.0 * 4.0 + 5.0 - 2.0", expression.toString());
    }
}
