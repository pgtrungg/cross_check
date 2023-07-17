package hust.soict.project1.server.transporter;

import hust.soict.project1.server.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MsgTransporter
{
    private PrintWriter out;
    private BufferedReader in;
    private Transporter transporter;

    public MsgTransporter() throws IOException
    {
        transporter = Main.getTransporter();
        out = new PrintWriter(transporter.tcpSocket.clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(transporter.tcpSocket.clientSocket.getInputStream()));
    }

    public void sendToClient(String msg)
    {
        out.println(msg);
    }

    public void sendMultipleLinesToClient(ArrayList<String> msg)
    {
        for (String line : msg)
        {
            out.println(line);
        }

        out.flush();
    }

    public String readFromClient() throws IOException
    {
        String msg = in.readLine();
        return msg;
    }

    public void closeConnection() throws IOException
    {
        in.close();
        out.close();
        transporter.tcpSocket.clientSocket.close();
    }
}
