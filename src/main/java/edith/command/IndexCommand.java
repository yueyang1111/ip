package edith.command;

import edith.exception.EdithException;
import edith.task.TaskList;

/**
 * Represents a command that operates on a task index.
 * <p>
 * Provides the logic for validating task index.
 */
public abstract class IndexCommand extends Command {
    protected final String index;

    /**
     * Creates a IndexCommand with the given index.
     *
     * @param index Index given by user.
     */
    public IndexCommand(String index) {
        this.index = index;
    }

    /**
     * Pareses and validates the task index.
     *
     * @param tasks Current TasksList.
     * @return Index of hte task.
     * @throws EdithException If the index is missing or invalid.
     */
    protected int parseTaskIndex(TaskList tasks) throws EdithException {
        if (index.trim().isEmpty()) {
            throw new EdithException("OOPS! Missing index!");
        }
        try {
            int taskIndex = Integer.parseInt(index.trim()) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new EdithException("Invalid task number");
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new EdithException("Task index have to be a integer.");
        }
    }
}
