package hust.soict.project1.server.transporter;

import hust.soict.project1.server.Main;

import java.io.*;

// EPIC SPAGHETTI CODE BELOW

public class FileTransporter
{
    private Transporter transporter;

    public FileTransporter()
    {
        // transporter = Main.getSecondTransporter();
        transporter = Main.getTransporter();
    }

    public void closeConnection() throws IOException
    {
        transporter.tcpSocket.clientSocket.close();
    }

    public void sendFile(String filePath)
    {
        try
        {
            OutputStream out = transporter.tcpSocket.clientSocket.getOutputStream();
            FileInputStream fileIn = new FileInputStream(filePath);

            byte[] buf = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileIn.read(buf)) != -1)
            {
                out.write(buf, 0, bytesRead);
            }

            out.close();
            fileIn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void receiveFile(String filePath)
    {
        try
        {
            InputStream in = transporter.tcpSocket.clientSocket.getInputStream();
            FileOutputStream fileOut = new FileOutputStream(filePath);

            byte[] buf = new byte[4096];
            int bytesRead;

            while ((bytesRead = in.read(buf)) != -1)
            {
                fileOut.write(buf, 0, bytesRead);
            }

            fileOut.close();
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
