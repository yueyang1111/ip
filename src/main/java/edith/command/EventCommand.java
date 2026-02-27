package edith.command;

import edith.exception.EdithException;
import edith.storage.Storage;
import edith.task.Event;
import edith.task.Task;
import edith.task.TaskList;
import edith.ui.Ui;

public class EventCommand extends Command {
    private final String userInput;

    public EventCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EdithException {
        if (userInput.trim().isEmpty()) {
            throw new EdithException("OOPS! The details of event cannot be empty!");
        }
        String[] details = userInput.split(" /from | /to ", 3);
        if (details.length < 3) {
            throw new EdithException("OOPS! Follow: event <description> /from <start> / to <end>");
        }
        Task task = new Event(details[0].trim(), details[1].trim(), details[2].trim());
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
        storage.save(tasks.getAll());
    }
}
