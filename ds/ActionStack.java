package ds;

import model.Student;
import java.util.ArrayDeque;
import java.util.Deque;

public class ActionStack {

    public static class Action {
        public final String  type;     // "ADD" or "REMOVE"
        public final Student student;

        public Action(String type, Student student) {
            this.type    = type;
            this.student = student;
        }
    }

    private final Deque<Action> stack = new ArrayDeque<>();

    public void push(String type, Student s) { stack.push(new Action(type, s)); }
    public Action pop()      { return stack.isEmpty() ? null : stack.pop(); }
    public boolean isEmpty() { return stack.isEmpty(); }
    public int size()        { return stack.size(); }
}
