package module;

import model.Student;
import java.time.LocalDate;
import java.util.*;

public class AttendanceTracker {

    // Each session date maps to a Queue of present roll numbers
    private final Map<LocalDate, Queue<Integer>> sessions = new LinkedHashMap<>();

    // Running record: rollNo → list of dates present
    private final Map<Integer, List<LocalDate>> record = new HashMap<>();

    // ── Start a new session for today ────────────────────
    public void startSession() {
        LocalDate today = LocalDate.now();
        sessions.putIfAbsent(today, new LinkedList<>());
        System.out.println("✓ Attendance session started for " + today);
    }

    // ── Mark a student present ───────────────────────────
    public void markPresent(int rollNo) {
        LocalDate today = LocalDate.now();
        Queue<Integer> q = sessions.get(today);
        if (q == null) { System.out.println("✗ No open session. Start a session first."); return; }
        if (q.contains(rollNo)) { System.out.println("⚠ Roll# " + rollNo + " already marked."); return; }
        q.offer(rollNo);
        record.computeIfAbsent(rollNo, k -> new ArrayList<>()).add(today);
        System.out.println("✓ Marked present: Roll# " + rollNo);
    }

    // ── Process / close session (dequeue all) ────────────
    public void closeSession() {
        LocalDate today = LocalDate.now();
        Queue<Integer> q = sessions.get(today);
        if (q == null || q.isEmpty()) { System.out.println("No open session or no attendees."); return; }
        System.out.println("\n── Attendance for " + today + " ──");
        int count = q.size();
        while (!q.isEmpty()) System.out.println("  Roll# " + q.poll());
        System.out.println("Total present: " + count);
    }

    // ── Attendance report for a student ──────────────────
    public void studentReport(int rollNo) {
        List<LocalDate> dates = record.getOrDefault(rollNo, Collections.emptyList());
        int totalSessions = sessions.size();
        System.out.printf("%nRoll# %d — Present: %d / %d sessions%n", rollNo, dates.size(), totalSessions);
        dates.forEach(d -> System.out.println("  " + d));
    }

    // ── Full report for all students ─────────────────────
    public void fullReport(List<Student> students) {
        System.out.println("\n── Full Attendance Report ──");
        int total = sessions.size();
        for (Student s : students) {
            int present = record.getOrDefault(s.getRollNo(), Collections.emptyList()).size();
            double pct  = total == 0 ? 0 : (present * 100.0 / total);
            System.out.printf("  %-20s | %d/%d (%.1f%%)%n", s.getName(), present, total, pct);
        }
    }

    public int getTotalSessions() { return sessions.size(); }
}
