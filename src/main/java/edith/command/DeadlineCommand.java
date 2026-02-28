package edith.command;

import edith.exception.EdithException;
import edith.parser.DateParser;
import edith.storage.Storage;
import edith.task.Deadline;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

import java.time.LocalDateTime;

/**
 * Represents a command that adds a deadline task.
 */
public class DeadlineCommand extends Command {
    private final String userInput;

    /**
     * Creates a DeadlineCommand with the given input details.
     *
     * @param userInput Details of the user input.
     */
    public DeadlineCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Parse the deadline details, creates and adds a Deadline task,
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
            throw new EdithException("OOPS! The details of deadline cannot be empty");
        }
        String[] details = userInput.split(" /by ", 2);
        if (details.length < 2) {
            throw new EdithException("OOPS! Follow: deadline <description> /by <time>");
        }
        String description = details[0].trim();
        String byDateString = details[1].trim();
        if (description.isEmpty() || byDateString.isEmpty()) {
            throw new EdithException("OOPS! The details of deadline cannot be empty");
        }
        LocalDateTime by = DateParser.parseDate(byDateString);
        Task task = new Deadline(description, by);
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
        storage.save(tasks.getAll());
    }
}
