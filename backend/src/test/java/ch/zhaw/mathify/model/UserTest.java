package ch.zhaw.mathify.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final User user = new User("test", "testmail@mail.com", "password", Grade.SECOND);

    @Test
    void testAddExp() {
        user.addExp(50);
        assertEquals(1, user.getLevel());
        user.addExp(50);
        assertEquals(2, user.getLevel());
        assertEquals(0, user.getExperience());
        user.addExp(101);
        assertEquals(3, user.getLevel());
        assertEquals(1, user.getExperience());
    }

    @Test
    void testPasswordHashing() {
        assertNotEquals("password", user.getPassword());
    }

    @Test
    void testVerifyPassword() {
        assertTrue(User.verifyPassword("password", user.getPassword()));
    }

    @Test
    void testVerifyPasswordWithWrongPassword() {
        assertFalse(User.verifyPassword("wrongpassword", user.getPassword()));
    }

    @Test
    void testTechnicalScoreInitialization() {
        for (int score : user.getTechnicalScore().values()) {
            assertEquals(1, score);
        }
    }
}
