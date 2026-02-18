package edith.ui;

import edith.task.Deadline;
import edith.exception.EdithException;
import edith.task.Event;
import edith.task.Task;
import edith.task.Todo;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Edith {
    private static final int TASK_INDEX_OFFSET = 1;
    private static final String LINE = "    ___________________________________";
    private static final int MAX_TASKS = 100;
    private static final String FILEPATH = "./data/edith.txt";

    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

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
                    printTaskList(tasks, taskCount);
                    break;
                case "mark":
                    markTask(tasks, userInput, taskCount);
                    save();
                    break;
                case "unmark":
                    unmarkTask(tasks, userInput, taskCount);
                    save();
                    break;
                case "todo":
                    checkTaskLimit(taskCount);
                    Task todo = addTodo(parts, taskCount);
                    tasks[taskCount++] = todo;
                    save();
                    break;
                case "event":
                    checkTaskLimit(taskCount);
                    Task event = addEvent(parts, taskCount);
                    tasks[taskCount++] = event;
                    save();
                    break;
                case "deadline":
                    checkTaskLimit(taskCount);
                    Task deadline = addDeadline(parts, taskCount);
                    tasks[taskCount++] = deadline;
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

    private static void printTaskList(Task[] tasks, int taskCount) {
        System.out.println(LINE);
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("    " + (i + 1) + "." + tasks[i]);
        }
        System.out.println(LINE);
    }

    private static Task addTodo(String[] parts, int taskCount) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The description of todo cannot be empty!");
        }
        Task task = new Todo(parts[1].trim());
        printAddMessage(task, taskCount + TASK_INDEX_OFFSET);
        return task;
    }

    private static Task addEvent(String[] parts, int taskCount) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The details of event cannot be empty!");
        }
        String[] details = parts[1].split(" /from | /to ", 3);
        if (details.length < 3) {
            throw new EdithException("OOPS! Follow: event <description> /from <start> / to <end>");
        }
        Task task = new Event(details[0].trim(), details[1].trim(), details[2].trim());
        printAddMessage(task, taskCount + TASK_INDEX_OFFSET);
        return task;
    }

    private static Task addDeadline(String[] parts, int taskCount) throws EdithException {
        if (parts.length < 2) {
            throw new EdithException("OOPS! The details of deadline cannot be empty");
        }
        String[] details = parts[1].split(" /by ", 2);
        if (details.length < 2) {
            throw new EdithException("OOPS! Follow: deadline <description> /by <time>");
        }
        Task task = new Deadline(details[0].trim(), details[1].trim());
        printAddMessage(task, taskCount + TASK_INDEX_OFFSET);
        return task;
    }

    private static void printAddMessage(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + taskCount + " tasks in the list");
        System.out.println(LINE);
    }

    private static int parseTaskIndex(String userInput, int taskCount) throws EdithException {
        try {
            int index = Integer.parseInt(userInput.split(" ")[1].trim()) - TASK_INDEX_OFFSET;
            if (index < 0 || index >= taskCount) {
                throw new EdithException("Invalid task number");
            }
            return index;
        } catch (Exception e) {
            throw new EdithException("Provide a valid task number");
        }
    }

    private static void markTask(Task[] tasks, String userInput, int taskCount) throws EdithException {
        int index = parseTaskIndex(userInput, taskCount);
        Task task = tasks[index];
        task.markAsDone();
        System.out.println(LINE);
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    private static void unmarkTask(Task[] tasks, String userInput, int taskCount) throws EdithException {
        int index = parseTaskIndex(userInput, taskCount);
        Task task = tasks[index];
        task.markAsNotDone();
        System.out.println(LINE);
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    private static void printError(String message) {
        System.out.println(LINE);
        System.out.println("    " + message);
        System.out.println(LINE);
    }

    private static void checkTaskLimit(int taskCount) throws EdithException {
        if (taskCount >= MAX_TASKS) {
            throw new EdithException("Oops, task List is full!");
        }
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
            throw new EdithException("Skip corrupted line" + line);
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
                tasks[taskCount++] = task;
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
            for (int i = 0; i < taskCount; i++) {
                textToWrite += tasks[i].toFileString() + System.lineSeparator();
            }
            writeToFile(FILEPATH, textToWrite);
        } catch (IOException e) {
            printError(e.getMessage());
        }
    }
}
