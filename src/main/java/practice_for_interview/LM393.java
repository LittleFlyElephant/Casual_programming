package practice_for_interview;

public class LM393 {
    private boolean isStartWithOneZero(int d) {
        return (d >> 6) == 2;
    }

    public boolean validUtf8(int[] data) {
        for (int i :
                data) {
            System.out.println(Integer.toBinaryString(i));
        }
        int i = 0;
        while (i < data.length) {
            int b3 = Integer.valueOf("11110", 2);
            int b2 = Integer.valueOf("1110", 2);
            int b1 = Integer.valueOf("110", 2);
            int base = Integer.valueOf("11111111", 2);
            if ((b3 & data[i]) == b3) {
                // 3-bytes
                if (i + 3 >= data.length) return false;
                if (!(isStartWithOneZero(base & data[i+1])
                        && isStartWithOneZero(base & data[i+2])
                        && isStartWithOneZero(base & data[i+3]))) {
                    return false;
                }
                i += 4;
            } else if ((b2 & data[i]) == b2) {
                if (i+2 >= data.length) return false;
                if (!(isStartWithOneZero(base & data[i+1])
                        && isStartWithOneZero(base & data[i+2]))){
                    return false;
                }
                i += 3;
            } else if ((b1 & data[i]) == b1) {
                if (i+1 >= data.length) return false;
                if (!isStartWithOneZero(base & data[i+1])){
                    return false;
                }
                i += 2;
            } else {
                if (((base & data[i]) >> 7) != 0) return false;
                i ++;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] t2 = {248,130,130,130};
//        int[] test = {240,162,138,147,145};
//        System.out.println((new LM393()).validUtf8(test));
        System.out.println((new LM393()).validUtf8(t2));
    }
}
