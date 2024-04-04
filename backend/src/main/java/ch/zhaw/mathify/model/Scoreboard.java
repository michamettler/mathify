package ch.zhaw.mathify.model;

import ch.zhaw.mathify.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static ch.zhaw.mathify.controller.UserController.USERS_JSON_FILE;

/**
 * This class represents an AVL-tree that stores the user's data in the scoreboard.
 */
public class Scoreboard {
    private ScoreboardNode root;
    private static final Logger LOG = LoggerFactory.getLogger(Scoreboard.class);

    /**
     * Creates a Scoreboard and loads the current users from the users.json file
     */
    public Scoreboard() {
        try {
            if (!USERS_JSON_FILE.exists()) {
                LOG.error("users.json does not exist!");
                throw new IOException("users.json does not exist!");
            }
            List<User> users = JsonMapper.map(Files.readString(USERS_JSON_FILE.toPath()), User.class);
            for (User user : users) {
                insert(new ScoreboardNode(user.getUsername(), user.getGrade(), user.getLevel(), user.getExperience()));
            }
            LOG.info("users.json was read successfully");
        } catch (IOException e) {
            LOG.error("Could not read users.json!", e);
        }
    }

    public ScoreboardNode getRoot() {
        return root;
    }

    /**
     * Inserts a new node into the Scoreboard
     *
     * @param node The node to be inserted
     */
    public void insert(ScoreboardNode node) {
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
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateHeight(node.leftScoreboardNode), calculateHeight(node.rightScoreboardNode));
    }

    /**
     * Traverses the Scoreboard in in-order
     *
     * @param node The current node
     * @return The list of nodes in in-order
     */
    public List<ScoreboardNode> inOrderTraversal(ScoreboardNode node) {
        List<ScoreboardNode> inOrderList = new LinkedList<>();

        if (node != null) {
            if (node.leftScoreboardNode != null) {
                inOrderList.addAll(inOrderTraversal(node.leftScoreboardNode));
            }
            inOrderList.addLast(node);
            if (node.rightScoreboardNode != null) {
                inOrderList.addAll(inOrderTraversal(node.rightScoreboardNode));
            }
        }
        return inOrderList;
    }

    /**
     * Traverses the Scoreboard in pre-order
     *
     * @param node The current node
     * @return The list of nodes in pre-order
     */
    public List<ScoreboardNode> preOrderTraversal(ScoreboardNode node) {
        List<ScoreboardNode> preOrderList = new LinkedList<>();

        if (node != null) {
            preOrderList.addLast(node);
            if (node.leftScoreboardNode != null) {
                preOrderList.addAll(inOrderTraversal(node.leftScoreboardNode));
            }
            if (node.rightScoreboardNode != null) {
                preOrderList.addAll(inOrderTraversal(node.rightScoreboardNode));
            }
        }
        return preOrderList;
    }

    /**
     * Traverses the Scoreboard in post-order
     *
     * @param node The current node
     * @return The list of nodes in post-order
     */
    public List<ScoreboardNode> postOrderTraversal(ScoreboardNode node) {
        List<ScoreboardNode> postOrderList = new LinkedList<>();

        if (node != null) {
            if (node.leftScoreboardNode != null) {
                postOrderList.addAll(inOrderTraversal(node.leftScoreboardNode));
            }
            if (node.rightScoreboardNode != null) {
                postOrderList.addAll(inOrderTraversal(node.rightScoreboardNode));
            }
            postOrderList.addLast(node);
        }
        return postOrderList;
    }

    /**
     * Traverses the Scoreboard in level-order
     *
     * @param node The current node
     * @return The list of nodes in level-order
     */
    public List<ScoreboardNode> levelOrderTraversal(ScoreboardNode node) {
        List<ScoreboardNode> levelOrderList = new LinkedList<>();
        Queue<ScoreboardNode> queue = new LinkedList<>();
        if (node != null) {
            queue.add(node);
        }
        while (!queue.isEmpty()) {
            node = queue.poll();
            levelOrderList.add(node);
            if (node.leftScoreboardNode != null) {
                queue.add(node.leftScoreboardNode);
            }
            if (node.rightScoreboardNode != null) {
                queue.add(node.rightScoreboardNode);
            }
        }
        return levelOrderList;
    }

    /**
     * Clears the Scoreboard
     */
    public void clear() {
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

        public void setLevel(int level) {
            this.level = level;
        }

        public void setExperience(int experience) {
            this.experience = experience;
        }

        public int getExperience() {
            return experience;
        }
    }
}
