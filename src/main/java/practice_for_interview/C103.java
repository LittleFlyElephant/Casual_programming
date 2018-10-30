package practice_for_interview;

public class C103 {
    public String reverseOnlyLetters(String S) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<S.length(); i++) {
            if (Character.isLetter(S.charAt(i))) {
                builder.append(S.charAt(i));
            }
        }
        builder.reverse();
        String ans = "";
        int j = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '-') {
                ans += S.charAt(i);
            } else {
                ans += builder.charAt(j++);
            }
        }
        return ans;
    }
}
