package ch.zhaw.mathify.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Scoreboard class.
 */
class ScoreboardTest {
    private final Scoreboard scoreboard = new Scoreboard();

    @Test
    void testInOrderTraversal() {
        Map<Grade, List<Scoreboard.ScoreboardNode>> sortedNodes = scoreboard.inOrderTraversal(scoreboard.getRoot());
        for (Map.Entry<Grade, List<Scoreboard.ScoreboardNode>> entry : sortedNodes.entrySet()) {
            List<Scoreboard.ScoreboardNode> expectedOrder = new ArrayList<>(entry.getValue());
            expectedOrder.sort(Comparator
                    .comparingInt(Scoreboard.ScoreboardNode::getLevel)
                    .thenComparingInt(Scoreboard.ScoreboardNode::getExperience)
            );
            for (int i = 0; i < entry.getValue().size(); i++) {
                assertEquals(entry.getValue().get(i).getUsername(), expectedOrder.get(i).getUsername());
            }
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
        scoreboard.createRanking();
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
        scoreboard.createRanking();
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
    void testDynamicInsertionAndRemoval() {
        int currentSize = scoreboard.size();

        Random random = new Random();
        int iterationsInsert = random.nextInt(10000) + 1000;

        List<Scoreboard.ScoreboardNode> insertedNodes = new ArrayList<>();
        int actuallyInserted = 0;

        for (int i = 0; i < iterationsInsert; i++) {
            int level = random.nextInt(100) + 1;
            int experience = random.nextInt(100);
            Scoreboard.ScoreboardNode node = new Scoreboard.ScoreboardNode("test" + i, Grade.FIRST, level, experience);
            scoreboard.insert(node);
            if (scoreboard.search(scoreboard.getRoot(), node).getUsername().equals(node.getUsername())) {
                actuallyInserted++;
                insertedNodes.add(node);
            }
        }

        assertEquals(currentSize + insertedNodes.size(), scoreboard.size());

        int iterationsRemove = random.nextInt(900) + 100;
        int actuallyRemoved = 0;
        for (int i = 0; i < iterationsRemove; i++) {
            Scoreboard.ScoreboardNode node = insertedNodes.get(i);
            scoreboard.remove(node);
            if (scoreboard.search(scoreboard.getRoot(), node) == null) {
                actuallyRemoved++;
            }
        }

        assertEquals(currentSize + iterationsInsert - iterationsRemove, scoreboard.size());
        assertEquals(iterationsInsert - iterationsRemove, actuallyInserted - actuallyRemoved);
    }
}