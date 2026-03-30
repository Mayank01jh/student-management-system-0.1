package test;

import model.Student;
import module.StudentRegistry;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StudentRegistry Tests")
public class StudentRegistryTest {

    private StudentRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new StudentRegistry();
    }

    @Test
    @DisplayName("New registry should be empty")
    void testInitiallyEmpty() {
        assertTrue(registry.isEmpty());
    }

    @Test
    @DisplayName("Add student successfully")
    void testAddStudent() {
        registry.addStudent(101, "Alice", 8.5, "CS");
        assertFalse(registry.isEmpty());
        assertNotNull(registry.findStudent(101));
    }

    @Test
    @DisplayName("Duplicate roll number is rejected")
    void testDuplicateRollNumber() {
        registry.addStudent(101, "Alice", 8.5, "CS");
        registry.addStudent(101, "Bob",   7.2, "IT"); // duplicate
        assertEquals(1, registry.getAllStudents().size());
        assertEquals("Alice", registry.findStudent(101).getName());
    }

    @Test
    @DisplayName("Remove existing student")
    void testRemoveStudent() {
        registry.addStudent(101, "Alice", 8.5, "CS");
        registry.removeStudent(101);
        assertNull(registry.findStudent(101));
        assertTrue(registry.isEmpty());
    }

    @Test
    @DisplayName("Remove non-existing student does not crash")
    void testRemoveNonExisting() {
        assertDoesNotThrow(() -> registry.removeStudent(999));
    }

    @Test
    @DisplayName("Find non-existing student returns null")
    void testFindNonExisting() {
        assertNull(registry.findStudent(999));
    }

    @Test
    @DisplayName("Undo ADD removes the student")
    void testUndoAdd() {
        registry.addStudent(101, "Alice", 8.5, "CS");
        registry.undoLastAction();
        assertNull(registry.findStudent(101));
    }

    @Test
    @DisplayName("Undo REMOVE restores the student")
    void testUndoRemove() {
        registry.addStudent(101, "Alice", 8.5, "CS");
        registry.removeStudent(101);
        registry.undoLastAction(); // undo the remove
        assertNotNull(registry.findStudent(101));
    }

    @Test
    @DisplayName("Undo on empty stack does not crash")
    void testUndoEmpty() {
        assertDoesNotThrow(() -> registry.undoLastAction());
    }

    @Test
    @DisplayName("getAllStudents returns all added students")
    void testGetAllStudents() {
        registry.addStudent(101, "Alice", 8.5, "CS");
        registry.addStudent(102, "Bob",   7.2, "IT");
        registry.addStudent(103, "Carol", 9.0, "CS");
        assertEquals(3, registry.getAllStudents().size());
    }
}
