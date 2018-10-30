package oa;

public class TwitterOA {

    public static String decode(String encoded) {
        // Write your code here
        StringBuilder builder = new StringBuilder(encoded);
        return builder.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println((int)'a');
        String[] sp = "1\t\n\t1".split("\t");
        System.out.println(sp.length);
    }
}
