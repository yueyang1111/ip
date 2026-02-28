package edith;

import edith.command.Command;
import edith.parser.Parser;
import edith.storage.Storage;
import edith.exception.EdithException;
import edith.task.TaskList;
import edith.ui.Ui;

/**
 * Main class of Edith chatbot.
 * <p>
 * Initialize the core components (Ui, Storage and TaskList)
 * and run the command processing loop.
 */
public class Edith {
    private static final String FILEPATH = "./data/edith.txt";

    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /**
     * Creates an instance of Edith with the specified filepath
     * for file persistence.
     *
     * @param filePath Relative path of the data file.
     */
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

    /**
     * Starts Edith and process the user command until an
     * exit command is given.
     * <p>
     * This method reads the user input, parse the information
     * and executes the command.
     */
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

    /**
     * Entry point for Edith chatbot.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        new Edith(FILEPATH).run();
    }
}
