package practice_for_interview;

public class PM287 {
    public int findDuplicate(int[] nums) {
        int n = nums.length;
        for(int i=0;i<nums.length;i++) nums[i]--;
        int slow = n-1;
        int fast = n-1;
        do{
            slow = nums[nums[slow]];
            fast = nums[nums[nums[fast]]];
        }while(slow != fast);
        slow = n-1;
        while(slow != fast){
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow+1;
    }

    public static void main(String[] args) {
        int[] test = {1, 3, 4, 5, 2, 3};
        System.out.println((new PM287()).findDuplicate(test));
    }
}
