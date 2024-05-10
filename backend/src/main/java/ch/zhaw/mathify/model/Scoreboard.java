package ch.zhaw.mathify.model;

import ch.zhaw.mathify.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * This class represents an AVL-tree that stores the user's data in the scoreboard.
 */
public class Scoreboard {
    private static final Logger LOG = LoggerFactory.getLogger(Scoreboard.class);
    private ScoreboardNode root;

    public ScoreboardNode getRoot() {
        return root;
    }

    /**
     * Inserts a new node into the Scoreboard
     *
     * @param node The node to be inserted
     */
    public void insert(ScoreboardNode node) {
        LOG.debug("Inserting {} into the scoreboard", node.username);
        root = insertAt(root, node);
    }

    /**
     * Inserts a new node into the Scoreboard at the specified position
     *
     * @param currentNode The current node
     * @param newNode     The new node to be inserted
     * @return The new node
     */
    private ScoreboardNode insertAt(ScoreboardNode currentNode, ScoreboardNode newNode) {
        LOG.debug("Inserting (at) {} into the scoreboard", newNode.username);
        if (currentNode == null) {
            return new ScoreboardNode(newNode.username, newNode.grade, newNode.level, newNode.experience);
        } else if (newNode.username.equals(currentNode.username)) {
            update(currentNode, newNode.level, newNode.experience);
        } else if (newNode.compareTo(currentNode) <= 0) {
            currentNode.leftScoreboardNode = insertAt(currentNode.leftScoreboardNode, newNode);
        } else {
            currentNode.rightScoreboardNode = insertAt(currentNode.rightScoreboardNode, newNode);
        }
        return currentNode;
    }

    /**
     * Removes a node from the Scoreboard
     *
     * @param node The node to be removed
     * @return The removed node
     */
    public ScoreboardNode remove(ScoreboardNode node) {
        LOG.debug("Removing {} from the scoreboard", node.username);
        if (root != null) {
            root = removeAt(root, node);
        }
        return node;
    }

    /**
     * Removes a node from the Scoreboard at the specified position
     *
     * @param currentNode     The current node
     * @param nodeToBeRemoved The node to be removed
     * @return The removed node
     */
    private ScoreboardNode removeAt(ScoreboardNode currentNode, ScoreboardNode nodeToBeRemoved) {
        LOG.debug("Removing (at) {} from the scoreboard", nodeToBeRemoved.username);
        if (currentNode == null) {
            return null;
        }
        if (currentNode.username.equals(nodeToBeRemoved.username)) {
            if (currentNode.leftScoreboardNode == null) {
                currentNode = currentNode.rightScoreboardNode;
            } else if (currentNode.rightScoreboardNode == null) {
                currentNode = currentNode.leftScoreboardNode;
            } else {
                currentNode.leftScoreboardNode = findReplacement(currentNode.leftScoreboardNode, currentNode);
            }
        } else if (nodeToBeRemoved.compareTo(currentNode) <= 0) {
            currentNode.leftScoreboardNode = removeAt(currentNode.leftScoreboardNode, nodeToBeRemoved);
        } else {
            currentNode.rightScoreboardNode = removeAt(currentNode.rightScoreboardNode, nodeToBeRemoved);
        }
        return currentNode;
    }

    /**
     * Finds the replacement node for the node to be removed
     *
     * @param currentNode     The current node
     * @param replacementNode The replacement node
     * @return The replacement node
     */
    private ScoreboardNode findReplacement(ScoreboardNode currentNode, ScoreboardNode replacementNode) {
        LOG.debug("Finding replacement for {}", replacementNode.username);
        if (currentNode.rightScoreboardNode == null) {
            replacementNode.username = currentNode.username;
            replacementNode.grade = currentNode.grade;
            replacementNode.level = currentNode.level;
            replacementNode.experience = currentNode.experience;
            currentNode = currentNode.leftScoreboardNode;
        } else {
            currentNode.rightScoreboardNode = findReplacement(currentNode.rightScoreboardNode, replacementNode);
        }
        return currentNode;
    }

    /**
     * Searches for a node in the Scoreboard
     *
     * @param currentNode The current node
     * @param searchNode  The node to be searched for
     * @return The searched node
     */
    public ScoreboardNode search(ScoreboardNode currentNode, ScoreboardNode searchNode) {
        LOG.debug("Searching for {} in the scoreboard", searchNode.username);
        if (currentNode == null) {
            return null;
        } else if (searchNode.username.equals(currentNode.username)) {
            return currentNode;
        } else if (searchNode.compareTo(currentNode) <= 0) {
            return search(currentNode.leftScoreboardNode, searchNode);
        } else {
            return search(currentNode.rightScoreboardNode, searchNode);
        }
    }

    /**
     * Updates a node in the Scoreboard
     *
     * @param node          The node to be updated
     * @param newLevel      The new level
     * @param newExperience The new experience
     */
    public void update(ScoreboardNode node, int newLevel, int newExperience) {
        LOG.debug("Updating {} in the scoreboard", node.username);
        ScoreboardNode nodeToUpdate = search(root, node);
        if (nodeToUpdate != null) {
            remove(node);
            nodeToUpdate.level = newLevel;
            nodeToUpdate.experience = newExperience;
            insert(nodeToUpdate);
        }
    }

    /**
     * Checks if the Scoreboard is empty
     *
     * @return True if the Scoreboard is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the size of the Scoreboard
     *
     * @return The size of the Scoreboard
     */
    public int size() {
        return calculateSize(root);
    }

    /**
     * Calculates the size of the Scoreboard
     *
     * @param node The current node
     * @return The size of the Scoreboard
     */
    private int calculateSize(ScoreboardNode node) {
        LOG.debug("Calculating size of the scoreboard");
        if (node == null) {
            return 0;
        }
        return 1 + calculateSize(node.leftScoreboardNode) + calculateSize(node.rightScoreboardNode);
    }

    /**
     * Returns the height of the Scoreboard
     *
     * @return The height of the Scoreboard
     */
    public int height() {
        return calculateHeight(root);
    }

    /**
     * Calculates the height of the Scoreboard
     *
     * @param node The current node
     * @return The height of the Scoreboard
     */
    private int calculateHeight(ScoreboardNode node) {
        LOG.debug("Calculating height of the scoreboard");
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateHeight(node.leftScoreboardNode), calculateHeight(node.rightScoreboardNode));
    }

    public Map<Grade, List<ScoreboardNode>> createRanking() {
        clear();
        for (User user : UserRepository.getInstance().get()) {
            if (user.getRole().equals(Role.USER)) {
                insert(new ScoreboardNode(user.getUsername(), user.getGrade(), user.getLevel(), user.getExperience()));
            }
        }
        return inOrderTraversal(root);
    }

    /**
     * Traverses the Scoreboard in in-order
     *
     * @param node The current node
     * @return The list of nodes in in-order
     */
    Map<Grade, List<ScoreboardNode>> inOrderTraversal(ScoreboardNode node) {
        LOG.debug("Traversing the scoreboard in in-order");
        Map<Grade, List<ScoreboardNode>> gradeMap = new HashMap<>();

        if (node != null) {
            for (Grade grade : Grade.values()) {
                if (!grade.equals(Grade.NONE)) {
                    List<ScoreboardNode> usersOfGrade = getUsersByGrade(node, grade);
                    gradeMap.put(grade, usersOfGrade);
                }
            }
        }

        return gradeMap;
    }

    private List<ScoreboardNode> getUsersByGrade(ScoreboardNode node, Grade grade) {
        List<ScoreboardNode> users = new LinkedList<>();
        if (node.leftScoreboardNode != null) {
            users.addAll(getUsersByGrade(node.leftScoreboardNode, grade));
        }
        if (node.getGrade() == grade) {
            users.add(node);
        }
        if (node.rightScoreboardNode != null) {
            users.addAll(getUsersByGrade(node.rightScoreboardNode, grade));
        }
        return users;
    }

    /**
     * Clears the Scoreboard
     */
    public void clear() {
        LOG.debug("Clearing the scoreboard");
        root = null;
    }

    /**
     * This class represents a node in the Scoreboard
     */
    public static class ScoreboardNode implements Comparable<ScoreboardNode> {
        private String username;
        private Grade grade;
        private int level;
        private int experience;
        private ScoreboardNode leftScoreboardNode = null;
        private ScoreboardNode rightScoreboardNode = null;

        /**
         * Creates a ScoreboardNode
         *
         * @param username   The username of the user
         * @param grade      The grade of the user
         * @param level      The level of the user
         * @param experience The experience of the user
         */
        ScoreboardNode(String username, Grade grade, int level, int experience) {
            this.username = username;
            this.grade = grade;
            this.level = level;
            this.experience = experience;
        }

        /**
         * Compares the current node with another node
         *
         * @param comparingNode the object to be compared.
         * @return 0 if level and experience are equal, -1 if the current node is smaller, 1 if the current node is greater
         */
        @Override
        public int compareTo(ScoreboardNode comparingNode) {
            if (this.level == comparingNode.level && this.experience == comparingNode.experience) {
                return 0;
            }
            if (this.level < comparingNode.level
                    || (this.level == comparingNode.level && this.experience < comparingNode.experience)) {
                return -1;
            } else {
                return 1;
            }
        }

        public String getUsername() {
            return username;
        }

        public Grade getGrade() {
            return grade;
        }

        public int getLevel() {
            return level;
        }

        public int getExperience() {
            return experience;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

    }
}
