package Ada.task;

/**
 * Base type for tasks tracked by the application.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     *
     * @param description task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon: "X" when done, space otherwise.
     *
     * @return status icon used in display
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the serialized core fields used by storage.
     *
     * @return data string with done flag and description
     */
    public String toDataString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}