package edith.task;

import edith.parser.DateParser;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    private String format(LocalDateTime dateTime) {
        return DateParser.formatDate(dateTime);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +
                format(by) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " +
                description + " | " + by;
    }
}
