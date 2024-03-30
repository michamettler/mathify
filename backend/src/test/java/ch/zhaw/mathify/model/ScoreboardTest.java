package ch.zhaw.mathify.model;

import ch.zhaw.mathify.UserSampleData;
import ch.zhaw.mathify.util.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    private List<User> jsonUsers;
    private UserSampleData userSampleData;
    private final Scoreboard scoreboard = new Scoreboard();

    @BeforeEach
    void setup() {
        userSampleData = new UserSampleData();
        try {
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("users.json")).toURI());
            String jsonString = Files.readString(path);
            jsonUsers = JsonMapper.map(jsonString, User.class);
            for (User user : jsonUsers) {
                scoreboard.insert(new Scoreboard.ScoreboardNode(user.getUsername(), user.getGrade(), user.getLevel(), user.getExperience()));
            }
        } catch (IOException e) {
            System.err.println("TEST-ERROR!");
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testInOrderTraversal() {
        List<Scoreboard.ScoreboardNode> sortedNodes = scoreboard.inOrderTraversal(scoreboard.getRoot());
        List<User> expectedOrder = Arrays.asList(
                userSampleData.getSampleUsers().get(3),
                userSampleData.getSampleUsers().get(1),
                userSampleData.getSampleUsers().get(0),
                userSampleData.getSampleUsers().get(2),
                userSampleData.getSampleUsers().get(4)
        );
        int counter = 0;
        for (Scoreboard.ScoreboardNode node : sortedNodes) {
            assertEquals(node.username, expectedOrder.get(counter).getUsername());
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
        assertEquals(node.username, scoreboard.search(scoreboard.getRoot(), node).username);
    }

    @Test
    void testRemove() {
        int currentSize = scoreboard.size();
        Scoreboard.ScoreboardNode node = new Scoreboard.ScoreboardNode("sarah_jackson", Grade.FOURTH, 6, 22);
        assertNotNull(scoreboard.search(scoreboard.getRoot(), node));

        scoreboard.remove(node);

        assertEquals(currentSize - 1, scoreboard.size());
        assertNull(scoreboard.search(scoreboard.getRoot(), node));
    }

    @Test
    void testUpdate() {
        int currentSize = scoreboard.size();
        Scoreboard.ScoreboardNode node = new Scoreboard.ScoreboardNode("jane_smith", Grade.SECOND, 8, 44);
        assertNotNull(scoreboard.search(scoreboard.getRoot(), node));
        assertEquals(8, scoreboard.search(scoreboard.getRoot(), node).level);
        assertEquals(44, scoreboard.search(scoreboard.getRoot(), node).experience);

        scoreboard.update(node, 10, 95);
        node.level = 10;
        node.experience = 95;

        assertEquals(currentSize, scoreboard.size());
        assertNotNull(scoreboard.search(scoreboard.getRoot(), node));
        assertEquals(10, scoreboard.search(scoreboard.getRoot(), node).level);
        assertEquals(95, scoreboard.search(scoreboard.getRoot(), node).experience);
    }
}