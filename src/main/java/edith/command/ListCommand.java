package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.TaskList;
import edith.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        ui.printTaskList(tasks.getAll());
    }
}
