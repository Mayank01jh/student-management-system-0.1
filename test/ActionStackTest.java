package test;

import ds.ActionStack;
import model.Student;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ActionStack Tests")
public class ActionStackTest {

    private ActionStack stack;
    private Student alice;
    private Student bob;

    @BeforeEach
    void setUp() {
        stack = new ActionStack();
        alice = new Student(101, "Alice", 8.5, "CS");
        bob   = new Student(102, "Bob",   7.2, "IT");
    }

    @Test
    @DisplayName("New stack should be empty")
    void testInitiallyEmpty() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Push increases size")
    void testPushIncreasesSize() {
        stack.push("ADD", alice);
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("Pop returns last pushed action (LIFO)")
    void testPopLIFO() {
        stack.push("ADD", alice);
        stack.push("ADD", bob);

        ActionStack.Action top = stack.pop();
        assertNotNull(top);
        assertEquals("ADD", top.type);
        assertEquals(102, top.student.getRollNo()); // bob was last pushed
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("Pop from empty stack returns null")
    void testPopEmpty() {
        assertNull(stack.pop());
    }

    @Test
    @DisplayName("Push REMOVE action stores correct type")
    void testPushRemoveAction() {
        stack.push("REMOVE", alice);
        ActionStack.Action action = stack.pop();
        assertEquals("REMOVE", action.type);
        assertEquals("Alice", action.student.getName());
    }

    @Test
    @DisplayName("Stack size decreases after pop")
    void testSizeAfterPop() {
        stack.push("ADD", alice);
        stack.push("ADD", bob);
        stack.pop();
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("Multiple pushes and pops maintain correct order")
    void testMultiplePushPop() {
        stack.push("ADD", alice);
        stack.push("REMOVE", bob);
        stack.push("ADD", alice);

        assertEquals(3, stack.size());
        assertEquals(101, stack.pop().student.getRollNo()); // alice (ADD)
        assertEquals(102, stack.pop().student.getRollNo()); // bob (REMOVE)
        assertEquals(101, stack.pop().student.getRollNo()); // alice (ADD)
        assertTrue(stack.isEmpty());
    }
}
