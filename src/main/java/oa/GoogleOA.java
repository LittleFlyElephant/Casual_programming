package oa;

import java.util.*;

public class GoogleOA {

    public String sample(String S, int K) {
        int currentCount = 0;
        boolean first = true;
        StringBuilder builder = new StringBuilder();
        StringBuilder currentBuilder = new StringBuilder();
        S = S.toUpperCase();
        for (int i=S.length()-1; i>=0; i--) {
            if (S.charAt(i) != '-') {
                currentCount++;
                currentBuilder.insert(0, S.charAt(i));
                if (currentCount == K) {
                    if (first) {
                        builder.append(currentBuilder.toString());
                        first = false;
                    } else {
                        builder.insert(0,"-");
                        builder.insert(0, currentBuilder.toString());
                    }
                    currentBuilder = new StringBuilder();
                    currentCount = 0;
                }
            }
        }
        if (currentBuilder.length() > 0) {
            builder.insert(0,"-");
            builder.insert(0, currentBuilder.toString());
        }
        return builder.toString();
    }

    public int p1(int[] A) {
        // write your code in Java SE 8
        ArrayList<Integer> tailList = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            // find a min tail queue that satisfies the requirement
            int findIndex = -1;
            for (int j = 0; j < tailList.size(); j++) {
                int tail = tailList.get(j);
                if (tail > A[i]) {
                    if (findIndex == -1 || tail < tailList.get(findIndex)) {
                        findIndex = j;
                    }
                }
            }
            if (findIndex >= 0) {
                tailList.set(findIndex, A[i]);
            } else {
                tailList.add(A[i]);
            }
        }
        return tailList.size();
    }

    public int[] p2(int[] stores, int[] houses) {
        // write your code in Java SE 8
        int m = stores.length, n = houses.length;
        int[] closestStores = new int[n];
        Arrays.sort(stores);
        for (int i = 0; i < n; i++) {
            int index = Arrays.binarySearch(stores, houses[i]);
            if (index >= 0) {
                // house and store in the same place
                closestStores[i] = houses[i];
            } else {
                index = - index - 1;
                if (index == 0) {
                    closestStores[i] = stores[0];
                } else if (index == m) {
                    closestStores[i] = stores[m-1];
                } else {
                    int l = houses[i] - stores[index-1];
                    int r = stores[index] - houses[i];
                    if (l <= r) {
                        closestStores[i] = stores[index-1];
                    } else {
                        closestStores[i] = stores[index];
                    }
                }
            }
        }
        return closestStores;
    }


    public static void main(String[] args) {
        int[] test = {-2, 3, 3, 3, 5, 9};
        System.out.println(Arrays.binarySearch(test, 3));
    }
}
