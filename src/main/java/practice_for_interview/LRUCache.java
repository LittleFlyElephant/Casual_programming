package practice_for_interview;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache {

    class CacheNode{
        Integer key;
        Integer val;
        CacheNode next;
        CacheNode prev;

        CacheNode(Integer key, Integer val) {
            this.key = key;
            this.val = val;
        }
    }

//    Map<Integer, Integer> map = new HashMap<>();
    int cap;
    int count = 0;
    CacheNode head;
    CacheNode tail;

    public LRUCache(int capacity) {
        cap = capacity;
    }

    void insertNode(CacheNode node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    void deleteNode() {
        if (tail == head) {
            tail = null;
            head = null;
        } else {
            CacheNode node = tail.prev;
            node.next = null;
            tail = node;
        }
    }

    public int get(int key) {
        CacheNode tmp = head;
        while (tmp != null) {
            if (tmp.key == key) {
                return tmp.val;
            }
            tmp = tmp.next;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (count < cap) {
            count ++;
            insertNode(new CacheNode(key, value));
        } else {
            deleteNode();
            insertNode(new CacheNode(key, value));
        }
    }
}
