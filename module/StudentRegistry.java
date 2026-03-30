package module;

import ds.ActionStack;
import ds.StudentLinkedList;
import model.Student;
import java.util.List;

public class StudentRegistry {

    private final StudentLinkedList list = new StudentLinkedList();
    private final ActionStack       undo = new ActionStack();

    // ── Add ──────────────────────────────────────────────
    public void addStudent(int rollNo, String name, double gpa, String course) {
        if (list.find(rollNo) != null) {
            System.out.println("✗ Student with Roll# " + rollNo + " already exists.");
            return;
        }
        Student s = new Student(rollNo, name, gpa, course);
        list.add(s);
        undo.push("ADD", s);
        System.out.println("✓ Added: " + s);
    }

    // ── Remove ───────────────────────────────────────────
    public void removeStudent(int rollNo) {
        Student removed = list.remove(rollNo);
        if (removed == null) {
            System.out.println("✗ Student Roll# " + rollNo + " not found.");
            return;
        }
        undo.push("REMOVE", removed);
        System.out.println("✓ Removed: " + removed);
    }

    // ── Find ─────────────────────────────────────────────
    public Student findStudent(int rollNo) {
        Student s = list.find(rollNo);
        if (s == null) System.out.println("✗ No student with Roll# " + rollNo);
        return s;
    }

    // ── Display All ──────────────────────────────────────
    public void displayAll() {
        if (list.isEmpty()) { System.out.println("No students registered."); return; }
        System.out.println("\n── Student Roster (" + list.size() + " students) ──");
        list.toList().forEach(System.out::println);
    }

    // ── Undo ─────────────────────────────────────────────
    public void undoLastAction() {
        if (undo.isEmpty()) { System.out.println("Nothing to undo."); return; }
        ActionStack.Action action = undo.pop();
        if (action.type.equals("ADD")) {
            list.remove(action.student.getRollNo());
            System.out.println("↩ Undid ADD — removed: " + action.student);
        } else {
            list.add(action.student);
            System.out.println("↩ Undid REMOVE — restored: " + action.student);
        }
    }

    public List<Student> getAllStudents() { return list.toList(); }
    public boolean isEmpty()             { return list.isEmpty(); }
}
