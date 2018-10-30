package practice_for_interview;

public class LH10 {
    boolean[][] dp;
    // abc
    // *c
    public boolean isMatch(String s, String p) {
        int ls = s.length(), lp = p.length();
        dp = new boolean[lp+1][ls+1];
        dp[0][0] = true;
        for (int i=1; i<=lp; i++) {
            char cp = p.charAt(i-1);
            if (cp >= 'a' && cp <= 'z') {
                for (int j=1; j<=ls; j++) {
                    if (s.charAt(j-1) == cp) {
                        dp[i][j] = dp[i][j] || dp[i-1][j-1];
                    }
                }
            } else if (cp == '?') {
                for (int j=1; j<=ls; j++) {
                    dp[i][j] = dp[i][j] || dp[i-1][j-1];
                }
            } else {
                // cp == '*'
                for (int j=0; j<=ls; j++) {
                    for (int k=0; k<=j; k++) {
                        dp[i][j] = dp[i][j] || dp[i-1][k];
                    }
                }
            }
            System.out.print(i+": ");
            for (int j=0; j<=ls; j++) {
                System.out.print(dp[i][j]?1:0);
            }
            System.out.println();
        }
        return dp[lp][ls];
    }

    public boolean isMatch2(String s, String p) {
        // dp[i][j] means if s.substring(0, i+1) matches p.substring(0, j+1)
        // if p=a..z && p[i]=s[i], dp[i][j] = dp[i-1][j-1]
        // if p=., dp[i][j] = dp[i-1][j-1]
        // if p=*, s[i] and p[j-1]
        //      if p[j-1]=. || p[j-1]=s[i], dp[i][j] = dp[i][j-1] || dp[i-1][j] || dp[i][j-2]
        // .    if p[j-1]<>s[i], dp[i][j] = dp[i][j-2]
        int ls = s.length(), lp = p.length();
        dp = new boolean[ls+1][lp+1];
        dp[0][0] = true;
        for (int i=1; i<=ls; i++) {
            for (int j=1; j<=lp; j++) {
                char sc = s.charAt(i-1), pc = p.charAt(j-1);
                if (pc>='a' && pc<='z') {
                    if (pc == sc) {
                        dp[i][j] = dp[i-1][j-1];
                    }
                } else if (pc == '.') {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    pc = p.charAt(j-2);
                    if (pc == sc || pc == '.') {
                        dp[i][j] = dp[i][j-1] || dp[i-1][j] || dp[i][j-2];
                    } else {
                        dp[i][j] = dp[i-1][j-2];
                    }
                }
            }

            System.out.print(i+": ");
            for (int j=0; j<=lp; j++) {
                System.out.print(dp[i][j]?1:0);
            }
            System.out.println();
        }
        return dp[ls][lp];
    }

    public static void main(String[] args) {
        System.out.println((new LH10()).isMatch2("mississippi", "mis*is*p*."));
    }
}
