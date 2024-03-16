package ch.zhaw.mathify.math.expression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationTest {
    @Test
    void testBasicOperation(){
        Operand left = new Operand(5);
        Operand right = new Operand(3);
        Operation operation = new Operation(left, right, '+');
        assertEquals(8, operation.calculate());

        operation = new Operation(left, right, '-');
        assertEquals(2, operation.calculate());

        operation = new Operation(left, right, '*');
        assertEquals(15, operation.calculate());

        operation = new Operation(left, right, '/');
        assertEquals(5.0/3.0, operation.calculate());
    }

    @Test
    void testUnsupportedOperation(){
        Operand left = new Operand(5);
        Operand right = new Operand(3);
        Operation operation = new Operation(left, right, '%');
        try {
            operation.calculate();
        } catch (UnsupportedOperationException e) {
            assertEquals("Operator not supported: %", e.getMessage());
        }
    }

    @Test
    void testNestedOperation(){
        Operand left = new Operand(5);
        Operand right = new Operand(3);
        Operation operation = new Operation(left, right, '+');
        Operation nestedOperation = new Operation(operation, right, '*');
        assertEquals(8*3, nestedOperation.calculate());
    }

    @Test
    void testNestedOperation2(){
        Operand left = new Operand(5);
        Operand right = new Operand(3);
        Operation operation = new Operation(left, right, '+');
        Operation nestedOperation = new Operation(operation, right, '/');
        assertEquals(8.0/3.0, nestedOperation.calculate());
    }

    @Test
    void testNestedOperation3(){
        Operand left = new Operand(5);
        Operand right = new Operand(3);
        Operation operation = new Operation(left, right, '+');
        Operation nestedOperation = new Operation(operation, right, '-');
        assertEquals(8-3, nestedOperation.calculate());
    }
}
