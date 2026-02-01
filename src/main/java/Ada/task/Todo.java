package Ada.task;

/**
 * A simple task without any associated dates.
 */
public class Todo extends Task {
    /**
     * Creates a todo task.
     *
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toDataString() {
        return "T | " + super.toDataString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}