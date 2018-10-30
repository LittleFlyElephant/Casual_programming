package practice_for_interview;

import util.TreeNode;

public class LE671 {

    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) return -1;
        if (root.left == null) return -1;
        if (root.left != root.right) return Math.max(root.left.val, root.right.val);
        int leftSecondMin = findSecondMinimumValue(root.left);
        int rightSecondMin = findSecondMinimumValue(root.right);
        if (leftSecondMin == -1) return rightSecondMin;
        if (rightSecondMin == -1) return leftSecondMin;
        return Math.min(leftSecondMin, rightSecondMin);
    }
}
