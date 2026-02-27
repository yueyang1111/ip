package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

public class UnmarkCommand extends IndexCommand {
    public UnmarkCommand(String index) {
        super(index);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        int index = parseTaskIndex(tasks);
        Task task = tasks.unmark(index);
        ui.printUnmarkMessage(task);
        storage.save(tasks.getAll());
    }
}
