import java.util.*;

public class Trans {

    List<Integer> shift_up = Arrays.asList(2, 7, 11, 15, 19, 23);
    List<Integer> shift_down = Arrays.asList(0, 4, 9, 13, 17, 21);
    int blank = 2;
    int n1 = Integer.valueOf("11101100", 2);
    int n2 = Integer.valueOf("00010001", 2);

    class Point{
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    Point get_next(int[][] arr, int x, int y, int size){
        if (shift_up.contains(y)) {
            int tmp = x-1;
            while (tmp >= 0 && arr[tmp][y+1] != blank) tmp--;
            if (tmp < 0) {
                y--;
                if (arr[x][y] != blank) y--;
                return new Point(x, y);
            } else {
                return new Point(tmp, y+1);
            }
        }
        if (shift_down.contains(y)) {
            int tmp = x+1;
            while (tmp < size && arr[tmp][y+1] != blank) tmp++;
            if (tmp == size) {
                y--;
                if (y == 8){
                    return new Point(size-9, y);
                }
                if (arr[x][y] != blank) y--;
                return new Point(x, y);
            } else {
                return new Point(tmp, y+1);
            }
        }
        return new Point(x, y-1);
    }

    int[][] get_qr_graph(int[] payload, int size) {
        int[][] arr = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(arr, blank);
        }
        int[][] position = {
                {1, 1, 1, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 1, 0},
                {1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1, 0, 1, 0},
                {1, 0, 0, 0, 0, 0, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                arr[i][j] = position[i][j];
                arr[i][size - j - 1] = position[i][j];
                arr[size - i - 1][j] = position[i][j];
            }
        }
        int[][] align = {{1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };
        for (int i = 0; i < 8; i++) {
            arr[i][8] = 0;
            arr[size-1-i][8] = 0;
        }
        if (size == 25) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    arr[16+i][16+j] = align[i][j];
                }
            }
        }
        for (int i = 0; i < size-8*2; i++) {
            arr[6][8+i] = (i+1) % 2;
            arr[8+i][6] = (i+1) % 2;
        }
        int px = size-1;
        int py = size-1;
        for (int data: payload){
            for (int i = 0; i < 8; i++) {
                arr[px][py] = (data >> (7-i)) & 1;
                Point p = get_next(arr, px, py, size);
                px = p.x;
                py = p.y;
            }
        }
        while (true) {
            if (py < 0) break;
            for (int i = 0; i < 8; i++) {
                if (py < 0) break;
                arr[px][py] = (n1 >> (7-i)) & 1;
                Point p = get_next(arr, px, py, size);
                px = p.x;
                py = p.y;
            }
            for (int i = 0; i < 8; i++) {
                if (py < 0) break;
                arr[px][py] = (n2 >> (7-i)) & 1;
                Point p = get_next(arr, px, py, size);
                px = p.x;
                py = p.y;
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arr[i][j] == blank) arr[i][j] = 0;
            }
        }
        return arr;
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();
        List<String> ans = new ArrayList<>();
        for (String word: words) {
            int val = map.getOrDefault(word, 0);
            map.put(word, val + 1);
            if (val == 0) {
                ans.add(word);
            }
        }
        ans.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int t1 = map.get(o1);
                int t2 = map.get(o2);
                if (t1 != t2) return t2 - t1;
                return o2.compareTo(o1);
            }
        });
        return ans.subList(0, k);
    }

    public static void main(String[] args) {
        int[] initial = {1,2,3};
        for (int i=0; i<initial.length; i++) {

        }
    }
}
