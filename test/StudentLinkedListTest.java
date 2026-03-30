package test;

import ds.StudentLinkedList;
import model.Student;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StudentLinkedList Tests")
public class StudentLinkedListTest {

    private StudentLinkedList list;

    @BeforeEach
    void setUp() {
        list = new StudentLinkedList();
    }

    @Test
    @DisplayName("New list should be empty")
    void testInitiallyEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Add single student increases size")
    void testAddSingle() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Add multiple students")
    void testAddMultiple() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        list.add(new Student(102, "Bob",   7.2, "IT"));
        list.add(new Student(103, "Carol", 9.1, "CS"));
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("Find existing student by roll number")
    void testFindExisting() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        list.add(new Student(102, "Bob",   7.2, "IT"));
        Student found = list.find(101);
        assertNotNull(found);
        assertEquals("Alice", found.getName());
    }

    @Test
    @DisplayName("Find non-existing student returns null")
    void testFindNonExisting() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        assertNull(list.find(999));
    }

    @Test
    @DisplayName("Remove head node")
    void testRemoveHead() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        list.add(new Student(102, "Bob",   7.2, "IT"));
        // add puts 102 at head
        Student removed = list.remove(102);
        assertNotNull(removed);
        assertEquals(102, removed.getRollNo());
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("Remove middle node")
    void testRemoveMiddle() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        list.add(new Student(102, "Bob",   7.2, "IT"));
        list.add(new Student(103, "Carol", 9.1, "CS"));
        Student removed = list.remove(102);
        assertNotNull(removed);
        assertEquals(2, list.size());
        assertNull(list.find(102));
    }

    @Test
    @DisplayName("Remove non-existing student returns null")
    void testRemoveNonExisting() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        assertNull(list.remove(999));
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("toList returns all students")
    void testToList() {
        list.add(new Student(101, "Alice", 8.5, "CS"));
        list.add(new Student(102, "Bob",   7.2, "IT"));
        assertEquals(2, list.toList().size());
    }

    @Test
    @DisplayName("Remove from empty list returns null")
    void testRemoveFromEmpty() {
        assertNull(list.remove(101));
    }
}
