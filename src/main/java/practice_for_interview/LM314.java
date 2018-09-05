package practice_for_interview;

import util.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LM314 {

    int min = 0;
    int max = 0;

    void dfs(TreeNode node, int val, int h, TreeNode par) {
        if (node == null) return;
        node.index = val;
        node.h = h;
        node.par = par;
        min = Math.min(min, val);
        max = Math.max(max, val);
        dfs(node.left, val-1, h+1, node);
        dfs(node.right, val+1, h+1, node);
    }

    void dfs2(TreeNode node, List<TreeNode>[] list) {
        if (node == null) return;
        int index = node.index - min;
        if (list[index] == null) {
            list[index] = new ArrayList<>();
        }
        int ins = list[index].size();
        for (int i=0; i<list[index].size(); i++) {
            if (list[index].get(i).h > node.h) {
                ins = i;
                break;
            }
        }
        list[index].add(ins, node);
        dfs2(node.left, list);
        dfs2(node.right, list);
    }

    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        dfs(root, 0, 0, null);
        List<TreeNode>[] list = new List[max-min+1];
        dfs2(root, list);
        for (List<TreeNode> nodes: list) {
            List<Integer> tmp = new ArrayList<>();
            for (TreeNode node: nodes) {
                tmp.add(node.val);
            }
            ans.add(tmp);
        }
        return ans;
    }
}
