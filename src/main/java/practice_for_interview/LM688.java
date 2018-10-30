package practice_for_interview;

public class LM688 {
    int[] dx = {-2,-2,-1,-1,1,1,2,2};
    int[] dy = {-1,1,-2,2,-2,2,1,-1};

    int n;
    double[][][] dp;

    double dfs(int x, int y, double prob, int l) {
        if (x<0 || x>=n || y>=n || y<0) return 0.0;
        if (dp[x][y][l] != 0.0) return dp[x][y][l];
        if (l == 0) {
            dp[x][y][l] = prob;
        } else {
            for (int i=0; i<8; i++) {
                dp[x][y][l] += dfs(x+dx[i], y+dy[i], prob*0.125, l-1);
            }
        }
        return dp[x][y][l];
    }

    public double knightProbability(int N, int K, int r, int c) {
        dp = new double[N][N][K+1];
        n = N;
        return dfs(r, c, 1.0, K);
    }

    public static void main(String[] args) {
        System.out.println((new LM688()).knightProbability(3,3,0,0));
    }
}
