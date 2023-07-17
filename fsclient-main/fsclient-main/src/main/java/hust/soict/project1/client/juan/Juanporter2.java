package hust.soict.project1.client.juan;

import hust.soict.project1.client.tcp.TCPSocket;


import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import org.json.JSONObject;

public class Juanporter2
{
    private TCPSocket tcpSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Juanporter2(String address, int port) throws IOException
    {
        tcpSocket = new TCPSocket(address, port);
        Socket clientSocket = tcpSocket.clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void send(String msg)
    {
        out.println(msg);
    }

    public String read() throws IOException
    {
        return in.readLine();
    }

    public ArrayList<String> readMultipleLines() throws IOException
    {
        ArrayList<String> msg = new ArrayList<>();

        String str;

        while ((str = in.readLine()) != null)
        {
            msg.add(str);
        }

        return msg;
    }

    public void Delete(String filename)
    {
        try
        {
            JSONObject request = new JSONObject();
            request.put("request", "delete");
            request.put("filename", filename);

            String requestSerialized = request.toString();

            send(requestSerialized);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void cd(String newDirectory)
    {
        try
        {
            JSONObject request = new JSONObject();
            request.put("request", "edit");
            request.put("newDirectory", newDirectory);

            String requestSerialized = request.toString();

            send(requestSerialized);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
