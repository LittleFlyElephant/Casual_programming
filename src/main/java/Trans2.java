import java.util.Arrays;
import java.util.List;

public class Trans2 {

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
            while (tmp >= 0 && arr[tmp][y+1] == blank) tmp--;
            if (tmp < 0) {
                y--;
                if (arr[x][y] == blank) y--;
                return new Point(x, y);
            } else {
                return new Point(tmp, y+1);
            }
        }
        if (shift_down.contains(y)) {
            int tmp = x+1;
            while (tmp < size && arr[tmp][y+1] == blank) tmp++;
            if (tmp == size) {
                y--;
                if (y == 8){
                    return new Point(size-9, y);
                }
                if (arr[x][y] == blank) y--;
                return new Point(x, y);
            } else {
                return new Point(tmp, y+1);
            }
        }
        return new Point(x, y-1);
    }

    int[] get_qr_graph(int[][] arr, int size) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                arr[i][j] = blank;
                arr[i][size - j - 1] = blank;
                arr[size - i - 1][j] = blank;
            }
        }
        for (int i = 0; i < 8; i++) {
            arr[i][8] = blank;
            arr[size-1-i][8] = blank;
        }
        if (size == 25) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    arr[16+i][16+j] = blank;
                }
            }
        }
        for (int i = 0; i < size-8*2; i++) {
            arr[6][8+i] = blank;
            arr[8+i][6] = blank;
        }
        int px = size-1;
        int py = size-1;
        int len = 0;
        for (int i = 0; i < 8; i++) {
            int num = arr[px][py] & 1;
            len = len + (num << (7-i));
            Point p = get_next(arr, px, py, size);
            px = p.x;
            py = p.y;
        }
        int[] ret = new int[len*2+1];
        ret[0] = len;
        for (int i = 0; i < len * 2; i++) {
            int d = 0;
            for (int j = 0; j < 8; j++) {
                int num = arr[px][py] & 1;
                d = d + (num << (7-j));
                Point p = get_next(arr, px, py, size);
                px = p.x;
                py = p.y;
            }
            ret[i+1] = d;
        }
        return ret;
    }
}
