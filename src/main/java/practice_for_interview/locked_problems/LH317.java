package practice_for_interview.locked_problems;

import java.util.*;

public class LH317 {

    int M, N;
    int[] dx = {0,0,1,-1};
    int[] dy = {1,-1,0,0};
    static int d = 4;

    class Loc {
        int x, y;
        Loc(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    void printVis(int[][] vis) {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(vis[i][j]+" ");
            }
            System.out.println();
        }
    }

    int bfs(int[][] grid, Loc s) {
        Queue<Loc> q = new LinkedList<>();
        int[][] vis = new int[M][N];
        q.offer(s);
        for (int i=0; i<M; i++) Arrays.fill(vis[i], -1);
        vis[s.x][s.y] = 0;
        while (!q.isEmpty()) {
            List<Loc> nLocs = new ArrayList<>();
            while (!q.isEmpty()) {
                Loc cur = q.poll();
                for (int i = 0; i < d; i++) {
                    if (cur.x + dx[i] < M && cur.x + dx[i] >= 0 && cur.y + dy[i] < N && cur.y + dy[i] >= 0) {
                        if (vis[cur.x + dx[i]][cur.y + dy[i]] == -1) {
                            switch (grid[cur.x + dx[i]][cur.y + dy[i]]) {
                                case 1:
                                    vis[cur.x + dx[i]][cur.y + dy[i]] = vis[cur.x][cur.y] + 1;
                                    break;
                                case 0: {
                                    vis[cur.x + dx[i]][cur.y + dy[i]] = vis[cur.x][cur.y] + 1;
                                    nLocs.add(new Loc(cur.x + dx[i], cur.y + dy[i]));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            for (Loc l: nLocs) {
                q.offer(l);
            }
        }
        System.out.println(s.x+" "+s.y);
        printVis(vis);
        int ans = 0;
        for (int i=0; i<M; i++) {
            for (int j=0; j<N; j++) {
                if (grid[i][j] == 1) {
                    if (vis[i][j] == -1) return -1;
                    ans += vis[i][j];
                }
            }
        }
        return ans;
    }

    public int shortestDistance(int[][] grid) {
        if (grid.length == 0) return -1;
        M = grid.length;
        N = grid[0].length;
        int ans = -1;
        for (int i=0; i<M; i++) {
            for (int j=0; j<N; j++) {
                if (grid[i][j] == 0) {
                    int val = bfs(grid, new Loc(i, j));
                    if (val != -1) {
                        ans = ans==-1?val:Math.min(ans, val);
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] test = {
                {1,0,2,0,1},
                {0,0,0,0,0},
                {0,0,1,0,0}
        };
        System.out.println((new LH317()).shortestDistance(test));
    }
}
