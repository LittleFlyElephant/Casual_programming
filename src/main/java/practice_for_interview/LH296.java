package practice_for_interview;

public class LH296 {

    boolean[][] map = new boolean[26][26];
    boolean[] vis2 = new boolean[26];
    boolean[] appearLetter = new boolean[26];

    void addToMap(String be, String af) {
        int i = 0, j = 0;
        while (i < be.length() && j < af.length()) {
            char c1 = be.charAt(i), c2 = af.charAt(j);
            if (c1 == c2) {
                i++;
                j++;
            } else {
                map[c1-'a'][c2-'a'] = true;
                break;
            }
        }
    }

    // check circle
    boolean dfs(int x, boolean[] vis) {
        vis[x] = true;
        vis2[x] = true;
        for (int i=0; i<26; i++) {
            if (map[x][i]) {
                if (vis[i]) {
                    return false;
                } else if (!dfs(i, vis)) {
                    return false;
                }
            }
        }
        vis[x] = false;
        return true;
    }

    public String alienOrder(String[] words) {
        // 1. use words to build a char relation graph
        //   if a->b, it means a is lexcographically smaller than b
        // 2. check if exist a circle
        // 3. topological sort to get the order
        int len = words.length;
        // set appear letter
        for (String str: words) {
            for (int i=0; i<str.length(); i++) {
                appearLetter[str.charAt(i)-'a'] = true;
            }
        }
        for (int i=1; i<len; i++) {
            // compare w[i] and w[i-1]
            addToMap(words[i-1], words[i]);
        }
        // check circle
        for (int i=0; i<26; i++) {
            if (!vis2[i]) {
                boolean[] vis = new boolean[26];
                if (!dfs(i, vis)) {
                    // invalid
                    return "";
                }
            }
        }
        // topological sort
        String ans = "";
        boolean hasNext = false;
        do{
            hasNext = false;
            // calculate in-degree
            int[] count = new int[26];
            for (int i=0; i<26; i++) {
                for (int j=0; j<26; j++) {
                    if (map[i][j]) {
                        count[j]++;
                    }
                }
            }
            for (int i=0; i<26; i++) {
                // no in-degree
                if (count[i] == 0) {
                    hasNext = true;
                    ans += (char)(i+'a');
                    appearLetter[i] = false;
                    // clear out edge
                    for (int j=0; j<26; j++) {
                        map[i][j] = false;
                    }
                }
            }
        } while (hasNext);
        for (int i=0; i<26; i++) {
            if (appearLetter[i]) {
                ans += (char)(i+'a');
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String[] words = {"wrt","wrf","er","ett","rftt"};
        System.out.println((new LH296()).alienOrder(words));
    }
}
