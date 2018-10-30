package practice_for_interview.dp;

public class LM152 {
    public int maxProduct(int[] nums) {
        int len = nums.length;
        int[] pos = new int[len];
        int[] neg = new int[len];
        int ans = nums[0];
        for (int i=0; i<len; i++) {
            pos[i] = nums[i];
            neg[i] = nums[i];
            if (i > 0 && nums[i] != 0) {
                if (nums[i] > 0) {
                    if (pos[i-1] > 0) {
                        pos[i] = pos[i-1]*nums[i];
                    }
                    if (neg[i-1] < 0) {
                        neg[i] = neg[i-1]*nums[i];
                    }
                } else {
                    if (neg[i-1] < 0) {
                        pos[i] = neg[i-1]*nums[i];
                    }
                    if (pos[i-1] > 0) {
                        neg[i] = pos[i-1]*nums[i];
                    }
                }
            }
            ans = Math.max(ans, pos[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] test = {2,-5,-2,-4,3};
        System.out.println((new LM152()).maxProduct(test));
    }
}
