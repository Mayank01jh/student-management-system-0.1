import model.Student;
import module.AttendanceTracker;
import module.GradeBook;
import module.SearchSort;
import module.StudentRegistry;
import util.FileManager;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final StudentRegistry   registry  = new StudentRegistry();
    private static final GradeBook         gradeBook = new GradeBook();
    private static final AttendanceTracker tracker   = new AttendanceTracker();
    private static final Scanner           sc        = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    Student Management System v1.0    ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Auto-load on startup
        FileManager.load(registry, gradeBook);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            System.out.println();
            switch (choice) {
                case 1  -> addStudent();
                case 2  -> removeStudent();
                case 3  -> findStudent();
                case 4  -> registry.displayAll();
                case 5  -> updateGrade();
                case 6  -> gradeBook.printLeaderboard();
                case 7  -> manageAttendance();
                case 8  -> sortStudents();
                case 9  -> searchStudents();
                case 10 -> registry.undoLastAction();
                case 11 -> FileManager.save(registry);
                case 0  -> running = false;
                default -> System.out.println("✗ Invalid option. Try again.");
            }
        }

        // Auto-save on exit
        FileManager.save(registry);
        System.out.println("Goodbye!");
        sc.close();
    }

    // ── Menu ─────────────────────────────────────────────────────────────────

    private static void printMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│             MAIN MENU               │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1.  Add Student                    │");
        System.out.println("│  2.  Remove Student                 │");
        System.out.println("│  3.  Find Student by Roll No.       │");
        System.out.println("│  4.  Display All Students           │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  5.  Update GPA                     │");
        System.out.println("│  6.  GPA Leaderboard (BST)          │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  7.  Attendance                     │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  8.  Sort Students                  │");
        System.out.println("│  9.  Search Students                │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  10. Undo Last Action (Stack)       │");
        System.out.println("│  11. Save to File                   │");
        System.out.println("│  0.  Exit                           │");
        System.out.println("└─────────────────────────────────────┘");
    }

    // ── Student Registry ─────────────────────────────────────────────────────

    private static void addStudent() {
        System.out.println("── Add Student ──");
        int    rollNo = readInt("Roll Number : ");
        String name   = readString("Name        : ");
        double gpa    = readDouble("GPA (0-10)  : ");
        String course = readString("Course      : ");

        registry.addStudent(rollNo, name, gpa, course);

        Student s = registry.findStudent(rollNo);
        if (s != null) gradeBook.register(s);
    }

    private static void removeStudent() {
        System.out.println("── Remove Student ──");
        int rollNo = readInt("Roll Number: ");
        gradeBook.remove(rollNo);
        registry.removeStudent(rollNo);
    }

    private static void findStudent() {
        System.out.println("── Find Student ──");
        int rollNo = readInt("Roll Number: ");
        Student s = registry.findStudent(rollNo);
        if (s != null) System.out.println("Found → " + s);
    }

    // ── Grade Book ───────────────────────────────────────────────────────────

    private static void updateGrade() {
        System.out.println("── Update GPA ──");
        int    rollNo = readInt("Roll Number: ");
        double newGpa = readDouble("New GPA    : ");
        gradeBook.updateGpa(rollNo, newGpa);

        Student s = registry.findStudent(rollNo);
        if (s != null) s.setGpa(newGpa);
    }

    // ── Attendance ───────────────────────────────────────────────────────────

    private static void manageAttendance() {
        System.out.println("── Attendance ──");
        System.out.println("  a. Start new session");
        System.out.println("  b. Mark student present");
        System.out.println("  c. Close & print session");
        System.out.println("  d. Student attendance report");
        System.out.println("  e. Full attendance report");
        String sub = readString("Choose: ");

        switch (sub.trim().toLowerCase()) {
            case "a" -> tracker.startSession();
            case "b" -> {
                int roll = readInt("Roll Number: ");
                tracker.markPresent(roll);
            }
            case "c" -> tracker.closeSession();
            case "d" -> {
                int roll = readInt("Roll Number: ");
                tracker.studentReport(roll);
            }
            case "e" -> tracker.fullReport(registry.getAllStudents());
            default  -> System.out.println("✗ Invalid option.");
        }
    }

    // ── Sort ─────────────────────────────────────────────────────────────────

    private static void sortStudents() {
        if (registry.isEmpty()) { System.out.println("No students to sort."); return; }

        System.out.println("── Sort By ──");
        System.out.println("  1. Name (A → Z)      — Merge Sort");
        System.out.println("  2. GPA  (High → Low) — Merge Sort");
        System.out.println("  3. Roll Number (Asc) — Merge Sort");
        int choice = readInt("Choose: ");

        List<Student> all = registry.getAllStudents();
        switch (choice) {
            case 1 -> SearchSort.printList("Sorted by Name", SearchSort.sortByName(all));
            case 2 -> SearchSort.printList("Sorted by GPA",  SearchSort.sortByGpa(all));
            case 3 -> {
                all.sort(Comparator.comparingInt(Student::getRollNo));
                SearchSort.printList("Sorted by Roll Number", all);
            }
            default -> System.out.println("✗ Invalid option.");
        }
    }

    // ── Search ───────────────────────────────────────────────────────────────

    private static void searchStudents() {
        if (registry.isEmpty()) { System.out.println("No students registered."); return; }

        System.out.println("── Search ──");
        System.out.println("  1. By Roll Number — Binary Search");
        System.out.println("  2. By Name        — Linear Search");
        int choice = readInt("Choose: ");

        List<Student> all = registry.getAllStudents();

        switch (choice) {
            case 1 -> {
                int rollNo = readInt("Roll Number: ");
                all.sort(Comparator.comparingInt(Student::getRollNo));
                Student found = SearchSort.binarySearchByRoll(all, rollNo);
                if (found != null) System.out.println("✓ Found → " + found);
                else               System.out.println("✗ Student with Roll# " + rollNo + " not found.");
            }
            case 2 -> {
                String query   = readString("Name (partial ok): ");
                List<Student> results = SearchSort.searchByName(all, query);
                SearchSort.printList("Results for \"" + query + "\"", results);
            }
            default -> System.out.println("✗ Invalid option.");
        }
    }

    // ── Input Helpers ─────────────────────────────────────────────────────────

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("✗ Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val < 0 || val > 10) {
                    System.out.println("✗ GPA must be between 0 and 10.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("✗ Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
