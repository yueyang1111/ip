package edith.task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }

    public Task mark(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    public Task unmark(int index) {
        Task task = tasks.get(index);
        task.markAsNotDone();
        return task;
    }

    public Task delete(int index) {
        Task removedTask = tasks.remove(index);
        return removedTask;
    }

    public TaskList find(String keyword) {
        TaskList foundTasks = new TaskList();

        String target = keyword.trim().toLowerCase();
        if (target.isEmpty()) {
            return foundTasks;
        }

        for (Task t : tasks) {
            String candidate = t.getDescription().toLowerCase();
            if (candidate.contains(target)) {
                foundTasks.add(t);
            }
        }
        return foundTasks;
    }
}
