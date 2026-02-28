package edith;

import edith.command.Command;
import edith.parser.Parser;
import edith.storage.Storage;
import edith.exception.EdithException;
import edith.task.TaskList;
import edith.ui.Ui;

public class Edith {
    private static final String FILEPATH = "./data/edith.txt";

    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    public Edith(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.loadFromFile());
        } catch (EdithException e) {
            ui.printError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.printGreeting();
        boolean isExit = false;

        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                Command c = Parser.parse(userInput);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (EdithException e) {
                ui.printError(e.getMessage());
            }
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Edith(FILEPATH).run();
    }
}
