package practice_for_interview;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class LH864 {
    // https://www.youtube.com/watch?v=A-8ziQc_4pk&list=PLLuMmzMTgVK66JImslfqAJLovRAyQKAas&index=18
    // 听了题解

    int[] dx = {1,-1,0,0};
    int[] dy = {0,0,1,-1};
    char[][] board;
    int m, n;
    HashMap<Integer, Integer> states = new HashMap<>();

    int getState(int x, int y, int key) {
        return x << 12 | y << 6 | key;
    }

    int getX(int state) {
        return (state >> 12) & (0x3f);
    }

    int getY(int state) {
        return (state >> 6) & (0x3f);
    }

    public int shortestPathAllKeys(String[] grid) {
        m = grid.length;
        n = grid[0].length();
        board = new char[m][n];
        int all_key = 0;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = grid[i].charAt(j);
                if (board[i][j] == '@') {
                    int state = getState(i, j, 0);
                    q.offer(state);
                    states.put(state, 0);
                } else if (board[i][j] >= 'A' && board[i][j] <= 'F') {
                    all_key = all_key | (1 << (board[i][j] - 'A'));
                }
            }
        }
        int step = 0;
//        System.out.println(all_key);
        while (!q.isEmpty()) {
            int size = q.size();
//            System.out.println("step: "+(step+1));
            while (size > 0) {
                int st = q.poll();
                int x = getX(st);
                int y = getY(st);
                for (int i = 0; i < dx.length; i++) {
                    if (x+dx[i]>=0 && y+dy[i]>=0 && x+dx[i]<m && y+dy[i]<n) {
                        int newKey = 0;
                        if (board[x+dx[i]][y+dy[i]] >= 'A' && board[x+dx[i]][y+dy[i]] <= 'F') {
                            // locks
                            if (((st & 0x3f) & (1 << (board[x+dx[i]][y+dy[i]] - 'A'))) == 0) continue;
                        } else if (board[x+dx[i]][y+dy[i]] == '#') continue;
                        else if (board[x+dx[i]][y+dy[i]] >= 'a' && board[x+dx[i]][y+dy[i]] <= 'f') {
                            newKey = 1 << (board[x+dx[i]][y+dy[i]] - 'a');
                        }
                        int newSt = getState(x+dx[i], y+dy[i], (st & 0x3f) | newKey);
                        if (!states.containsKey(newSt)) {
//                            System.out.println(" "+(x+dx[i])+" "+(y+dy[i])+" "+(newSt & 0x3f));
                            // return
                            if ((newSt & 0x3f) == all_key)
                                return step+1;
                            states.put(newSt, step+1);
                            q.offer(newSt);
                        }
                    }
                }
                size--;
            }
            step++;
        }
        return -1;
    }

    public static void main(String[] args) {
        LH864 lh864 = new LH864();
        String[] test = {"@...a",".###A","b.BCc"};
        System.out.println(lh864.shortestPathAllKeys(test));
    }
}
