package practice_for_interview;

// Mock Two-sigma intern OA
// Problem 1

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Missing_words {

    // Work function
    public List<String> missingWords(String s, String t) {
        String[] srcs = s.split(" ");
        List<String> tars = Arrays.asList(t.split(" "));
        List<String> ans = new ArrayList<String>();
        for (String str: srcs) {
            if (!tars.contains(str)) {
                ans.add(str);
            }
        }
        return ans;
    }
}
