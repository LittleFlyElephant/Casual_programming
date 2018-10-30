package practice_for_interview;

import java.util.ArrayList;
import java.util.List;

public class KMP {
    int[] lps;

    void getLPS(char[] pat) {
        int i=1, len = 0;
        lps[0] = 0;
        while (i < pat.length) {
            if (pat[i] == pat[len]) {
                lps[i++] = ++len;
            } else {
                if (len != 0) {
                    len = lps[len-1];
                } else {
                    lps[i++] = 0;
                }
            }
        }
    }

    List<Integer> getMatchIndex(char[] str, char[] pat) {
        lps = new int[pat.length];
        getLPS(pat);
        List<Integer> ans = new ArrayList<>();
        int i = 0, j = 0;
        while (i < str.length) {
            if (str[i] == pat[j]) {
                i++;
                j++;
            }
            if (j == pat.length) {
                ans.add(i-j);
                j = lps[j-1];
            }else if (i < str.length && str[i] != pat[j]) {
                if (j == 0) {
                    i++;
                } else {
                    j = lps[j-1];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String str = "AABAACAADAABAABA";
        String pat = "AABA";
        List<Integer> ans = (new KMP()).getMatchIndex(str.toCharArray(), pat.toCharArray());
        System.out.println(ans.toString());
    }
}
