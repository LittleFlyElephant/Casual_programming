package oa;

import java.util.*;

public class WishOA {
    class Item{

    }
    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        String str;
        List<Integer> list = new ArrayList<>();
        Stack<Integer> stack;
        PriorityQueue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());
        StringBuilder builder = new StringBuilder();
        List<Item> items = new ArrayList<>();
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return 0;
            }
        });
    }
}
