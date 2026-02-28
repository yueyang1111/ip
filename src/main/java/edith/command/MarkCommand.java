package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Represents a command that marks a task as complete.
 */
public class MarkCommand extends IndexCommand {
    /**
     * Creates a MarkCommand with the given index.
     *
     * @param index Index of the task to be mark.
     */
    public MarkCommand(String index) {
        super(index);
    }

    /**
     * Marks the specified task and saves the updated list.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException If the index is missing or invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        int index = parseTaskIndex(tasks);
        Task task = tasks.mark(index);
        ui.printMarkMessage(task);
        storage.save(tasks.getAll());
    }
}
