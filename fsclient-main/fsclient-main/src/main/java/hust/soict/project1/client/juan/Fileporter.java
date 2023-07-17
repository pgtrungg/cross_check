package hust.soict.project1.client.juan;

import java.io.*;
import java.net.Socket;

public class Fileporter
{
// receive from server
    public static void SocketFileReceive(String savePath) {
        String serverAddress = "127.0.0.1"; // Server IP address
        int serverPort = 1234; // Server port to connect to

        try {
            // Create client socket and connect to server
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            // Create input stream to receive file
            InputStream inputStream = socket.getInputStream();

            // Create output stream to write file
            FileOutputStream fileOutputStream = new FileOutputStream(savePath);

            // Buffer for data transfer
            byte[] buffer = new byte[4096];
            int bytesRead;

            // Receive file data and write to file
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File received successfully!");

            // Close streams and socket
            fileOutputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
}
// send to server
    public static void SocketFileSend(String filePath) {
        String serverAddress = "127.0.0.1"; // Server IP address
        int serverPort = 1234; // Server port to connect to

        try {
            // Create client socket and connect to server
            Socket socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            // Create input stream to read file
            FileInputStream fileInputStream = new FileInputStream(filePath);

            // Create output stream to send file
            OutputStream outputStream = socket.getOutputStream();

            // Buffer for data transfer
            byte[] buffer = new byte[4096];
            int bytesRead;

            // Read file data and send to server
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully!");

            // Close streams and socket
            outputStream.close();
            fileInputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}