package oa_test;

import oa.GoogleOA;
import org.junit.Test;

public class GoogleTest {

    private GoogleOA oa = new GoogleOA();

    @Test
    public void t1() {
        int[] t1 = {5, 4, 3, 6, 1};
        System.out.println(oa.p1(t1));
        int[] t2 = {7, 6, 4, 6};
        System.out.println(oa.p1(t2));
        int[] t3 = {8, 6, 5, 4, 3};
        System.out.println(oa.p1(t3));
        int[] t4 = {1, 2, 3, 4, 5};
        System.out.println(oa.p1(t4));
    }
}
