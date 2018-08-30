package practice_for_interview;

import util.Interval;

import java.util.Arrays;
import java.util.Comparator;

public class LM253 {

    int heap_len;
    Interval[] heap;

    void down(int x) {
        int son = -1;
        if (x*2+1 < heap_len) {
            son = x*2+1;
        }
        if (x*2+2 < heap_len) {
            if (heap[x*2+2].end < heap[x*2+1].end) {
                son = x*2+2;
            }
        }
        if (son > -1 && heap[x].end > heap[son].end) {
            Interval tmp = heap[x];
            heap[x] = heap[son];
            heap[son] = tmp;
            down(son);
        }
    }

    void up(int x) {
        if (x == 0) return;
        int f = (x-1)/2;
        if (heap[x].end < heap[f].end) {
            Interval tmp = heap[x];
            heap[x] = heap[f];
            heap[f] = tmp;
            up((x-1)/2);
        }
    }

    void delete_heap() {
        heap[0] = heap[heap_len-1];
        heap_len --;
        down(0);
    }

    void insert_heap(Interval node) {
        heap[heap_len ++] = node;
        up(heap_len - 1);
    }

    public int minMeetingRooms(Interval[] intervals) {
        // Sort first
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval o1, Interval o2) {
                return o1.start - o2.start;
            }
        });
        // Heap
        heap = new Interval[intervals.length];
        heap_len = 0;
        int ans = 0;
        for (int i = 0; i < intervals.length; i++) {
            while (heap_len>0 && heap[0].end < intervals[i].start) {
                delete_heap();
            }
            insert_heap(intervals[i]);
            ans = Math.max(ans, heap_len);
        }
        return ans;
    }

    public static void main(String[] args) {
        Interval[] intervals = new Interval[2];
        intervals[0] = new Interval(7, 10);
        intervals[1] = new Interval(2, 4);
        System.out.println((new LM253()).minMeetingRooms(intervals));
    }
}
