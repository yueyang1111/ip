package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

public class DeleteCommand extends IndexCommand {
    public DeleteCommand(String index) {
        super(index);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        int index = parseTaskIndex(tasks);
        Task removedTask = tasks.delete(index);
        ui.printDeleteMessage(removedTask, tasks.size());
        storage.save(tasks.getAll());
    }
}
