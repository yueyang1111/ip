import java.util.Scanner;

public class Edith {
    private static final int TASK_INDEX_OFFSET = 1;
    private static final String LINE = "    ___________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        printGreeting();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            String[] parts = userInput.split(" ", 2);
            String command = parts[0];

            switch (command) {
            case "bye":
                printExitMessage();
                scanner.close();
                return;
            case "list":
                printTaskList(tasks, taskCount);
                break;
            case "mark":
                markTask(tasks, userInput);
                break;
            case "unmark":
                unmarkTask(tasks, userInput);
                break;
            case "todo":
                tasks[taskCount++] = addTodo(parts[1], taskCount);
                break;
            case "event":
                tasks[taskCount++] = addEvent(parts[1], taskCount);
                break;
            case "deadline":
                tasks[taskCount++] = addDeadline(parts[1], taskCount);
                break;
            default:
                System.out.println("    Invalid Command.");
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

    private static Task addTodo(String userInput, int taskCount) {
        Task task = new Todo(userInput);
        printAddMessage(task, taskCount);
        return task;
    }

    private static Task addEvent(String userInput, int taskCount) {
        String[] parts = userInput.split(" /from | /to ");
        Task task = new Event(parts[0], parts[1], parts[2]);
        printAddMessage(task, taskCount);
        return task;
    }

    private static Task addDeadline(String userInput, int taskCount) {
        String[] parts = userInput.split(" /by ");
        Task task = new Deadline(parts[0], parts[1]);
        printAddMessage(task, taskCount);
        return task;
    }

    private static void printAddMessage(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("    Got it. I've added this task:");
        System.out.println("    " + task);
        System.out.println("    Now you have " + taskCount + " tasks in the list");
        System.out.println(LINE);
    }

    private static int parseTaskIndex(String userInput) {
        return Integer.parseInt(userInput.split(" ")[1]) - TASK_INDEX_OFFSET;
    }

    private static void markTask(Task[] tasks, String userInput) {
        System.out.println(LINE);
        int index = parseTaskIndex(userInput);
        Task task = tasks[index];
        task.markAsDone();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }

    private static void unmarkTask(Task[] tasks, String userInput) {
        System.out.println(LINE);
        int index = parseTaskIndex(userInput);
        Task task = tasks[index];
        task.markAsNotDone();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    " + task);
        System.out.println(LINE);
    }
}
