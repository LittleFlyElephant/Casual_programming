package practice_for_interview;

// Two-sigma intern OA
// Problem 2
// String

public class _Sub_strings {
    static char[] vowels = {'a', 'e', 'i', 'o', 'u'};

    static boolean isVowel(char c) {
        for (int i = 0; i < vowels.length; i++) {
            if (vowels[i] == c) return true;
        }
        return false;
    }

    static int findMin(char[] arr, int x) {
        int i = x;
        while (i < arr.length && isVowel(arr[i])) i++;
        return i;
    }

    static int findMax(char[] arr, int x) {
        int i = arr.length - 1;
        while (i > x && isVowel(arr[i])) i--;
        return i;
    }

    static boolean bigger(char[] arr, int l1, int r1, int l2, int r2) {
        while (l1 <= r1 && l2 <= r2) {
            if (arr[l1] != arr[l2]) return (arr[l1] > arr[l2]);
            l1 ++;
            l2 ++;
        }
        return (l2 == r2 + 1);
    }

    // Complete the findSubstrings function below.
    public static void findSubstrings(String s) {
        char[] arr = s.toCharArray();
        int minl = -1, maxl = -1, minr = -1, maxr = -1;
        int tmp_l = -1, tmp_r = -1;
        for (int i = 0; i < arr.length; i++) {
            if (isVowel(arr[i])) {
                //
                if (minl == -1) {
                    minl = i;
                    minr = findMin(arr, i);
                } else {
                    tmp_l = i;
                    tmp_r = findMin(arr, i);
                    if (tmp_r == arr.length) continue;
                    if (!bigger(arr, tmp_l, tmp_r, minl, minr)) {
                        minl = tmp_l;
                        minr = tmp_r;
                    }
                }
                //
                if (maxl == -1) {
                    maxl = i;
                    maxr = findMax(arr, i);
                } else {
                    tmp_l = i;
                    tmp_r = findMax(arr, i);
                    if (tmp_r == i) continue;
                    if (bigger(arr, tmp_l, tmp_r, maxl, maxr)) {
                        maxl = tmp_l;
                        maxr = tmp_r;
                    }
                }
            }
        }
        System.out.println(s.substring(minl, minr + 1));
        System.out.println(s.substring(maxl, maxr + 1));
    }
}
