package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Represents a generic executable user command.
 * <p>
 * Subclasses will implement specific operations.
 */
public abstract class Command {
    /**
     * Executes the command using given TaskList, Ui and Storage.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException If the command execution fails.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException;

    /**
     * Returns whether this command should terminate the program.
     *
     * @return True if the command exits the program, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
