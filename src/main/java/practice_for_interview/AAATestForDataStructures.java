package practice_for_interview;

import java.util.*;

public class AAATestForDataStructures {
    public static void main(String[] args) {
        //Set
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        //Deque
        Deque<Integer> deque = new ArrayDeque<>();
//        deque.getFirst();
//        deque.getLast();
//        deque.pollFirst();
//        deque.peekLast();
        //String
        String str = "Abc12";
        str.toLowerCase();
        System.out.println(str.substring(1,2));
        String str2 = " 12 34 45 1  ";
        System.out.println(str2.trim());
        //StringBuilder
        StringBuilder builder = new StringBuilder();
        builder.insert(0,1);
        //Character
//        System.out.println(Character.isLetterOrDigit());
        //Long
        long a = Long.parseLong("123");
        //Integer
        int i = Integer.valueOf("0101", 2);
        Integer i2 = 30;
        Integer.toBinaryString(i2);
        //Set
        Set<Integer> set = new HashSet<>();
        set.contains(1);
        //Queue
        Queue<Integer> q = new LinkedList<>();
        q.clear();
        //Collections
        List<Integer> list = new ArrayList<>();
        Collections.sort(list, ((o1, o2) -> o1-o2));
        //Arrays
        int[] ar = {1, 3, 4, 6, 8};
        System.out.println(Arrays.binarySearch(ar, 7));
        //PQ
        PriorityQueue<Double> pq = new PriorityQueue<>((o1, o2) -> Double.compare(Math.abs(o2-1.0), Math.abs(o1-1.0)));
        //List
        List<Integer> list1 = new ArrayList<>();
        list1.add(0, new Integer(1));
        //Double
        Double d = 1.0;
        d.intValue();
    }
}
