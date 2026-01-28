import java.util.Scanner;

public class Edith {
    public static void main(String[] args) {
        String line = "    ___________________________________";
        System.out.println(line);
        System.out.println("    Hello! I'm Edith");
        System.out.println("    What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            System.out.println(line);
            if (userInput.equals("bye")) {
                System.out.println("    Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else {
                System.out.println("    " + userInput);
            }
            System.out.println(line);
        }
        scanner.close();
    }
}
