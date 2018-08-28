import org.junit.Before;
import org.junit.Test;
import practice_for_interview.Missing_words;

import java.util.List;

public class Missing_words_test {

    Missing_words missing_words;

    @Before
    public void setup() {
        missing_words = new Missing_words();
    }

    @Test
    public void test1() {
        List<String> ans = missing_words.missingWords("I am using hackerrank to improve programming", "am hackerrank improve programming");
        for (String s: ans) {
            System.out.println(s);
        }
    }
}
