package edith;

import edith.task.Deadline;
import edith.exception.EdithException;
import edith.task.Event;
import edith.task.Task;
import edith.task.Todo;
import edith.ui.Ui;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Edith {
    private static final int TASK_INDEX_OFFSET = 1;
    private static final String FILEPATH = "./data/edith.txt";

    private static final ArrayList<Task> tasks = new ArrayList<>();
    private static final Ui ui = new Ui();

    public static void main(String[] args) {
        File f = new File(FILEPATH);
        if (f.exists()) {
            try {
                loadFromFile();
            } catch (FileNotFoundException e) {
                ui.printError(e.getMessage());
            }
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
                    save();
                    break;
                case "unmark":
                    unmarkTask(parts);
                    save();
                    break;
                case "todo":
                    addTodo(parts);
                    save();
                    break;
                case "event":
                    addEvent(parts);
                    save();
                    break;
                case "deadline":
                    addDeadline(parts);
                    save();
                    break;
                case "delete":
                    deleteTask(parts);
                    save();
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

    private static Task parseTaskData(String line) throws EdithException {
        String [] parts = line.split("\\|");
        if (parts.length < 2) {
            throw new EdithException("Skip corrupted line: " + line);
        }
        String command = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");

        Task task;
        switch (command) {
        case "T":
            if (parts.length < 3) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            task = new Todo(parts[2].trim());
            break;
        case "D":
            if (parts.length < 4) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            task = new Deadline(parts[2].trim(), parts[3].trim());
            break;
        case "E":
            if (parts.length < 5) {
                throw new EdithException("Skip corrupted line: " + line);
            }
            task = new Event(parts[2].trim(), parts[3].trim(), parts[4].trim());
            break;
        default:
            throw new EdithException("Skip corrupted line: " + line);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private static void loadFromFile() throws FileNotFoundException {
        File f = new File(FILEPATH);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String line = s.nextLine();
            try {
                Task task = parseTaskData(line);
                tasks.add(task);
            } catch (EdithException e) {
                ui.printError(e.getMessage());
            }
        }
        s.close();
    }

    private static void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(FILEPATH);
        fw.write(textToAdd);
        fw.close();
    }

    private static void save() {
        try {
            File f = new File(FILEPATH);
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            String textToWrite = "";
            for (int i = 0; i < tasks.size(); i++) {
                textToWrite += tasks.get(i).toFileString() + System.lineSeparator();
            }
            writeToFile(textToWrite);
        } catch (IOException e) {
            ui.printError(e.getMessage());
        }
    }
}
