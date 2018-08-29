import org.junit.Before;
import org.junit.Test;
import practice_for_interview._Sub_strings;

import java.util.List;

public class Sub_strings_test {

    _Sub_strings sub_strings;

    @Before
    public void setup() {
        sub_strings = new _Sub_strings();
    }

    @Test
    public void test1() {
        _Sub_strings.findSubstrings("aab");
    }
}
