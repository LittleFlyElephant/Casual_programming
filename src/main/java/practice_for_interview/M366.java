package practice_for_interview;

import util.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class M366 {

    Map<Integer, Integer> parMap = new HashMap<>();
    Map<Integer, Integer> inDegree = new HashMap<>();

    void dfs(TreeNode node, TreeNode par) {
        if (node == null) return;
        inDegree.put(node.val, 0);
        if (par != null) {
            parMap.put(node.val, par.val);
            inDegree.put(par.val, inDegree.get(par.val)+1);
        }
        dfs(node.left, node);
        dfs(node.right, node);
    }

    public List<List<Integer>> findLeaves(TreeNode root) {
        dfs(root, null);
        List<List<Integer>> ans = new ArrayList<>();
        while (inDegree.keySet().size() > 0) {
            List<Integer> tmp = new ArrayList<>();
            for (Integer key: inDegree.keySet()) {
                if (inDegree.get(key) == 0) {
                    tmp.add(key);
                }
            }
            for (Integer key: tmp) {
                inDegree.remove(key);
                if (parMap.containsKey(key)) {
                    int par = parMap.get(key);
                    inDegree.put(par, inDegree.get(par)-1);
                }
            }
            ans.add(tmp);
        }
        return ans;
    }

    public static void main(String[] args) {
        TreeNode n1 = new TreeNode(37);
        TreeNode n2 = new TreeNode(-34); n1.left = n2;
        TreeNode n3 = new TreeNode(-100); n2.right = n3;
        TreeNode n4 = new TreeNode(-48); n1.right = n4;
        TreeNode n5 = new TreeNode(1);
        TreeNode n6 = new TreeNode(2);
        TreeNode n7 = new TreeNode(3);
        TreeNode n8 = new TreeNode(4);
        TreeNode n9 = new TreeNode(3);
        TreeNode n0 = new TreeNode(4);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        System.out.println((new M366()).findLeaves(n1));
    }
}
