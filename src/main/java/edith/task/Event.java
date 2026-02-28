package edith.task;

import edith.parser.DateParser;

import java.time.LocalDateTime;

public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    private String format(LocalDateTime dateTime) {
        return DateParser.formatDate(dateTime);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + format(from)
                + " to: " + format(to) + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + from + " | " + to;
    }
}
