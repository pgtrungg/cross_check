package hust.soict.project1.client.juan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Juanportertest
{
    public boolean SocketLogin(String ip, int port, String username, String password) throws IOException 
    {
        try (Socket socket = new Socket(ip, port)) {
            // code that uses the socket goes here
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            // use the input and output streams as you need to
            String message = (username + " " + password);
            output.write(message.getBytes());
            output.flush();
            socket.shutdownOutput();

            // read the response
            byte[] buffer = new byte[2048];
            int length = input.read(buffer);
            String response = new String(buffer, 0, length);
            System.out.println("Response: " + response);
            if (response.equals("OK")) {
                // the server accepted the credentials
                System.out.println("Credentials Accepted");
                return true;
            } else {
                // the server rejected the credentials
                System.out.println("Invalid username or password");
                return false;
            }

        } catch (IOException e) {
            // handle the exception
            System.out.println("Error");
            return false;
        }
    }


    public boolean SocketFileReceive(String ip, int port, String filename) throws IOException
    {
        try (Socket socket = new Socket(ip, port)) {
            // code that uses the socket goes here
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            // use the input and output streams as you need to
            String message = ("FILE " + filename);
            output.write(message.getBytes());
            output.flush();
            socket.shutdownOutput();

            // read the response
            byte[] buffer = new byte[2048];
            int length = input.read(buffer);
            String response = new String(buffer, 0, length);
            System.out.println("Response: " + response);
            if (response.equals("OK")) {
                // the server accepted the credentials
                System.out.println("File Request Accepted");
                return true;
            } else {
                // the server rejected the credentials
                System.out.println("Invalid File Request");
                return false;
            }

        } catch (IOException e) {
            // handle the exception
            System.out.println("Error");
            return false;
        }
    }

    public boolean SocketFileSend(String ip, int port, String filename) throws IOException
    {
        try (Socket socket = new Socket(ip, port)) {
            // code that uses the socket goes here
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            // use the input and output streams as you need to
            String message = ("FILE " + filename);
            output.write(message.getBytes());
            output.flush();
            socket.shutdownOutput();

            // read the response
            byte[] buffer = new byte[2048];
            int length = input.read(buffer);
            String response = new String(buffer, 0, length);
            System.out.println("Response: " + response);
            if (response.equals("OK")) {
                // the server accepted the credentials
                System.out.println("File Request Accepted");
                return true;
            } else {
                // the server rejected the credentials
                System.out.println("Invalid File Request");
                return false;
            }

        } catch (IOException e) {
            // handle the exception
            System.out.println("Error");
            return false;
        }
    }
}

