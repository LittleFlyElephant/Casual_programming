package practice_for_interview;

public class LH903 {

    public int numPermsDISequence(String S) {
        int base = 1000000007;
        int len = S.length();
        char[] arr = S.toCharArray();
        int[][] dp = new int[len+1][len+1];
        for (int i=0; i<=len; i++) {
            dp[0][i] = 1;
        }
        for (int i=0; i<len; i++) {
            for (int j=0; j<len-i; j++) {
                if (arr[i] == 'I') {
                    for (int k=0; k<=j; k++) {
                        dp[i+1][j] = (dp[i+1][j] + dp[i][k]) % base;
                    }
                } else {
                    for (int k=j+1; k<=len-i; k++) {
                        dp[i+1][j] = (dp[i+1][j] + dp[i][k]) % base;
                    }
                }
                System.out.print(dp[i+1][j]+" ");
            }
            System.out.println();
        }
        return dp[len][0];
    }

    public static void main(String[] args) {
        LH903 lh903 = new LH903();
        System.out.println(lh903.numPermsDISequence("DID"));
    }
}
