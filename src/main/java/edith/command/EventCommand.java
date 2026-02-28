package edith.command;

import edith.exception.EdithException;
import edith.parser.DateParser;
import edith.storage.Storage;
import edith.task.Event;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

import java.time.LocalDateTime;

/**
 * Represents a command that adds a event tasks.
 */
public class EventCommand extends Command {
    private final String userInput;

    /**
     * Creates a EventCommand with the given input details.
     *
     * @param userInput Details of the user input.
     */
    public EventCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Parse the event details, creates and adds event task,
     * saves the updated list.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException If the input format is invalid.
     */
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
