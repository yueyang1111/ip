package edith.ui;

import edith.exception.EdithException;
import edith.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the user interaction with Edith.
 * <p>
 * Reads the user command and prints formatted messages.
 */
public class Ui {
    private static final String INDENT = "    ";
    private static final String LINE = INDENT + "___________________________________";
    private final Scanner scanner;

    /**
     * Creates the Ui object and initialize the scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads the user command.
     *
     * @return User input string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Close the scanner.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Prints the line divider.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    private void printMessage(String message) {
        System.out.println(INDENT + message);
    }

    private String pluralizeTask(int taskCount) {
        return taskCount == 1 ? "task" : "tasks";
    }

    /**
     * Prints the greeting message at the start of the conversation.
     */
    public void printGreeting() {
        showLine();
        printMessage("Hello! I'm Edith");
        printMessage("What can I do for you?");
        showLine();
    }

    /**
     * Prints the exit message at the end of the conversation.
     */
    public void printExitMessage() {
        showLine();
        printMessage("Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Prints the full list of task.
     *
     * @param tasks List of tasks to be displayed.
     * @throws EdithException If the list is empty.
     */
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

    /**
     * Prints a formatted message whenever a task is added.
     *
     * @param task Task that was added.
     * @param taskCount The current task count.
     */
    public void printAddMessage(Task task, int taskCount) {
        showLine();
        printMessage("Got it. I've added this task:");
        printMessage(task.toString());
        printMessage("Now you have " + taskCount + " "
                + pluralizeTask(taskCount) + " in the list");
        showLine();
    }

    /**
     * Prints the formatted message whenever a task is marked.
     *
     * @param task Task to be marked.
     */
    public void printMarkMessage(Task task) {
        showLine();
        printMessage("Nice! I've marked this task as done:");
        printMessage(task.toString());
        showLine();
    }

    /**
     * Prints the formatted message whenever a task is unmarked.
     *
     * @param task Task to be unmarked.
     */
    public void printUnmarkMessage(Task task) {
        showLine();
        printMessage("OK, I've marked this task as not done yet:");
        printMessage(task.toString());
        showLine();
    }

    /**
     * Prints the formatted message whenever a task is deleted.
     *
     * @param task Task to be deleted.
     * @param taskCount Remaining number of tasks in the list.
     */
    public void printDeleteMessage(Task task, int taskCount) {
        showLine();
        printMessage("Noted. I've removed this task:");
        printMessage(task.toString());
        printMessage("Now you have " + taskCount + " "
                + pluralizeTask(taskCount) + " in the list");
        showLine();
    }

    /**
     * Prints the error message to the console.
     *
     * @param message Message to be printed out.
     */
    public void printError(String message) {
        showLine();
        printMessage(message);
        showLine();
    }
}
