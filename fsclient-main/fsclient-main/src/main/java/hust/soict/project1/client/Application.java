package hust.soict.project1.client;

import hust.soict.project1.client.juan.Juanporter2;
import hust.soict.project1.client.juan.Fileporter;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Application
{
    private static boolean running = true;
    private static String address = "127.0.0.1";
    private static int port = 1506;

    public static Juanporter2 getJuan()
    {
        return juan;
    }

    private static Juanporter2 juan;

    public static void applicationLoop() throws IOException
    {
        System.out.print("> ");

        Scanner in = new Scanner(System.in);

        String[] command = in.nextLine().split(" ");

        switch (command[0])
        {
            case "login" ->
            {
                String username = command[1];
                String password = command[2];

                JSONObject request = new JSONObject();
                request.put("request", "login");
                request.put("username", username);
                request.put("password", password);

                String requestSerialized = request.toString();

                juan.send(requestSerialized);

                System.out.println(juan.read());
            }

            case "logout" -> 
            {
                System.out.println("Logout request");
            }

            case "cd" ->
            {
                JSONObject request = new JSONObject();
                request.put("request", "cd");
                request.put("newDir", command[1]);

                String requestSerialized = request.toString();
                juan.send(requestSerialized);

                try
                {
                    System.out.println(juan.read());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            
            case "upload" -> 
            {
                JSONObject request = new JSONObject();
                request.put("request", "upload");
                request.put("filename", command[2]);

                String path = command[1] + "/" + command[2];

                String requestSerialized = request.toString();
                juan.send(requestSerialized);

                String response = null;

                try
                {
                    response = juan.read();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                JSONObject serverResponse = new JSONObject(response);

                if (serverResponse.get("status").equals("ready"))
                {
                    Fileporter.SocketFileSend(path);

                    JSONObject finishedResponse = new JSONObject();
                    finishedResponse.put("status", "done");

                    juan.send(finishedResponse.toString());
                }

                System.out.println(juan.read());
            }
            case "download" -> 
            {
                JSONObject request = new JSONObject();
                request.put("request", "download");
                request.put("filename", command[1]);
                String path = command[2] + "/" + command[1];

                String requestSerialized = request.toString();
                juan.send(requestSerialized);

                String response = null;

                try
                {
                    response = juan.read();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                JSONObject serverResponse = new JSONObject(response);

                if (serverResponse.get("status").equals("ready"))
                {
                    Fileporter.SocketFileReceive(path);
                }
            }
            
            case "list" -> 
            {
                JSONObject request = new JSONObject();
                request.put("request", "list");

                String requestSerialized = request.toString();
                juan.send(requestSerialized);

                try
                {
                    JSONObject response = new JSONObject(juan.read());
                    System.out.printf(response.get("result").toString());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            case "delete" -> 
            {
                JSONObject request = new JSONObject();
                request.put("request", "delete");
                request.put("filename", command[1]);

                String requestSerialized = request.toString();
                juan.send(requestSerialized);

                System.out.println(juan.read());
            }

            case "exit" -> running = false;
            
            default ->
            {
                System.out.println(Arrays.toString(command));
                juan.send(Arrays.toString(command));

//                ArrayList<String> msg = juan.readMultipleLines();
//
//                for (String line : msg)
//                {
//                    System.out.println(line);
//                }

                String msg = juan.read();
                System.out.println(msg);
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        Application app = new Application();

        for (int i=0; i<args.length; ++i)
        {
            if (args[i].equals("-a"))
            {
                address = args[i+1];
            }
            if (args[i].equals("-p"))
            {
                port = Integer.parseInt(args[i+1]);
            }
        }

        juan = new Juanporter2(address, port);

        while (running)
        {
            applicationLoop();
        }
    }
}
