package practice_for_interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AutocompleteSystem {

    static int size = 26;

    class Trie{
        Trie[] next = new Trie[size+1];  // add one more for blank
        boolean end = false;
        int count = 0;
    }

    class StrNode{
        String str;
        int num;
        StrNode(String str, int num) {
            this.str = str;
            this.num = num;
        }
    }

    Trie root = new Trie();
    String currentStr = "";

    void insert(Trie node, char[] arr, int x, int times) {
        if (x == arr.length) {
            if (node.end) {
                node.count += times;
            } else {
                node.end = true;
                node.count = times;
            }
        } else {
            int index = arr[x]==' '?size:arr[x]-'a';
            if (node.next[index] == null) {
                node.next[index] = new Trie();
            }
            insert(node.next[index], arr, x+1, times);
        }
    }

    void findPrefix(Trie node, char[] arr, int x, String path, List<StrNode> ans) {
        if (x == arr.length) {
            if (node.end) {
                ans.add(new StrNode(path, node.count));
            }
            for (int i=0; i<=size; i++) {
                if (node.next[i] != null) {
                    findPrefix(node.next[i], arr, x, path+(i==size?' ':(char)('a'+i)), ans);
                }
            }
        } else {
            int index = arr[x]==' '?size:arr[x]-'a';
            if (node.next[index] == null) return;
            findPrefix(node.next[index], arr, x+1, path+arr[x], ans);
        }
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        for (int i=0; i<sentences.length; i++) {
            insert(root, sentences[i].toCharArray(), 0, times[i]);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            //deal with special character
            insert(root, currentStr.toCharArray(), 0, 1);
            currentStr = "";
            return new ArrayList<>();
        } else {
            //deal with common input
            currentStr += c;
            List<StrNode> hotStrs = new ArrayList<>();
            findPrefix(root, currentStr.toCharArray(), 0, "", hotStrs);
            List<String> ans = new ArrayList<>();
            Collections.sort(hotStrs, new Comparator<StrNode>(){
                @Override
                public int compare(StrNode n1, StrNode n2) {
                    if (n1.num == n2.num) {
                        return n1.str.compareTo(n2.str);
                    }
                    return n2.num - n1.num;
                }
            });
            for (int i=0; i<Math.min(3, hotStrs.size()); i++) {
                ans.add(hotStrs.get(i).str);
            }
            return ans;
        }
    }
}
