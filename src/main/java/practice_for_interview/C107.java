package practice_for_interview;

public class C107 {
    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0;
        while (i < name.length() && j < typed.length()) {
            if (name.charAt(i) != typed.charAt(j)) {
                return false;
            }
            if ((i+1 < name.length() && name.charAt(i+1) != name.charAt(i)) || i+1 == name.length())  {
                // a new char
                while (j+1 < typed.length() && typed.charAt(j+1) == typed.charAt(j)) {
                    j++;
                }
            }
            i++;
            j++;
        }
        if (i < name.length() || j < typed.length()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();

        System.out.println((new C107()).isLongPressedName("alex", "aaleex"));
    }
}
