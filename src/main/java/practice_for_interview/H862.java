package practice_for_interview;

import util.Info;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class H862 {

    public int shortestSubarray(int[] A, int K) {
        Stack<Info> s = new Stack<>();
        int ans = Integer.MAX_VALUE;
        for (int i=0; i<A.length; i++) {
            List<Info> tmp = new ArrayList<>();
            while (!s.isEmpty()) {
                tmp.add(0, s.pop());
            }
            if (A[i] >= K) return 1;
            s.push(new Info(A[i], 1));
            for (Info inf: tmp) {
                if (inf.sum+A[i] > s.peek().sum && inf.len < ans-1) {
                    if (inf.sum+A[i] >= K) ans = inf.len + 1;
                    s.push(new Info(inf.sum+A[i], inf.len+1));
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
