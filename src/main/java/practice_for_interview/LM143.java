package practice_for_interview;

import util.ListNode;

public class LM143 {
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        ListNode mid = head, j = head, pre = null;
        while (j.next != null && j.next.next != null) {
            j = j.next.next;
            ListNode tmp = pre;
            pre = mid;
            mid = mid.next;
            pre.next = tmp;
        }
        ListNode hold = mid;
        if (j.next != null) {
            mid = mid.next;
        }
        while (pre != null) {
            ListNode tmp = pre.next;
            pre.next = mid.next;
            mid.next = mid.next.next;
            pre.next.next = hold;
            hold = pre;
            pre = tmp;
        }
    }
}
