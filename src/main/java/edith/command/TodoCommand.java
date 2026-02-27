package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Task;
import edith.task.TaskList;
import edith.task.Todo;
import edith.ui.Ui;

public class TodoCommand extends Command {
    private final String userInput;

    public TodoCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        if (userInput.trim().isEmpty()) {
            throw new EdithException("OOPS! The description of todo cannot be empty!");
        }
        Task task = new Todo(userInput.trim());
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
        storage.save(tasks.getAll());
    }
}
