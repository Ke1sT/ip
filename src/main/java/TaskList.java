import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public int size() {
        return this.tasks.size();
    }

    public Task delete(int index) {
        return this.tasks.remove(index);
    }
}