package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.TaskList;
import edith.ui.Ui;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        if (keyword.trim().isEmpty()) {
            throw new EdithException("Invalid keyword");
        }
        TaskList foundTasks = tasks.find(keyword);
        ui.printFoundTasksMessage(foundTasks.getAll());
    }
}
