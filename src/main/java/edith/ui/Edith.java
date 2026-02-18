package edith.ui;

import edith.task.Deadline;
import edith.exception.EdithException;
import edith.task.Event;
import edith.task.Task;
import edith.task.Todo;

import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Edith {
    private static final int TASK_INDEX_OFFSET = 1;
    private static final String LINE = "    ___________________________________";
    private static final String FILEPATH = "./data/edith.txt";

    private static final ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        File f = new File(FILEPATH);
        if (f.exists()) {
            try {
                loadFromFile(FILEPATH);
            } catch (FileNotFoundException e) {
                printError(e.getMessage());
            }
        }

        printGreeting();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String userInput = scanner.nextLine();
                String[] parts = userInput.split(" ", 2);
                String command = parts[0].trim();

                switch (command) {
                case "bye":
                    printExitMessage();
                    scanner.close();
                    return;
                case "list":
                    printTaskList(tasks);
                    break;
                case "mark":
                    markTask(tasks, parts);
                    save();
                    break;
                case "unmark":
                    unmarkTask(tasks, parts);
                    save();
                    break;
                case "todo":
                    addTodo(tasks, parts);
                    save();
                    break;
                case "event":
                    addEvent(tasks, parts);
                    save();
                    break;
                case "deadline":
                    addDeadline(tasks, parts);
                    save();
                    break;
                case "delete":
                    deleteTask(tasks, parts);
                    save();
                    break;
                default:
                    throw new EdithException("OOPS! I don't know what that means!");
                }
            } catch (EdithException e) {
                printError(e.getMessage());
            }
        }
    }

    private static void printGreeting() {
        System.out.println(LINE);
        System.out.println("    Hello! I'm Edith");
        System.out.println("    What can I do for you?");
        System.out.println(LINE);
    }

    private static void printExitMessage() {
        System.out.println(LINE);
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private static void printTaskList(ArrayList<Task> tasks) throws EdithException {
        if (tasks.isEmpty()) {
            throw new EdithException("OOPS! The list is empty!");
        }
        System.out.println(LINE);
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    private static void addTodo(ArrayList<Task> tasks, String[] parts) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The description of todo cannot be empty!");
        }
        Task task = new Todo(parts[1].trim());
        tasks.add(task);
        printAddMessage(task, tasks.size());
    }

    private static void addEvent(ArrayList<Task> tasks, String[] parts) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The details of event cannot be empty!");
        }
        String[] details = parts[1].split(" /from | /to ", 3);
        if (details.length < 3) {
            throw new EdithException("OOPS! Follow: event <description> /from <start> / to <end>");
        }
        Task task = new Event(details[0].trim(), details[1].trim(), details[2].trim());
        tasks.add(task);
        printAddMessage(task, tasks.size());
    }

    private static void addDeadline(ArrayList<Task> tasks, String[] parts) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The details of deadline cannot be empty");
        }
        String[] details = parts[1].split(" /by ", 2);
        if (details.length < 2) {
            throw new EdithException("OOPS! Follow: deadline <description> /by <time>");
        }
        Task task = new Deadline(details[0].trim(), details[1].trim());
        tasks.add(task);
        printAddMessage(task, tasks.size());
    }

    private static void printAddMessage(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + taskCount + " tasks in the list");
        System.out.println(LINE);
    }

    private static int parseTaskIndex(String[] parts, int taskSize) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! Missing index!");
        }
        try {
            int index = Integer.parseInt(parts[1].trim()) - TASK_INDEX_OFFSET;
            if (index < 0 || index >= taskSize) {
                throw new EdithException("Invalid task number");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new EdithException("Task index have to be a integer.");
        }
    }

    private static void markTask(ArrayList<Task> tasks, String[] parts) throws EdithException {
        int index = parseTaskIndex(parts, tasks.size());
        Task task = tasks.get(index);
        task.markAsDone();
        System.out.println(LINE);
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    private static void unmarkTask(ArrayList<Task> tasks, String[] parts) throws EdithException {
        int index = parseTaskIndex(parts, tasks.size());
        Task task = tasks.get(index);
        task.markAsNotDone();
        System.out.println(LINE);
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    private static void deleteTask(ArrayList<Task> tasks, String[] parts) throws EdithException {
        int index = parseTaskIndex(parts, tasks.size());
        Task removedTask = tasks.remove(index);
        System.out.println(LINE);
        System.out.println("    Noted. I've removed this task:");
        System.out.println("    " + removedTask);
        System.out.println("    Now you have " + tasks.size() + " tasks in the list");
        System.out.println(LINE);
    }

    private static void printError(String message) {
        System.out.println(LINE);
        System.out.println("    " + message);
        System.out.println(LINE);
    }

    private static Task parseTaskData(String line) throws EdithException {
        String [] parts = line.split("\\|");
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

    private static void loadFromFile(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String line = s.nextLine();
            try {
                Task task = parseTaskData(line);
                tasks.add(task);
            } catch (EdithException e) {
                printError(e.getMessage());
            }
        }
        s.close();
    }

    private static void writeToFile(String filePath, String textToAdd) throws IOException {
        FileWriter fw = new FileWriter(filePath);
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
            writeToFile(FILEPATH, textToWrite);
        } catch (IOException e) {
            printError(e.getMessage());
        }
    }
}
