package practice_for_interview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LH336 {

    boolean isPar(String str) {
        int l = 0, r = str.length()-1;
        while (l < r) {
            if (str.charAt(l) != str.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }

    List<Integer> getAns(int i, int j) {
        List<Integer> tmp = new ArrayList<>();
        tmp.add(i);
        tmp.add(j);
        return tmp;
    }

    public List<List<Integer>> palindromePairs(String[] words) {
        // 1. build trie, or hashmap
        // 2. for each str in list:
        // 3.   enumerate possible palindrome to see if word exists in trie
        Map<String, Integer> wordMap = new HashMap<>();
        for (int i=0; i<words.length; i++) {
            wordMap.put(words[i], i);
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i=0; i<words.length; i++) {
            for (int j=0; j<words[i].length(); j++) {
                String reversed = (new StringBuilder(words[i].substring(j))).reverse().toString();
                if (!reversed.equals(words[i]) && isPar(reversed+words[i]) && wordMap.containsKey(reversed)) {
                    ans.add(getAns(wordMap.get(reversed), i));
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println((new LH336()).palindromePairs(new String[]{"abcd","dcba","lls","s","sssll"}));
    }
}
