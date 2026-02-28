package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Task;
import edith.task.TaskList;
import edith.task.Todo;
import edith.ui.Ui;

/**
 * Represents a command that adds a todo task.
 */
public class TodoCommand extends Command {
    private final String userInput;

    /**
     * Creates a TodoCommand with the given input details.
     *
     * @param userInput Details of user input.
     */
    public TodoCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Parse the todo details, creates and adds a todo task,
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
            throw new EdithException("OOPS! The description of todo cannot be empty!");
        }
        Task task = new Todo(userInput.trim());
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
        storage.save(tasks.getAll());
    }
}
