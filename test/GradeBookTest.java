package test;

import ds.StudentBST;
import model.Student;
import module.GradeBook;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GradeBook Tests")
public class GradeBookTest {

    private GradeBook gradeBook;
    private Student alice;
    private Student bob;
    private Student carol;

    @BeforeEach
    void setUp() {
        gradeBook = new GradeBook();
        alice = new Student(101, "Alice", 7.5, "CS");
        bob   = new Student(102, "Bob",   9.0, "IT");
        carol = new Student(103, "Carol", 8.2, "CS");
    }

    @Test
    @DisplayName("New grade book should be empty")
    void testInitiallyEmpty() {
        assertTrue(gradeBook.isEmpty());
    }

    @Test
    @DisplayName("Register student adds to grade book")
    void testRegisterStudent() {
        gradeBook.register(alice);
        assertFalse(gradeBook.isEmpty());
        assertTrue(gradeBook.contains(101));
    }

    @Test
    @DisplayName("Registering duplicate does not add twice")
    void testRegisterDuplicate() {
        gradeBook.register(alice);
        gradeBook.register(alice); // duplicate
        // Leaderboard should still have only 1 entry
        assertEquals(1, gradeBook.getLeaderboard().size());
    }

    @Test
    @DisplayName("Update GPA reflects in leaderboard")
    void testUpdateGpa() {
        gradeBook.register(alice);
        gradeBook.register(bob);
        gradeBook.updateGpa(101, 9.8); // alice overtakes bob
        List<Student> board = gradeBook.getLeaderboard();
        assertEquals(9.8, board.get(0).getGpa(), 0.001);
        assertEquals("Alice", board.get(0).getName());
    }

    @Test
    @DisplayName("Update GPA for non-existing student does not crash")
    void testUpdateGpaNonExisting() {
        assertDoesNotThrow(() -> gradeBook.updateGpa(999, 8.0));
    }

    @Test
    @DisplayName("Leaderboard returns students in descending GPA")
    void testLeaderboardOrder() {
        gradeBook.register(alice); // 7.5
        gradeBook.register(bob);   // 9.0
        gradeBook.register(carol); // 8.2
        List<Student> board = gradeBook.getLeaderboard();
        assertEquals("Bob",   board.get(0).getName()); // 9.0
        assertEquals("Carol", board.get(1).getName()); // 8.2
        assertEquals("Alice", board.get(2).getName()); // 7.5
    }

    @Test
    @DisplayName("Remove student from grade book")
    void testRemoveStudent() {
        gradeBook.register(alice);
        gradeBook.remove(101);
        assertFalse(gradeBook.contains(101));
        assertTrue(gradeBook.isEmpty());
    }

    @Test
    @DisplayName("Remove non-existing student does not crash")
    void testRemoveNonExisting() {
        assertDoesNotThrow(() -> gradeBook.remove(999));
    }

    // Helper to expose leaderboard for assertions
    // (Add this method to GradeBook if not already present)
}
