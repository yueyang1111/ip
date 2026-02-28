package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Represents a command that deletes a task from the list.
 */
public class DeleteCommand extends IndexCommand {
    /**
     * Create a Delete command with the given index.
     *
     * @param index Index of the task to be deleted.
     */
    public DeleteCommand(String index) {
        super(index);
    }

    /**
     * Deleted the specified task and saves the updated list.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException If the index is missing or invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        int index = parseTaskIndex(tasks);
        Task removedTask = tasks.delete(index);
        ui.printDeleteMessage(removedTask, tasks.size());
        storage.save(tasks.getAll());
    }
}
