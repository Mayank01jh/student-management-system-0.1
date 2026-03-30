package ds;

import model.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentBST {

    private static class Node {
        Student data;
        Node left, right;
        Node(Student s) { this.data = s; }
    }

    private Node root;

    public void insert(Student s) { root = insert(root, s); }

    private Node insert(Node node, Student s) {
        if (node == null) return new Node(s);
        if (s.getGpa() > node.data.getGpa())
            node.left  = insert(node.left,  s);
        else
            node.right = insert(node.right, s);
        return node;
    }

    public void delete(int rollNo) { root = delete(root, rollNo); }

    private Node delete(Node node, int rollNo) {
        if (node == null) return null;
        if (rollNo == node.data.getRollNo()) {
            if (node.left  == null) return node.right;
            if (node.right == null) return node.left;
            Node minNode = findMin(node.right);
            node.data  = minNode.data;
            node.right = delete(node.right, minNode.data.getRollNo());
        } else if (node.data.getRollNo() > rollNo) {
            node.left  = delete(node.left,  rollNo);
        } else {
            node.right = delete(node.right, rollNo);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // In-order = ascending GPA; reverse = leaderboard (highest first)
    public List<Student> getLeaderboard() {
        List<Student> result = new ArrayList<>();
        inOrder(root, result);
        Collections.reverse(result);
        return result;
    }

    private void inOrder(Node node, List<Student> result) {
        if (node == null) return;
        inOrder(node.left,  result);
        result.add(node.data);
        inOrder(node.right, result);
    }

    public boolean isEmpty() { return root == null; }
}
