package practice_for_interview.locked_problems;

public class LH269 {

    static int size = 26;

    // Structure to maintain the order
    boolean[][] map = new boolean[size][size];
    boolean[] letters = new boolean[size];

    // Retrive the answer
    String getAns() {
        String ans = "";
        boolean[] tmp = new boolean[size];
        int num = 0;
        for (int i=0; i<size; i++) {
            if (letters[i]) num ++;
        }
        while (num > 0) {
            for (int i=0; i<size; i++) tmp[i] = letters[i];
            for (int i=0; i<size; i++) {
                if (tmp[i]) {
                    for (int j=0; j<size; j++) {
                        if (letters[j] && map[j][i]) {
                            tmp[i] = false;
                            break;
                        }
                    }
                }
            }
            for (int i=0; i<size; i++) {
                if (tmp[i]) {
                    num --;
                    letters[i] = false;
                    ans = ans + (char)('a'+i);
                }
            }
        }
        return ans;
    }

    // Dfs check circle
    boolean dfs(int x, int[] vis) {
        vis[x] = 1;
        for (int i=0; i<size; i++) {
            if (map[x][i]) {
                if (vis[i] == vis[x]) return true;
                else if (vis[i] == 0) {
                    if (dfs(i, vis)) return true;
                }
            }
        }
        vis[x] = -1;
        return false;
    }

    // Add relation(a->b) to WordTree
    boolean buildRelation(char a, char b) {
        map[a-'a'][b-'a'] = true;
        // Check circle
        int[] vis = new int[size];
        for (int i=0; i<size; i++) {
            if (dfs(i, vis)) return false;
        }
        return true;
    }

    boolean comp(String[] words, int l, int r, int x) {
        // Return if valid
        if (l >= r) return true;
        int j = l, i = l;
        while (i <= r) {
            if (x == words[i].length()) {
                j++;
                i++;
            } else {
                while (i<=r && words[i].charAt(x) == words[j].charAt(x)) i++;
                if (i == r+1) break;
                if (!comp(words, j, i-1, x+1)) {
                    return false;
                }
                if (!buildRelation(words[j].charAt(x), words[i].charAt(x))) {
                    return false;
                }
                j=i;
            }
        }
        return comp(words, j, i-1, x+1);
    }

    public String alienOrder(String[] words) {
        for (int i=0; i<words.length; i++) {
            for (int j=0; j<words[i].length(); j++) {
                letters[words[i].charAt(j)-'a'] = true;
            }
        }
        if (comp(words, 0, words.length-1, 0)) {
            return getAns();
        } else {
            return "";
        }
    }
}
