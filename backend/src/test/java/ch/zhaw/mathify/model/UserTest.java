package ch.zhaw.mathify.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    private final User user = new User("test");

    @Test
    void testAddExp() {
        user.addExp(50);
        assertEquals(1, user.getLevel());
        user.addExp(50);
        assertEquals(2, user.getLevel());
        assertEquals(0, user.getExp());
        user.addExp(101);
        assertEquals(3, user.getLevel());
        assertEquals(1, user.getExp());
    }
}
