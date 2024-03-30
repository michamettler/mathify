package ch.zhaw.mathify.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Scoreboard {
    private ScoreboardNode root;

    public ScoreboardNode insert(ScoreboardNode node, ScoreboardNode rootNode) {
        if (node == null) {
            return null;
        } else if (rootNode.compareTo(node) <= 0) {
            node.leftScoreboardNode = insert(node.leftScoreboardNode, node);
        } else {
            node.rightScoreboardNode = insert(node.rightScoreboardNode, node);
        }
        return balance(node);
    }

    public ScoreboardNode remove(ScoreboardNode node, ScoreboardNode rootNode) {
        if (node.username.equals(rootNode.username)) {
            if (node.leftScoreboardNode == null) {
                node = node.rightScoreboardNode;
            } else if (node.rightScoreboardNode == null) {
                node = node.leftScoreboardNode;
            } else {
                node.leftScoreboardNode = findReplacement(node.leftScoreboardNode, node);
            }
        } else if (rootNode.compareTo(node) < 0) {
            node.leftScoreboardNode = remove(node.leftScoreboardNode, rootNode);
        } else {
            node.rightScoreboardNode = remove(node.rightScoreboardNode, rootNode);
        }
        return node;
    }

    private ScoreboardNode findReplacement(ScoreboardNode node, ScoreboardNode rootNode) {
        if (node.rightScoreboardNode == null) {
            node = node.leftScoreboardNode;
        } else {
            node.rightScoreboardNode = findReplacement(node.rightScoreboardNode, rootNode);
        }
        return node;
    }

    public ScoreboardNode search(ScoreboardNode node, ScoreboardNode rootNode) {
        if (node == null) {
            return null;
        } else if (rootNode.compareTo(node) == 0) {
            return rootNode;
        } else if (rootNode.compareTo(node) < 0) {
            return search(node, rootNode.rightScoreboardNode);
        } else {
            return search(node, rootNode.leftScoreboardNode);
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
            inOrderList.addAll(preOrderTraversal(node.leftScoreboardNode));
            inOrderList.add(node);
            inOrderList.addAll(preOrderTraversal(node.rightScoreboardNode));
        }
        return inOrderList;
    }

    public List<ScoreboardNode> preOrderTraversal(ScoreboardNode node) {
        List<ScoreboardNode> preOrderList = new LinkedList<>();
        if (node != null) {
            preOrderList.add(node);
            preOrderList.addAll(preOrderTraversal(node.leftScoreboardNode));
            preOrderList.addAll(preOrderTraversal(node.rightScoreboardNode));
        }
        return preOrderList;
    }

    public List<ScoreboardNode> postOrderTraversal(ScoreboardNode node) {
        List<ScoreboardNode> postOrderList = new LinkedList<>();
        if (node != null) {
            postOrderList.addAll(preOrderTraversal(node.leftScoreboardNode));
            postOrderList.addAll(preOrderTraversal(node.rightScoreboardNode));
            postOrderList.add(node);
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

    public class ScoreboardNode implements Comparable<ScoreboardNode> {
        String username;
        Grade grade;
        int level;
        int experience;
        ScoreboardNode leftScoreboardNode = null;
        ScoreboardNode rightScoreboardNode = null;

        private ScoreboardNode(String username, Grade grade, int level, int experience) {
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
