package practice_for_interview;

import java.util.*;

class Master{
    public int guess(String word) {
        return -1;
    }
}

public class LH843 {

    int match(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public void findSecretWord(String[] wordlist, Master master) {
        Set<String> strSet = new HashSet<>();
        strSet.addAll(Arrays.asList(wordlist));
        while (!strSet.isEmpty()) {
            String candidate = strSet.iterator().next();
            int val = master.guess(candidate);
            if (val == -1) val = 0;
            if (val == 6) {
                return;
            } else {
                for (Iterator<String> it = strSet.iterator(); it.hasNext();) {
                    String str = it.next();
                    if (match(str, candidate) != val) {
                        strSet.remove(str);
                    }
                }
            }
        }
//        Objects.equals()
    }
}
