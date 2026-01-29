import java.util.Scanner;

public class Edith {
    private static final String LINE = "    ___________________________________";

    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;

        printGreeting();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                printExitMessage();
                break;
            } else if (userInput.equals("list")) {
                printTaskList(tasks, taskCount);
            } else if (userInput.startsWith("mark ")) {
                markTask(tasks, userInput);
            } else if (userInput.startsWith("unmark ")) {
                unmarkTask(tasks, userInput);
            } else {
                addTask(tasks, taskCount, userInput);
                taskCount++;
            }
        }
        scanner.close();
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
            Task task = tasks[i];
            System.out.println("    " + (i + 1) + ".[" + task.getStatusIcon()
                    + "] " + task.getDescription());
        }
        System.out.println(LINE);
    }

    private static void addTask(Task[] tasks, int taskCount, String userInput) {
        System.out.println(LINE);
        tasks[taskCount] = new Task(userInput);
        System.out.println("    added: " + userInput);
        System.out.println(LINE);
    }

    private static void markTask(Task[] tasks, String userInput) {
        System.out.println(LINE);
        int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
        Task task = tasks[index];
        task.markAsDone();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("    [X] " + task.getDescription());
        System.out.println(LINE);
    }

    private static void unmarkTask(Task[] tasks, String userInput) {
        System.out.println(LINE);
        int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
        Task task = tasks[index];
        task.markAsNotDone();
        System.out.println("    OK, I've marked this task as not done yet:");
        System.out.println("    [ ] " + task.getDescription());
        System.out.println(LINE);
    }
}
