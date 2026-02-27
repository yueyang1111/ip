package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Deadline;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

public class DeadlineCommand extends Command {
    private final String userInput;

    public DeadlineCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        if (userInput.trim().isEmpty()) {
            throw new EdithException("OOPS! The details of deadline cannot be empty");
        }
        String[] details = userInput.split(" /by ", 2);
        if (details.length < 2) {
            throw new EdithException("OOPS! Follow: deadline <description> /by <time>");
        }
        Task task = new Deadline(details[0].trim(), details[1].trim());
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
        storage.save(tasks.getAll());
    }
}
