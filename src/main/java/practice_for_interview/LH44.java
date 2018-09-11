package practice_for_interview;

public class LH44 {

    int[][] match;
    int lenS, lenP;

    int isMatch(char[] s, char[] p, int x, int y) {
        if (match[x][y] != 0) return match[x][y];
        // boundary
        if (x == 0) {
            if (p[y-1] == '*') {
                match[x][y] = isMatch(s, p, x, y-1);
            } else {
                match[x][y] = -1;
            }
            return match[x][y];
        } else {
            match[x][y] = -1;
            switch (p[y-1]){
                case '*':{
                    for (int i=0; i<=x; i++) {
                        if (isMatch(s, p, i, y-1) == 1) {
                            match[x][y] = 1;
                        }
                    }
                    break;
                }
                case '?':{
                    match[x][y] = isMatch(s, p, x-1, y-1);
                    break;
                }
                default:{
                    if (s[x-1] == p[y-1]) {
                        match[x][y] = isMatch(s, p, x-1, y-1);
                    }
                    break;
                }
            }
            return match[x][y];
        }
    }

    public boolean isMatch(String s, String p) {
        lenS = s.length();
        lenP = p.length();
        // add 0 as boundary
        match = new int[lenS+1][lenP+1];
        match[0][0] = 1;
        for (int i=0; i<lenS; i++) {
            match[i+1][0] = -1;
        }
        return isMatch(s.toCharArray(), p.toCharArray(), lenS, lenP) == 1?true:false;
    }
}
