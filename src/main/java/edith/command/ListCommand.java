package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Represents a command that display all the tasks.
 */
public class ListCommand extends Command {
    /**
     * Displays the full list of tasks.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException Not used.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        ui.printTaskList(tasks.getAll());
    }
}
