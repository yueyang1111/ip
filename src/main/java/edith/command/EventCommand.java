package edith.command;

import edith.exception.EdithException;
import edith.parser.DateParser;
import edith.storage.Storage;
import edith.task.Event;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

import java.time.LocalDateTime;

public class EventCommand extends Command {
    private final String userInput;

    public EventCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        if (userInput.trim().isEmpty()) {
            throw new EdithException("OOPS! The details of event cannot be empty!");
        }
        String[] first = userInput.split(" /from ", 2);
        if (first.length < 2) {
            throw new EdithException("OOPS! Follow: event <description> /from <start> /to <end>");
        }
        String description = first[0].trim();
        if (description.isEmpty()) {
            throw new EdithException("OOPS! The description of event cannot be empty");
        }

        String[] second = first[1].split(" /to ", 2);
        if (second.length < 2) {
            throw new EdithException("OOPS! Follow: event <description> /from <start> /to <end>");
        }
        String fromDateString = second[0].trim();
        String toDateString = second[1].trim();
        if (fromDateString.isEmpty() || toDateString.isEmpty()) {
            throw new EdithException("OOPS! The start and end time cannot be empty");
        }
        LocalDateTime from = DateParser.parseDate(fromDateString);
        LocalDateTime to = DateParser.parseDate(toDateString);

        if (to.isBefore(from)) {
            throw new EdithException("OOPS! End date/time must not be before start date/time!");
        }
        Task task = new Event(description, from, to);
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
        storage.save(tasks.getAll());
    }
}
