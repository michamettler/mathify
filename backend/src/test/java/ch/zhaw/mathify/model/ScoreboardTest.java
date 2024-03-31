package ch.zhaw.mathify.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Scoreboard class.
 */
class ScoreboardTest {
    private List<User> jsonUsers;
    private final Scoreboard scoreboard = new Scoreboard();

    @Test
    void testInOrderTraversal() {
        List<Scoreboard.ScoreboardNode> sortedNodes = scoreboard.inOrderTraversal(scoreboard.getRoot());
        List<Scoreboard.ScoreboardNode> expectedOrder = new ArrayList<>(sortedNodes);
        expectedOrder.sort(Comparator
                .comparingInt(Scoreboard.ScoreboardNode::getLevel)
                .thenComparingInt(Scoreboard.ScoreboardNode::getExperience)
        );

        int counter = 0;
        for (Scoreboard.ScoreboardNode node : sortedNodes) {
            assertEquals(node.getUsername(), expectedOrder.get(counter).getUsername());
            counter++;
        }
    }

    @Test
    void testInsert() {
        int currentSize = scoreboard.size();
        Scoreboard.ScoreboardNode node = new Scoreboard.ScoreboardNode("test", Grade.FIRST, 1, 1);
        assertNull(scoreboard.search(scoreboard.getRoot(), node));

        scoreboard.insert(node);

        assertEquals(currentSize + 1, scoreboard.size());
        assertEquals(node.getUsername(), scoreboard.search(scoreboard.getRoot(), node).getUsername());
    }

    @Test
    void testRemove() {
        int currentSize = scoreboard.size();
        Scoreboard.ScoreboardNode node = new Scoreboard.ScoreboardNode("sarah_jackson", Grade.FOURTH, 6, 22);
        assertNotNull(scoreboard.search(scoreboard.getRoot(), node));

        Scoreboard.ScoreboardNode removedNode = scoreboard.remove(node);

        assertEquals(node, removedNode);
        assertEquals(currentSize - 1, scoreboard.size());
        assertNull(scoreboard.search(scoreboard.getRoot(), node));
    }

    @Test
    void testUpdate() {
        int currentSize = scoreboard.size();
        Scoreboard.ScoreboardNode node = new Scoreboard.ScoreboardNode("jane_smith", Grade.SECOND, 8, 44);
        assertNotNull(scoreboard.search(scoreboard.getRoot(), node));
        assertEquals(8, scoreboard.search(scoreboard.getRoot(), node).getLevel());
        assertEquals(44, scoreboard.search(scoreboard.getRoot(), node).getExperience());

        scoreboard.update(node, 10, 95);
        node.setLevel(10);
        node.setExperience(95);

        assertEquals(currentSize, scoreboard.size());
        assertNotNull(scoreboard.search(scoreboard.getRoot(), node));
        assertEquals(10, scoreboard.search(scoreboard.getRoot(), node).getLevel());
        assertEquals(95, scoreboard.search(scoreboard.getRoot(), node).getExperience());
    }

    @Test
    void testBalancing() {
        int currentSize = scoreboard.size();

        Random random = new Random();
        int iterationsInsert = random.nextInt(9000) + 1000;

        List<Scoreboard.ScoreboardNode> insertedNodes = new ArrayList<>();

        for (int i = 0; i < iterationsInsert; i++) {
            int level = random.nextInt(100);
            int experience = random.nextInt(100);
            Scoreboard.ScoreboardNode node = new Scoreboard.ScoreboardNode("test" + i, Grade.FIRST, level, experience);
            scoreboard.insert(node);
            insertedNodes.add(node);
        }

        assertEquals(currentSize + iterationsInsert, scoreboard.size());

        int iterationsRemove = random.nextInt(900) + 100;

        for (int i = 0; i < iterationsRemove; i++) {
            Scoreboard.ScoreboardNode node = insertedNodes.get(i);
            scoreboard.remove(node);
        }

        assertEquals(currentSize + iterationsInsert - iterationsRemove, scoreboard.size());
        assertTrue(scoreboard.isBalanced(scoreboard.getRoot()));
    }
}