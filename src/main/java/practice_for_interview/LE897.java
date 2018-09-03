package practice_for_interview;

import util.TreeNode;

public class LE897 {

    TreeNode head = null;
    TreeNode cur = null;

    void dfs(TreeNode node) {
        if (node.left != null) dfs(node.left);
        // This line is important
        node.left = null;
        if (head == null) {
            head = node;
            cur = head;
        } else {
            cur.right = node;
            cur = node;
        }
        if (node.right != null) dfs(node.right);
    }

    public TreeNode increasingBST(TreeNode root) {
        if (root == null) return null;
        dfs(root);
        return head;
    }

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(5);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(6);
        TreeNode n4 = new TreeNode(2);
        TreeNode n5 = new TreeNode(4);
        TreeNode n6 = new TreeNode(1);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
        TreeNode n9 = new TreeNode(9);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n4.left = n6;
        n3.right = n8;
        n8.left = n7;
        n8.right = n9;
        (new LE897()).increasingBST(n1);
    }
}
