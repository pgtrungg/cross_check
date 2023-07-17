package hust.soict.project1.server.transporter;

import hust.soict.project1.server.net.TCPSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Transporter
{
    public TCPSocket tcpSocket;

    public Transporter(int port)
    {
        tcpSocket = new TCPSocket(port);
        start();
        System.out.println("Server started at " + tcpSocket.serverSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.serverSocket.getLocalPort());
    }

    public ServerSocket getServerSocket()
    {
        return tcpSocket.serverSocket;
    }

    public Socket getClientSocket()
    {
        return tcpSocket.clientSocket;
    }

    public void start()
    {
        tcpSocket.acceptConnection();
    }

    public void closeConnection() throws IOException
    {
        tcpSocket.closeConnection();
    }
}
