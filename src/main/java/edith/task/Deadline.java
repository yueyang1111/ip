package edith.task;

import edith.parser.DateParser;

import java.time.LocalDateTime;

/**
 * Represents a Deadline task that contains a description and by details.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Creates a Deadline tasks with the given description and by details.
     *
     * @param description Description of the task.
     * @param by Deadline of the task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    private String format(LocalDateTime dateTime) {
        return DateParser.formatDate(dateTime);
    }

    /**
     * Returns the string representation of deadline task.
     *
     * @return Formatted deadline task string.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +
                format(by) + ")";
    }

    /**
     * Converts deadline task into a format to save to disk.
     *
     * @return Formatted deadline task string.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " +
                description + " | " + by;
    }
}
