package practice_for_interview;

import java.util.*;

public class C101 {

    public int atMostNGivenDigitSet(String[] D, int N) {
        int[] d = new int[D.length];
        for (int i=0; i<D.length; i++) {
            d[i] = Integer.valueOf(D[i]);
        }
        int len = 0, tmp = N;
        int[] nums = new int[11];
        while (tmp > 0) {
            nums[len++] = tmp%10;
            tmp /= 10;
        }
        int ans = 0, js = 1;
        if (len >= 2) {
            for (int i=0; i<len-1; i++) {
                js *= D.length;
                ans += js;
            }
        }
        int j = len-1;
        boolean b = false;
        while (j >= 0 && b) {
            b = false;
            for (int i=0; i<d.length; i++) {
                if (d[i] < nums[j]) {
                    ans += js;
                } else if (d[i] >= nums[j-1]) {
                    if (d[i] == nums[j-1]) b = true;
                    break;
                }
            }
            js /= D.length;
            j --;
        }
        if (j == -1 && b) ans ++;
        return ans;
    }

    public static void main(String[] args) {
        C101 c101 = new C101();
        String[] t = {"1", "3", "5", "7"};
        System.out.println(c101.atMostNGivenDigitSet(t, 73));
        List<Integer> list = new ArrayList<>();
        list.clear();
    }
}
