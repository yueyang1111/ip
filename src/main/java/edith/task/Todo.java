package edith.task;

/**
 * Represents a todo task that contains only a description.
 */
public class Todo extends Task {
    /**
     * Creates a Todo task with the given description.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of todo task.
     *
     * @return Formatted todo task string.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Converts todo task into a format to save to disk.
     *
     * @return Formatted todo task string.
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
