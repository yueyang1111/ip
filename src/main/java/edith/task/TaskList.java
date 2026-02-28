package edith.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks in Edith.
 * <p>
 * Allow operations to add, remove, update tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList initialized with the given tasks.
     *
     * @param tasks Initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to TaskList.
     *
     * @param task Task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Index of the task wanted.
     * @return Task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns whether TaskList is empty.
     *
     * @return True is empty, false otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns all the tasks in the list.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
     * Marks given task as completed.
     *
     * @param index Index of the task.
     * @return The updated task.
     */
    public Task mark(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    /**
     * Marks given task as not completed.
     *
     * @param index Index of the task.
     * @return The updated task.
     */
    public Task unmark(int index) {
        Task task = tasks.get(index);
        task.markAsNotDone();
        return task;
    }

    /**
     * Deletes the given task.
     *
     * @param index Index of the task.
     * @return The removed task.
     */
    public Task delete(int index) {
        Task removedTask = tasks.remove(index);
        return removedTask;
    }
}
