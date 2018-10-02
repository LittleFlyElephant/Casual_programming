package practice_for_interview;

public class LH76 {

    boolean checkOver(int[] tCharCount, int[] currentCount) {
        for (int i = 0; i < 26; i++) {
            if (currentCount[i] < tCharCount[i]) {
                return false;
            }
        }
        return true;
    }

    public String minWindow(String s, String t) {
        if (s == null || t == null) return "";
        // initial varibles
        int l = 0, r = 0, ansL = 0, ansR = 0;
        int[] tCharCount = new int[26];
        // two char arrays
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        int sLen = sArray.length, tLen = tArray.length;
        // count chars in t
        for (char c: tArray) {
            tCharCount[c - 'A']++;
        }
        int[] currentCount = new int[26];
        int minLen = Integer.MAX_VALUE;
        // linear scan
        while (r < sLen) {
            currentCount[sArray[r] - 'A']++;
            boolean over = false;
            while (checkOver(tCharCount, currentCount)) {
                over = true;
                currentCount[sArray[l++] - 'A']--;
            }
            if (over) {
                l--;
                currentCount[sArray[l] - 'A']++;
                if (r-l+1 < minLen) {
                    minLen = r-l+1;
                    ansL = l;
                    ansR = r;
                }
            }
            r++;
        }
        if (minLen == Integer.MAX_VALUE) {
            return "";
        } else {
            return s.substring(ansL, ansR+1);
        }
    }

    public static void main(String[] args) {
        LH76 lh76 = new LH76();
        System.out.println(lh76.minWindow("ADOBECODEBANC", "ABC"));
    }
}
