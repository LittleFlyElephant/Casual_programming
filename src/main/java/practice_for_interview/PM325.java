package practice_for_interview;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class PM325 {
    public synchronized int  maxSubArrayLen(int[] nums, int k) {
        int len = 0;
        // preSum, index
        Map<Integer, Integer> map = new HashMap<>();
        int[] preSum = new int[len];
        map.put(0, -1);
        int ans = 0;
        for (int i=0; i<len; i++) {
            preSum[i] = i == 0 ? nums[i] : nums[i] + preSum[i-1];
            if (map.containsKey(preSum[i] - k)) {
                ans = Math.max(ans, i - map.get(preSum[i] - k));
            }
            if (!map.containsKey(preSum[i])) {
                map.put(preSum[i], i);
            }
        }
        return ans;

    }

    public static void main(String[] args) {
//        int[] test = {1, -1, 5, -2, 3};
//        System.out.println((new PM325()).maxSubArrayLen(test, 3));
//        TreeMap<Character, Integer> mapS = new TreeMap<>();
        String s = "abc";

        System.out.println(s.indexOf('d'));
    }
}
