package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Represents a command that marks a task as not complete.
 */
public class UnmarkCommand extends IndexCommand {
    /**
     * Creates a UnmarkCommand with the given index.
     *
     * @param index Index of the task to be unmark.
     */
    public UnmarkCommand(String index) {
        super(index);
    }

    /**
     * Unmarks the specified task and saves the updated list.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException If the index is missing or invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        int index = parseTaskIndex(tasks);
        Task task = tasks.unmark(index);
        ui.printUnmarkMessage(task);
        storage.save(tasks.getAll());
    }
}
