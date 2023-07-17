import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommunicationTest
{
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public CommunicationTest(String ip, int port) throws IOException
    {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void startConnection(String ip, int port) throws IOException
    {
        // clientSocket = new Socket(ip, port);
        // out = new PrintWriter(clientSocket.getOutputStream(), true);
        // in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

//    public String sendMessage(String msg) throws IOException
//    {
//        out.println(msg);
//        String resp = in.readLine();
//        return resp;
//    }

//    public void stopConnection() throws IOException
//    {
//        in.close();
//        out.close();
//        clientSocket.close();
//    }

    public void sendToServer(String msg) throws IOException
    {
        out.println(msg);
    }

    public String readFromServer() throws IOException
    {
        String msg = in.readLine();
        return msg;
    }

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException
    {
        CommunicationTest client = new CommunicationTest("127.0.0.1", 1506);
        // client.startConnection("127.0.0.1", 1506);
//        String response = client.sendMessage("seggggggggg");
//        System.out.println(response);
        client.sendToServer("seggggggggggggg");
        System.out.println(client.readFromServer());
    }
}
