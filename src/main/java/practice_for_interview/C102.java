package practice_for_interview;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class C102 {

    public int totalFruit(int[] tree) {
        Map<Integer, Integer> map = new HashMap<>();
        Queue<Integer> q = new LinkedList<>();
        int ans = 0, len = tree.length, max = 0;
        for (int i=0; i<len; i++) {
            if (map.containsKey(tree[i])) {
                if (q.peek() == tree[i]) {
                    q.poll();
                    q.offer(tree[i]);
                }
            } else {
                if (q.size() == 2) {
                    int first = q.poll();
                    ans -= map.get(first);
                    map.remove(first);
                    map.put(q.peek(), ans);
                }
                q.offer(tree[i]);
            }
            ans++;
            map.put(tree[i], ans);
            max = Math.max(max, ans);
        }
        return max;
    }

    public void getPar(List<String> ans, int x, String l, String r) {
        if (x == 0) {
            ans.add(l+r);
        } else if (x == 1) {
            for (int i = 0; i < 10; i++) {
                getPar(ans, x-1, l+i, r);
            }
        } else {
            if (l.length() > 0) {
                getPar(ans, x-2, l+0, 0+r);
            }
            for (int i = 1; i < 10; i++) {
                getPar(ans, x-2, l+i, i+r);
            }
        }
    }

    public boolean isPar(char[] arr, int l, int r) {
        if (l >= r) return true;
        if (arr[l] != arr[r]) return false;
        return isPar(arr, l+1, r-1);
    }

    public int superpalindromesInRange(String L, String R) {
        List<String> list = new ArrayList<>();
        for (int i=Math.max(L.length()/2, 1); i<=(R.length()+1)/2; i++) {
            getPar(list, i, "", "");
        }
        for (String str: list) {
            System.out.println(str);
        }
        long min = Long.valueOf(L), max = Long.valueOf(R);
        int ans = 0;
        for (String str: list) {
            long v = Long.valueOf(str);
            String vs = String.valueOf(v*v);
            if (v*v >= min && v*v <= max && isPar(vs.toCharArray(), 0, vs.length()-1)) {
                ans++;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        C102 c102 = new C102();
        System.out.println(c102.superpalindromesInRange("10119", "16264"));
    }
}
