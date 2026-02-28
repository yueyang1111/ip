package edith.task;

/**
 * Represents a generic task with description and done status.
 * <p>
 * Subclasses Todo, Event, Deadline provides more details.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a Task with the given description and
     * initialize done status to incomplete.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the tasks is completed, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the tasks status of task to be completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the tasks status of tasks to be not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the string representation of the tasks.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Converts the task into a format to save to disk.
     *
     * @return Formatted task string.
     */
    public abstract String toFileString();
}
