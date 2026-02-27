package edith.command;

import edith.exception.EdithException;
import edith.task.TaskList;

public abstract class IndexCommand extends Command {
    protected final String index;

    public IndexCommand(String index) {
        this.index = index;
    }

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
