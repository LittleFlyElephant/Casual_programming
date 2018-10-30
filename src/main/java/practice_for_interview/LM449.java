package practice_for_interview;

import util.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LM449 {
    void bfs(List<String> list, TreeNode node) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(node);
        boolean hasNext = true;
        while (hasNext) {
            int size = q.size();
            hasNext = false;
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();
                if (n == null) {
                    list.add("-");
                    q.offer(null);
                    q.offer(null);
                } else {
                    hasNext = true;
                    list.add(String.valueOf(n.val));
                    q.offer(n.left);
                    q.offer(n.right);
                }
            }
        }
    }

    TreeNode dedfs(String[] list, int x) {
        if (x >= list.length || list[x].equals("-")) {
            return null;
        }
        int val = Integer.valueOf(list[x]);
        TreeNode node = new TreeNode(val);
        node.left = dedfs(list, x*2+1);
        node.right = dedfs(list, x*2+2);
        return node;
    }

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        List<String> list = new ArrayList<>();
        bfs(list, root);
        String ans = "";
        for (int i=0; i<list.size()-1; i++) {
            ans += String.valueOf(list.get(i))+"=";
        }
        ans += String.valueOf(list.get(list.size()-1));
        return ans;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] list = data.split("=");
        return dedfs(list, 0);
    }

    public static void main(String[] args) {
        LM449 lm449 = new LM449();
        TreeNode n1 = new TreeNode(2);
        n1.left = new TreeNode(1);
        n1.right = new TreeNode(3);
        System.out.println(lm449.serialize(n1));
    }
}
