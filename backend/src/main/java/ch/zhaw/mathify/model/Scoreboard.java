package ch.zhaw.mathify.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scoreboard {
    private ScoreboardNode root;

    public ScoreboardNode getRoot() {
        return root;
    }

    public void insert(ScoreboardNode node) {
        root = insertAt(root, node);
    }

    private ScoreboardNode insertAt(ScoreboardNode currentNode, ScoreboardNode newNode) {
        if (currentNode == null) {
            return new ScoreboardNode(newNode.username, newNode.grade, newNode.level, newNode.experience);
        } else if (newNode.compareTo(currentNode) <= 0) {
            currentNode.leftScoreboardNode = insertAt(currentNode.leftScoreboardNode, newNode);
        } else {
            currentNode.rightScoreboardNode = insertAt(currentNode.rightScoreboardNode, newNode);
        }
        return balance(currentNode);
    }

    public ScoreboardNode remove(ScoreboardNode node) {
        if (root != null) {
            root = removeAt(root, node);
        }
        return null;
    }

    private ScoreboardNode removeAt(ScoreboardNode currentNode, ScoreboardNode nodeToBeRemoved) {
        if (currentNode.username.equals(nodeToBeRemoved.username)) {
            if (currentNode.leftScoreboardNode == null) {
                currentNode = currentNode.rightScoreboardNode;
            } else if (currentNode.rightScoreboardNode == null) {
                currentNode = currentNode.leftScoreboardNode;
            } else {
                currentNode.leftScoreboardNode = findReplacement(currentNode.leftScoreboardNode, currentNode);
            }
        } else if (nodeToBeRemoved.compareTo(currentNode) < 0) {
            currentNode.leftScoreboardNode = removeAt(currentNode.leftScoreboardNode, nodeToBeRemoved);
        } else {
            currentNode.rightScoreboardNode = removeAt(currentNode.rightScoreboardNode, nodeToBeRemoved);
        }
        return balance(currentNode);
    }

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

    public void update(ScoreboardNode node, int newLevel, int newExperience) {
        ScoreboardNode nodeToUpdate = search(root, node);
        if (nodeToUpdate != null && nodeToUpdate.level != newLevel && nodeToUpdate.experience != newExperience) {
            remove(node);
            nodeToUpdate.level = newLevel;
            nodeToUpdate.experience = newExperience;
            insert(nodeToUpdate);
        } else {
            insert(node);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return calculateSize(root);
    }

    private int calculateSize(ScoreboardNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + calculateSize(node.leftScoreboardNode) + calculateSize(node.rightScoreboardNode);
    }

    public int height() {
        return calculateHeight(root);
    }

    private int calculateHeight(ScoreboardNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateHeight(node.leftScoreboardNode), calculateHeight(node.rightScoreboardNode));
    }

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

    public void clear() {
        root = null;
    }

    private ScoreboardNode balance(ScoreboardNode node) {
        if (node == null) {
            return null;
        }
        if (calculateHeight(node.leftScoreboardNode) - calculateHeight(node.rightScoreboardNode) == 2) {
            if (calculateHeight(node.leftScoreboardNode.leftScoreboardNode) >= calculateHeight(node.leftScoreboardNode.rightScoreboardNode)) {
                node = rotateRight(node);
            } else {
                node = rotateLeftRight(node);
            }
        } else if (calculateHeight(node.rightScoreboardNode) - calculateHeight(node.leftScoreboardNode) == 2) {
            if (calculateHeight(node.rightScoreboardNode.rightScoreboardNode) >= calculateHeight(node.rightScoreboardNode.leftScoreboardNode)) {
                node = rotateLeft(node);
            } else {
                node = rotateRightLeft(node);
            }
        }
        return node;
    }

    private ScoreboardNode rotateLeft(ScoreboardNode node) {
        ScoreboardNode newRoot = node.rightScoreboardNode;
        node.rightScoreboardNode = newRoot.leftScoreboardNode;
        newRoot.leftScoreboardNode = node;
        return newRoot;
    }

    private ScoreboardNode rotateRight(ScoreboardNode node) {
        ScoreboardNode newRoot = node.leftScoreboardNode;
        node.leftScoreboardNode = newRoot.rightScoreboardNode;
        newRoot.rightScoreboardNode = node;
        return newRoot;
    }

    private ScoreboardNode rotateLeftRight(ScoreboardNode node) {
        node.leftScoreboardNode = rotateLeft(node.leftScoreboardNode);
        return rotateRight(node);
    }

    private ScoreboardNode rotateRightLeft(ScoreboardNode node) {
        node.rightScoreboardNode = rotateRight(node.rightScoreboardNode);
        return rotateLeft(node);
    }

    public static class ScoreboardNode implements Comparable<ScoreboardNode> {
        String username;
        Grade grade;
        int level;
        int experience;
        ScoreboardNode leftScoreboardNode = null;
        ScoreboardNode rightScoreboardNode = null;

        ScoreboardNode(String username, Grade grade, int level, int experience) {
            this.username = username;
            this.grade = grade;
            this.level = level;
            this.experience = experience;
        }

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
    }
}
