package module;

import ds.StudentBST;
import model.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeBook {

    // HashMap for O(1) lookup: rollNo → Student
    private final Map<Integer, Student> gradeMap = new HashMap<>();

    // BST for sorted leaderboard by GPA
    private final StudentBST bst = new StudentBST();

    // ── Register student in grade book ───────────────────
    public void register(Student s) {
        if (gradeMap.containsKey(s.getRollNo())) return;
        gradeMap.put(s.getRollNo(), s);
        bst.insert(s);
    }

    // ── Update GPA ───────────────────────────────────────
    public void updateGpa(int rollNo, double newGpa) {
        Student s = gradeMap.get(rollNo);
        if (s == null) { System.out.println("✗ Roll# " + rollNo + " not in grade book."); return; }
        bst.delete(rollNo);
        s.setGpa(newGpa);
        bst.insert(s);
        System.out.printf("✓ Updated Roll# %d GPA → %.2f%n", rollNo, newGpa);
    }

    // ── Get single student grade ──────────────────────────
    public void printGrade(int rollNo) {
        Student s = gradeMap.get(rollNo);
        if (s == null) { System.out.println("✗ Roll# " + rollNo + " not in grade book."); return; }
        System.out.println(s);
    }

    // ── Leaderboard (BST in-order, highest GPA first) ────
    public void printLeaderboard() {
        List<Student> board = bst.getLeaderboard();
        if (board.isEmpty()) { System.out.println("Grade book is empty."); return; }
        System.out.println("\n── GPA Leaderboard ──");
        int rank = 1;
        for (Student s : board) {
            System.out.printf("%2d. %s%n", rank++, s);
        }
    }

    // ── Remove from grade book ───────────────────────────
    public void remove(int rollNo) {
        if (gradeMap.remove(rollNo) != null) bst.delete(rollNo);
    }

    public List<Student> getLeaderboard() { return bst.getLeaderboard(); }
    public boolean contains(int rollNo)   { return gradeMap.containsKey(rollNo); }
    public boolean isEmpty()              { return gradeMap.isEmpty(); }
}
