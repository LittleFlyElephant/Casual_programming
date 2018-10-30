package practice_for_interview;

import java.util.*;

public class AllOne {

    class Node{
        Node prev, next;
        public Set<String> set;
        int val;
        Node(int val, String str) {
            set = new HashSet<>();
            set.add(str);
            prev = null;
            next = null;
            this.val = val;
        }
    }

    HashMap<String, Integer> map1 = new HashMap<>();
    HashMap<Integer, Node> map2 = new HashMap<>();

    Node head = null, tail = null;

    /** Initialize your data structure here. */
    public AllOne() {
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        if (map1.containsKey(key)) {
            Integer i = map1.get(key);
            Node node = map2.get(i);
            node.set.remove(key);
            map1.put(key, i+1);
            // insert
            if (map2.containsKey(i+1)) {
                Node n2 = map2.get(i+1);
                n2.set.add(key);
            } else {
                Node n2 = new Node(i+1, key);
                map2.put(i+1, n2);
                if (head == node) {
                    head.prev = n2;
                    n2.next = head;
                    head = n2;
                } else {
                    Node prev = node.prev;
                    prev.next = n2;
                    n2.prev = prev;
                    n2.next = node;
                    node.prev = n2;
                }
            }
            // delete
            if (node.set.size() == 0) {
                map2.remove(i);
                if (node == tail) {
                    tail = node.prev;
                    tail.next = null;
                } else {
                    Node next = node.next;
                    next.prev = node.prev;
                    node.prev.next = next;
                }
            }
        } else {
            map1.put(key, 1);
            if (map2.containsKey(1)) {
                Node node = map2.get(1);
                node.set.add(key);
            } else {
                Node node = new Node(1, key);
                map2.put(1, node);
                if (tail == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    node.prev = tail;
                    tail = node;
                }
            }
        }
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        if (map1.containsKey(key)) {
            int i = map1.get(key);
            Node node = map2.get(i);
            node.set.remove(key);
            if (i == 1) {
                map1.remove(key);
                // delete
                if (node.set.size() == 0) {
                    map2.remove(i);
                    if (head == tail) {
                        head = null;
                        tail = null;
                    } else {
                        tail = tail.prev;
                        tail.next = null;
                    }
                }
            } else {
                // insert
                map1.put(key, i-1);
                if (map2.containsKey(i-1)) {
                    Node n2 = map2.get(i-1);
                    n2.set.add(key);
                } else {
                    Node n2 = new Node(i-1, key);
                    map2.put(i-1, n2);
                    if (node == tail) {
                        node.next = n2;
                        n2.prev = node;
                        tail = n2;
                    } else {
                        Node next = node.next;
                        n2.prev = node;
                        n2.next = next;
                        node.next = n2;
                        next.prev = n2;
                    }
                }
                // delete
                if (node.set.size() == 0) {
                    map2.remove(i);
                    if (node == head) {
                        head = node.next;
                        head.prev = null;
                    } else {
                        Node prev = node.prev;
                        prev.next = node.next;
                        node.next.prev = prev;
                    }
                }
            }
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        if (head == null) return "";
        return head.set.iterator().next();
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if (tail == null) return "";
        return tail.set.iterator().next();
    }

    public static void main(String[] args) {
//        AllOne allOne = new AllOne();
//        allOne.inc("hello");
//        allOne.inc("world");
//        allOne.inc("leet");
//        allOne.inc("code");
//        allOne.inc("DS");
//        allOne.inc("leet");
//        System.out.println(allOne.getMaxKey());
//        allOne.inc("DS");
//        allOne.dec("leet");
//        System.out.println(allOne.getMaxKey());
//        allOne.dec("DS");
//        allOne.inc("hello");
//        System.out.println(allOne.getMaxKey());
//        allOne.inc("hello");
//        allOne.inc("hello");
//        allOne.dec("world");
//        allOne.dec("leet");
//        allOne.dec("code");
//        allOne.dec("DS");
//        System.out.println(allOne.getMaxKey());
//        allOne.inc("new");
//        allOne.inc("new");
//        allOne.inc("new");
//        allOne.inc("new");
//        allOne.inc("new");
//        System.out.println(allOne.getMaxKey());
//        System.out.println(allOne.getMinKey());
        String str = "123 ";
        System.out.println(str.trim());
    }
}
