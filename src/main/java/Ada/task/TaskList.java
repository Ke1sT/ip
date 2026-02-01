package Ada.task;

import java.util.ArrayList;

/**
 * Mutable collection of tasks with basic operations.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    
    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list backed by the given array list.
     *
     * @param tasks initial tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task task to add
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index zero-based index
     * @return task at index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return number of tasks
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index zero-based index
     * @return removed task
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public Task delete(int index) {
        return this.tasks.remove(index);
    }
}