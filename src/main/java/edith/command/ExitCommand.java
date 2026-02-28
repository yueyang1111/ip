package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Represents the exit command that terminates the program.
 */
public class ExitCommand extends Command {
    /**
     * Prints the exit message.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException Not used.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        ui.printExitMessage();
    }

    /**
     * Indicate that this command exits the program.
     *
     * @return True.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
