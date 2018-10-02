package practice_for_interview;

import java.util.*;

public class Test {

    int[] dx = {0,0,-1,1};
    int[] dy = {-1,1,0,0};

    class Union{
        int[] p;
        int[] size;

        // p[num] is the root
        Union(int num) {
            p = new int[num+1];
            size = new int[num+1];
            for (int i=0; i<=num; i++) {
                p[i] = i;
                size[i] = 1;
            }
            size[num] = 0;
        }

        public void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px != py) {
                p[px] = py;
                size[py] += size[px];
            }
        }

        public int find(int x) {
            if (p[x] != x) {
                p[x] = find(p[x]);
            }
            return p[x];
        }

        public int top() {
            return size[find(size.length-1)];
        }
    }

    public int[] hitBricks(int[][] grid, int[][] hits) {
        int r = grid.length;
        int c = grid[0].length;
        int hitLen = hits.length;
        int[][] grid_final = new int[r][c];
        for (int i=0; i<grid.length; i++) {
            grid_final[i] = grid[i].clone();
        }
        for (int[] hit: hits) {
            grid_final[hit[0]][hit[1]] = 0;
        }
        Union uni = new Union(r*c);
        for (int i=0; i<r; i++) {
            for (int j=0; j<c; j++) {
                if (grid_final[i][j] == 1) {
                    if (i == 0) {
                        // roof
                        uni.union(j, r*c);
                    } else {
                        if (j > 0 && grid_final[i][j-1] == 1) {
                            uni.union(i*c+j, i*c+j-1);
                        }
                        if (grid_final[i-1][j] == 1) {
                            uni.union(i*c+j, (i-1)*c+j);
                        }
                    }
                }
            }
        }
        int[] ans = new int[hitLen];
        for (int i=hitLen-1; i>=0; i--) {
            int hx = hits[i][0];
            int hy = hits[i][1];
            int preSize = uni.top();
            if (grid[hx][hy] == 0) {
                // meaningless hit
                ans[i] = 0;
            } else {
                grid_final[hx][hy] = 1;
                if (hx == 0) {
                    uni.union(hy, r*c);
                }
                for (int j=0; j<4; j++) {
                    int nx = hx+dx[j];
                    int ny = hy+dy[j];
                    if (nx >= 0 && ny >= 0 && nx < r && ny < c && grid_final[nx][ny] == 1) {
                        uni.union(hx*c+hy, nx*c+ny);
                    }
                }
                ans[i] = Math.max(0, uni.top() - preSize - 1);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        //[[3,4],[3,5],[3,6],[0,1,2],[0,5,6],[1,4],[2,4]] 0
        //[[2,3],[3,4],[0,4],[0,1],[1,2]] 1
        Test test = new Test();
        int[][] grid = {{1},{1},{1},{1},{1}};
        int[][] hits = {{3,0},{4,0},{1,0},{2,0},{0,0}};
        for (int i: test.hitBricks(grid, hits)){
            System.out.println(i);
        }
    }
}
