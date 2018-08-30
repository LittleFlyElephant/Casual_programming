package practice_for_interview.locked_problems;

import util.Node;

public class LM426 {


    Node first = null;
    Node prev = null;

    void dfs(Node node) {
        if (node == null) return;
        dfs(node.left);
        if (prev == null) {
            first = node;
        } else {
            prev.right = node;
            node.left = prev;
        }
        prev = node;
        dfs(node.right);
    }

    public Node treeToDoublyList(Node root) {
        dfs(root);
        if (prev != null) {
            prev.right = first;
            first.left = prev;
        }
        return first;
    }
}
