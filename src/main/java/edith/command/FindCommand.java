package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Represents a command that searches a keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a FindCommand with the given keyword.
     *
     * @param keyword Keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Finds and display tasks with matching keywords.
     *
     * @param tasks Current TaskList.
     * @param ui The Ui for user interaction.
     * @param storage Storage for file persistence.
     * @throws EdithException If the keyword is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        if (keyword.trim().isEmpty()) {
            throw new EdithException("Invalid keyword");
        }
        TaskList foundTasks = tasks.find(keyword);
        ui.printFoundTasksMessage(foundTasks.getAll());
    }
}
