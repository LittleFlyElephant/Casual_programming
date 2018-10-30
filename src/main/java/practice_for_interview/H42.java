package practice_for_interview;

public class H42 {

    int getWater(int[] h, int l, int r) {
        int sum = 0, minH = Math.min(h[l], h[r]);
        for (int i=l+1; i<=r-1; i++) {
            sum += Math.max(0, minH - h[i]);
        }
        return sum;
    }

    public int trap(int[] height) {
        int l=0, r=1, n = height.length, ans = 0;
        while (r < n) {
            int currentRmax = r;
            while (r < n && height[r] < height[l]) {
                if (height[r] >= height[currentRmax]) {
                    currentRmax = r;
                }
                r++;
            }
            if (r < n) {
                // count from left to r
                ans += getWater(height, l, r);
                l = r;
            } else {
                // count from left to current R max
                ans += getWater(height, l, currentRmax);
                l = currentRmax;
            }
            r = l+1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println((new H42()).trap(new int[]{6,4,2,0,3,2,0,3,1,4,5,3,2,7,5,3,0,1,2,1,3,4,6,8,1,3}));
    }
}
