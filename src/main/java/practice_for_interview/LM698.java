package practice_for_interview;

public class LM698 {
    int n, target;
    boolean[] use;
    int[] nums;

    boolean helper(int sum, int k) {
        if (k == 0) {
            if (sum != 0) return false;
            for (int i=0; i<n; i++) {
                if (!use[i]) {
                    return false;
                }
            }
            return true;
        } else {
            if (sum > target) return false;
            if (sum == target) {
                return helper(0, k-1);
            }
            for (int i=0; i<n; i++) {
                if (!use[i]) {
                    use[i] = true;
                    if (helper(sum+nums[i], k)) {
                        return true;
                    }
                    use[i] = false;
                }
            }
            return false;
        }
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        n = nums.length;
        this.nums = nums;
        int sum = 0;
        if (n == 0) return false;
        if (k == 1) return true;
        use = new boolean[n];
        for (int i=0; i<n; i++) sum += nums[i];
        if (sum % k != 0) return false;
        target = sum/k;
        return helper(0, k);
    }

    public static void main(String[] args) {
        LM698 lm698 = new LM698();
        int[] test = {4,3,2,3,5,2,1};
        System.out.println(lm698.canPartitionKSubsets(test, 4));
    }
}
