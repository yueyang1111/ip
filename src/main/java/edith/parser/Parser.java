package edith.parser;

import edith.command.Command;
import edith.command.DeadlineCommand;
import edith.command.DeleteCommand;
import edith.command.EventCommand;
import edith.command.ExitCommand;
import edith.command.FindCommand;
import edith.command.ListCommand;
import edith.command.MarkCommand;
import edith.command.TodoCommand;
import edith.command.UnmarkCommand;
import edith.exception.EdithException;

public class Parser {
    public static Command parse(String userInput) throws EdithException {
        if (userInput.trim().isEmpty()) {
            throw new EdithException("Oops! Empty Command");
        }

        String[] parts = userInput.trim().split(" ",2);
        String command = parts[0].trim();
        String details = (parts.length < 2) ? "" : parts[1];

        switch (command) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "mark":
            return new MarkCommand(details);
        case "unmark":
            return new UnmarkCommand(details);
        case "todo":
            return new TodoCommand(details);
        case "event":
            return new EventCommand(details);
        case "deadline":
            return new DeadlineCommand(details);
        case "delete":
            return new DeleteCommand(details);
        case "find":
            return new FindCommand(details);
        default:
            throw new EdithException("OOPS! I don't know what that means!");
        }
    }
}
