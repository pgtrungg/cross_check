package hust.soict.project1.server;

import hust.soict.project1.server.account.AuthStore;
import hust.soict.project1.server.files.FileHandler;
import hust.soict.project1.server.parser.MsgParser;
import hust.soict.project1.server.transporter.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketException;

// spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti
// spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti
// spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti
// spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti
// spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti spaghetti

public class Main
{
    public static int port = 1506;

    public static int getPort()
    {
        return port;
    }

    public static FileHandler getFileHandler()
    {
        return fileHandler;
    }

    public static void setFileHandler(FileHandler fileHandler)
    {
        Main.fileHandler = fileHandler;
    }

    private static FileHandler fileHandler;

    private static void setPort(int port)
    {
        Main.port = port;
    }

    public static Transporter getTransporter()
    {
        return transporter;
    }

    private static Transporter transporter;

    public static MsgTransporter getMsgTransporter()
    {
        return msgTransporter;
    }

    public static FileTransporter getFileTransporter()
    {
        return fileTransporter;
    }

    private static MsgTransporter msgTransporter;
    private static FileTransporter fileTransporter;

    public static AuthStore getAuthStore()
    {
        return authStore;
    }

    private static AuthStore authStore;
    private static MsgParser parser;

    public static void mainLoop(int port) throws IOException, SocketException
    {
        boolean running = true;

        while (running)
        {
            try
            {
                String msg = msgTransporter.readFromClient();
                System.out.println(msg);
                try
                {
                    JSONObject jsonMsg = new JSONObject(msg);
                    parser.parseMsg(jsonMsg);
                }
                catch (JSONException e)
                {
                    System.out.println("Not a valid JSON message");
                    msgTransporter.sendToClient(msg + " is not a valid command.");
                }
            }
            catch (SocketException e)
            {
                System.out.println("Client disconnected");
                running = false;
            }
        }

        transporter.closeConnection();
    }

    public static void main(String[] args) throws Exception
    {
        for (int i = 0; i < args.length; ++i)
        {
            if (args[i].equals("-d") || args[i].equals("--debug"))
            {
                System.out.println("Debug mode enabled");
            }
            if (args[i].equals("-p") || args[i].equals("--port"))
            {
                setPort(Integer.parseInt(args[i + 1]));
            }
            if (args[i].equals("-a") || args[i].equals("--address"))
            {
                System.out.println("Address: " + args[i + 1]);
            }
        }

        transporter = new Transporter(port);

        try
        {
            msgTransporter = new MsgTransporter();
            fileTransporter = new FileTransporter();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        authStore = new AuthStore();
        parser = new MsgParser();

        mainLoop(port);

        authStore.cleanUp();
    }
}