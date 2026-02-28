package edith.ui;

import edith.exception.EdithException;
import edith.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String INDENT = "    ";
    private static final String LINE = INDENT + "___________________________________";
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    private void printMessage(String message) {
        System.out.println(INDENT + message);
    }

    private String pluralizeTask(int taskCount) {
        return taskCount == 1 ? "task" : "tasks";
    }

    public void printGreeting() {
        showLine();
        printMessage("Hello! I'm Edith");
        printMessage("What can I do for you?");
        showLine();
    }

    public void printExitMessage() {
        showLine();
        printMessage("Bye. Hope to see you again soon!");
        showLine();
    }

    public void printTaskList(ArrayList<Task> tasks) throws EdithException {
        if (tasks.isEmpty()) {
            throw new EdithException("OOPS! The list is empty!");
        }
        showLine();
        printMessage("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            printMessage((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    public void printAddMessage(Task task, int taskCount) {
        showLine();
        printMessage("Got it. I've added this task:");
        printMessage(task.toString());
        printMessage("Now you have " + taskCount + " "
                + pluralizeTask(taskCount) + " in the list");
        showLine();
    }

    public void printMarkMessage(Task task) {
        showLine();
        printMessage("Nice! I've marked this task as done:");
        printMessage(task.toString());
        showLine();
    }

    public void printUnmarkMessage(Task task) {
        showLine();
        printMessage("OK, I've marked this task as not done yet:");
        printMessage(task.toString());
        showLine();
    }

    public void printDeleteMessage(Task task, int taskCount) {
        showLine();
        printMessage("Noted. I've removed this task:");
        printMessage(task.toString());
        printMessage("Now you have " + taskCount + " "
                + pluralizeTask(taskCount) + " in the list");
        showLine();
    }

    public void printError(String message) {
        showLine();
        printMessage(message);
        showLine();
    }

    public void printFoundTasksMessage(ArrayList<Task> tasks) throws EdithException {
        if (tasks.isEmpty()) {
            throw new EdithException("Oops! No matches found!");
        }

        showLine();
        printMessage("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            printMessage((i+1) + "." + tasks.get(i));
        }
        showLine();
    }
}
