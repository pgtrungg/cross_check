package hust.soict.project1.client.tcp;

import java.io.*;
import java.net.Socket;

public class TCPSocket
{
    public Socket clientSocket;

    public TCPSocket(String address, int port)
    {
        try
        {
            clientSocket = new Socket(address, port);
            System.out.println("Connected to server at " + address + ":" + port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            clientSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
