package module;

import model.Student;
import java.util.*;

public class SearchSort {

    // ── Merge Sort by Name (A→Z) — O(n log n) ────────────
    public static List<Student> sortByName(List<Student> list) {
        if (list.size() <= 1) return new ArrayList<>(list);
        int mid = list.size() / 2;
        List<Student> left  = sortByName(list.subList(0, mid));
        List<Student> right = sortByName(list.subList(mid, list.size()));
        return mergeByName(left, right);
    }

    private static List<Student> mergeByName(List<Student> l, List<Student> r) {
        List<Student> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < l.size() && j < r.size()) {
            if (l.get(i).getName().compareToIgnoreCase(r.get(j).getName()) <= 0)
                result.add(l.get(i++));
            else
                result.add(r.get(j++));
        }
        while (i < l.size()) result.add(l.get(i++));
        while (j < r.size()) result.add(r.get(j++));
        return result;
    }

    // ── Merge Sort by GPA (high→low) — O(n log n) ────────
    public static List<Student> sortByGpa(List<Student> list) {
        if (list.size() <= 1) return new ArrayList<>(list);
        int mid = list.size() / 2;
        List<Student> left  = sortByGpa(list.subList(0, mid));
        List<Student> right = sortByGpa(list.subList(mid, list.size()));
        return mergeByGpa(left, right);
    }

    private static List<Student> mergeByGpa(List<Student> l, List<Student> r) {
        List<Student> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < l.size() && j < r.size()) {
            if (l.get(i).getGpa() >= r.get(j).getGpa())
                result.add(l.get(i++));
            else
                result.add(r.get(j++));
        }
        while (i < l.size()) result.add(l.get(i++));
        while (j < r.size()) result.add(r.get(j++));
        return result;
    }

    // ── Binary Search by Roll Number — O(log n) ──────────
    // List must be sorted ascending by rollNo before calling
    public static Student binarySearchByRoll(List<Student> sorted, int rollNo) {
        int lo = 0, hi = sorted.size() - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cur = sorted.get(mid).getRollNo();
            if      (cur == rollNo) return sorted.get(mid);
            else if (cur <  rollNo) lo = mid + 1;
            else                    hi = mid - 1;
        }
        return null;
    }

    // ── Linear Search by Name (partial match) — O(n) ─────
    public static List<Student> searchByName(List<Student> list, String query) {
        List<Student> results = new ArrayList<>();
        String q = query.toLowerCase();
        for (Student s : list) {
            if (s.getName().toLowerCase().contains(q)) results.add(s);
        }
        return results;
    }

    // ── Print helpers ─────────────────────────────────────
    public static void printList(String title, List<Student> list) {
        System.out.println("\n── " + title + " ──");
        if (list.isEmpty()) { System.out.println("  (no results)"); return; }
        list.forEach(s -> System.out.println("  " + s));
    }
}
