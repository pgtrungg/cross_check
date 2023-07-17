import java.util.Arrays;
import java.util.Scanner;

public class ApplicationTest
{
    private static boolean running = true;

    public static void applicationLoop()
    {
        System.out.print("> ");

        Scanner in = new Scanner(System.in);

        String[] command = in.nextLine().split(" ");

        switch (command[0])
        {
            case "login" -> System.out.println("Login request");
            case "logout" -> System.out.println("Logout request");
            case "register" -> System.out.println("Register request");
            case "send" -> System.out.println("Send request");
            case "receive" -> System.out.println("Receive request");
            case "list" -> System.out.println("List request");
            case "delete" -> System.out.println("Delete request");
            case "edit" -> System.out.println("Edit request");
            case "search" -> System.out.println("Search request");
            case "exit" -> running = false;
            default -> System.out.println(Arrays.toString(command));
        }
    }

    public static void main(String[] args)
    {
        while (running)
        {
            applicationLoop();
        }
    }
}
