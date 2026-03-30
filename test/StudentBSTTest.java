package test;

import ds.StudentBST;
import model.Student;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StudentBST Tests")
public class StudentBSTTest {

    private StudentBST bst;

    @BeforeEach
    void setUp() {
        bst = new StudentBST();
    }

    @Test
    @DisplayName("New BST should be empty")
    void testInitiallyEmpty() {
        assertTrue(bst.isEmpty());
        assertTrue(bst.getLeaderboard().isEmpty());
    }

    @Test
    @DisplayName("Insert single student")
    void testInsertSingle() {
        bst.insert(new Student(101, "Alice", 8.5, "CS"));
        List<Student> board = bst.getLeaderboard();
        assertEquals(1, board.size());
        assertEquals("Alice", board.get(0).getName());
    }

    @Test
    @DisplayName("Leaderboard returns students in descending GPA order")
    void testLeaderboardOrder() {
        bst.insert(new Student(101, "Alice", 7.0, "CS"));
        bst.insert(new Student(102, "Bob",   9.5, "IT"));
        bst.insert(new Student(103, "Carol", 8.2, "CS"));

        List<Student> board = bst.getLeaderboard();
        assertEquals(3, board.size());
        assertEquals(9.5, board.get(0).getGpa(), 0.001); // Bob first
        assertEquals(8.2, board.get(1).getGpa(), 0.001); // Carol second
        assertEquals(7.0, board.get(2).getGpa(), 0.001); // Alice third
    }

    @Test
    @DisplayName("Delete a student from BST")
    void testDelete() {
        bst.insert(new Student(101, "Alice", 7.0, "CS"));
        bst.insert(new Student(102, "Bob",   9.5, "IT"));
        bst.delete(102);

        List<Student> board = bst.getLeaderboard();
        assertEquals(1, board.size());
        assertEquals("Alice", board.get(0).getName());
    }

    @Test
    @DisplayName("Delete non-existing node does not crash")
    void testDeleteNonExisting() {
        bst.insert(new Student(101, "Alice", 7.0, "CS"));
        assertDoesNotThrow(() -> bst.delete(999));
        assertEquals(1, bst.getLeaderboard().size());
    }

    @Test
    @DisplayName("Delete root node")
    void testDeleteRoot() {
        bst.insert(new Student(101, "Alice", 7.0, "CS"));
        bst.delete(101);
        assertTrue(bst.isEmpty());
    }

    @Test
    @DisplayName("Insert students with same GPA")
    void testInsertSameGpa() {
        bst.insert(new Student(101, "Alice", 8.0, "CS"));
        bst.insert(new Student(102, "Bob",   8.0, "IT"));
        assertEquals(2, bst.getLeaderboard().size());
    }
}
