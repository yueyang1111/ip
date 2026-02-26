package edith;

import edith.storage.Storage;
import edith.task.Deadline;
import edith.exception.EdithException;
import edith.task.Event;
import edith.task.Task;
import edith.task.Todo;
import edith.ui.Ui;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;

public class Edith {
    private static final int TASK_INDEX_OFFSET = 1;
    private static final String FILEPATH = "./data/edith.txt";

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage(FILEPATH);

    public static void main(String[] args) {
        try {
            tasks.addAll(storage.loadFromFile());
        } catch (EdithException | FileNotFoundException e) {
            ui.printError(e.getMessage());
        }

        ui.printGreeting();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String userInput = scanner.nextLine();
                String[] parts = userInput.split(" ", 2);
                String command = parts[0].trim();

                switch (command) {
                case "bye":
                    ui.printExitMessage();
                    scanner.close();
                    return;
                case "list":
                    ui.printTaskList(tasks);
                    break;
                case "mark":
                    markTask(parts);
                    storage.save(tasks);
                    break;
                case "unmark":
                    unmarkTask(parts);
                    storage.save(tasks);
                    break;
                case "todo":
                    addTodo(parts);
                    storage.save(tasks);
                    break;
                case "event":
                    addEvent(parts);
                    storage.save(tasks);
                    break;
                case "deadline":
                    addDeadline(parts);
                    storage.save(tasks);
                    break;
                case "delete":
                    deleteTask(parts);
                    storage.save(tasks);
                    break;
                default:
                    throw new EdithException("OOPS! I don't know what that means!");
                }
            } catch (EdithException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    private static void addTodo(String[] parts) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The description of todo cannot be empty!");
        }
        Task task = new Todo(parts[1].trim());
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
    }

    private static void addEvent(String[] parts) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The details of event cannot be empty!");
        }
        String[] details = parts[1].split(" /from | /to ", 3);
        if (details.length < 3) {
            throw new EdithException("OOPS! Follow: event <description> /from <start> / to <end>");
        }
        Task task = new Event(details[0].trim(), details[1].trim(), details[2].trim());
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
    }

    private static void addDeadline(String[] parts) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The details of deadline cannot be empty");
        }
        String[] details = parts[1].split(" /by ", 2);
        if (details.length < 2) {
            throw new EdithException("OOPS! Follow: deadline <description> /by <time>");
        }
        Task task = new Deadline(details[0].trim(), details[1].trim());
        tasks.add(task);
        ui.printAddMessage(task, tasks.size());
    }

    private static int parseTaskIndex(String[] parts) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! Missing index!");
        }
        try {
            int index = Integer.parseInt(parts[1].trim()) - TASK_INDEX_OFFSET;
            if (index < 0 || index >= tasks.size()) {
                throw new EdithException("Invalid task number");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new EdithException("Task index have to be a integer.");
        }
    }

    private static void markTask(String[] parts) throws EdithException {
        int index = parseTaskIndex(parts);
        Task task = tasks.get(index);
        task.markAsDone();
        ui.printMarkMessage(task);
    }

    private static void unmarkTask(String[] parts) throws EdithException {
        int index = parseTaskIndex(parts);
        Task task = tasks.get(index);
        task.markAsNotDone();
        ui.printUnmarkMessage(task);
    }

    private static void deleteTask(String[] parts) throws EdithException {
        int index = parseTaskIndex(parts);
        Task removedTask = tasks.remove(index);
        ui.printDeleteMessage(removedTask, tasks.size());
    }
}
