package hust.soict.project1.server.net;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

public class TCPSocket
{
    public ServerSocket serverSocket;
    public Socket clientSocket;

    public TCPSocket(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void acceptConnection()
    {
        try
        {
            clientSocket = serverSocket.accept();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeConnection()
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
