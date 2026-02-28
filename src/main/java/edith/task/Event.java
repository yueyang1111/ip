package edith.task;

import edith.parser.DateParser;

import java.time.LocalDateTime;

/**
 * Represents a Event task that contains a description, from and to details.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Creates a Event tasks with the given description, from and to details.
     *
     * @param description Description of the event task.
     * @param from Start date/time of the event.
     * @param to End date/time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    private String format(LocalDateTime dateTime) {
        return DateParser.formatDate(dateTime);
    }

    /**
     * Returns the string representation of event task.
     *
     * @return Formatted event task string.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + format(from)
                + " to: " + format(to) + ")";
    }

    /**
     * Converts event task into a format to save to disk.
     *
     * @return Formatted event task string.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + from + " | " + to;
    }
}
