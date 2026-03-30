package ds;

import model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentLinkedList {

    private static class Node {
        Student data;
        Node next;
        Node(Student s) { this.data = s; }
    }

    private Node head;
    private int size;

    // Add to front — O(1)
    public void add(Student s) {
        Node node = new Node(s);
        node.next = head;
        head = node;
        size++;
    }

    // Remove by roll number — O(n)
    public Student remove(int rollNo) {
        if (head == null) return null;
        if (head.data.getRollNo() == rollNo) {
            Student removed = head.data;
            head = head.next;
            size--;
            return removed;
        }
        Node cur = head;
        while (cur.next != null) {
            if (cur.next.data.getRollNo() == rollNo) {
                Student removed = cur.next.data;
                cur.next = cur.next.next;
                size--;
                return removed;
            }
            cur = cur.next;
        }
        return null;
    }

    // Find by roll number — O(n)
    public Student find(int rollNo) {
        Node cur = head;
        while (cur != null) {
            if (cur.data.getRollNo() == rollNo) return cur.data;
            cur = cur.next;
        }
        return null;
    }

    public List<Student> toList() {
        List<Student> list = new ArrayList<>();
        Node cur = head;
        while (cur != null) { list.add(cur.data); cur = cur.next; }
        return list;
    }

    public int size()        { return size; }
    public boolean isEmpty() { return head == null; }
}
