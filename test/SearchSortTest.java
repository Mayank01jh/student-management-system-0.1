package test;

import model.Student;
import module.SearchSort;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SearchSort Tests")
public class SearchSortTest {

    private List<Student> students;

    @BeforeEach
    void setUp() {
        students = new ArrayList<>();
        students.add(new Student(103, "Carol", 9.1, "CS"));
        students.add(new Student(101, "Alice", 7.5, "IT"));
        students.add(new Student(105, "Eve",   8.0, "CS"));
        students.add(new Student(102, "Bob",   6.3, "IT"));
        students.add(new Student(104, "Dave",  8.8, "CS"));
    }

    // ── Sort by Name ──────────────────────────────────────

    @Test
    @DisplayName("Sort by name returns alphabetical order")
    void testSortByName() {
        List<Student> sorted = SearchSort.sortByName(students);
        assertEquals("Alice", sorted.get(0).getName());
        assertEquals("Bob",   sorted.get(1).getName());
        assertEquals("Carol", sorted.get(2).getName());
        assertEquals("Dave",  sorted.get(3).getName());
        assertEquals("Eve",   sorted.get(4).getName());
    }

    @Test
    @DisplayName("Sort by name does not modify original list")
    void testSortByNameImmutable() {
        String firstBefore = students.get(0).getName();
        SearchSort.sortByName(students);
        assertEquals(firstBefore, students.get(0).getName());
    }

    @Test
    @DisplayName("Sort by name with single student")
    void testSortByNameSingle() {
        List<Student> single = List.of(new Student(101, "Alice", 8.0, "CS"));
        List<Student> sorted = SearchSort.sortByName(single);
        assertEquals(1, sorted.size());
        assertEquals("Alice", sorted.get(0).getName());
    }

    @Test
    @DisplayName("Sort empty list returns empty list")
    void testSortEmptyList() {
        List<Student> sorted = SearchSort.sortByName(new ArrayList<>());
        assertTrue(sorted.isEmpty());
    }

    // ── Sort by GPA ───────────────────────────────────────

    @Test
    @DisplayName("Sort by GPA returns descending order")
    void testSortByGpa() {
        List<Student> sorted = SearchSort.sortByGpa(students);
        assertEquals(9.1, sorted.get(0).getGpa(), 0.001); // Carol
        assertEquals(8.8, sorted.get(1).getGpa(), 0.001); // Dave
        assertEquals(8.0, sorted.get(2).getGpa(), 0.001); // Eve
        assertEquals(7.5, sorted.get(3).getGpa(), 0.001); // Alice
        assertEquals(6.3, sorted.get(4).getGpa(), 0.001); // Bob
    }

    // ── Binary Search ─────────────────────────────────────

    @Test
    @DisplayName("Binary search finds existing student")
    void testBinarySearchFound() {
        students.sort(Comparator.comparingInt(Student::getRollNo));
        Student found = SearchSort.binarySearchByRoll(students, 103);
        assertNotNull(found);
        assertEquals("Carol", found.getName());
    }

    @Test
    @DisplayName("Binary search returns null for missing roll number")
    void testBinarySearchNotFound() {
        students.sort(Comparator.comparingInt(Student::getRollNo));
        assertNull(SearchSort.binarySearchByRoll(students, 999));
    }

    @Test
    @DisplayName("Binary search finds first element")
    void testBinarySearchFirst() {
        students.sort(Comparator.comparingInt(Student::getRollNo));
        Student found = SearchSort.binarySearchByRoll(students, 101);
        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }

    @Test
    @DisplayName("Binary search finds last element")
    void testBinarySearchLast() {
        students.sort(Comparator.comparingInt(Student::getRollNo));
        Student found = SearchSort.binarySearchByRoll(students, 105);
        assertNotNull(found);
        assertEquals("Eve", found.getName());
    }

    @Test
    @DisplayName("Binary search on empty list returns null")
    void testBinarySearchEmpty() {
        assertNull(SearchSort.binarySearchByRoll(new ArrayList<>(), 101));
    }

    // ── Linear Search by Name ─────────────────────────────

    @Test
    @DisplayName("Search by full name returns match")
    void testSearchByNameFull() {
        List<Student> results = SearchSort.searchByName(students, "Alice");
        assertEquals(1, results.size());
        assertEquals("Alice", results.get(0).getName());
    }

    @Test
    @DisplayName("Search by partial name returns matches")
    void testSearchByNamePartial() {
        List<Student> results = SearchSort.searchByName(students, "al");
        assertEquals(1, results.size()); // "Alice" contains "al"
    }

    @Test
    @DisplayName("Search by name is case-insensitive")
    void testSearchByNameCaseInsensitive() {
        List<Student> results = SearchSort.searchByName(students, "CAROL");
        assertEquals(1, results.size());
    }

    @Test
    @DisplayName("Search with no match returns empty list")
    void testSearchByNameNoMatch() {
        List<Student> results = SearchSort.searchByName(students, "Zara");
        assertTrue(results.isEmpty());
    }
}
