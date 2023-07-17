package hust.soict.project1.server.parser;

import hust.soict.project1.server.transporter.MsgTransporter;
import hust.soict.project1.server.transporter.Transporter;
import org.json.JSONObject;

import hust.soict.project1.server.account.*;
import hust.soict.project1.server.Main;
import hust.soict.project1.server.files.FileHandler;
import hust.soict.project1.server.account.AuthStore;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MsgParser
{
    private static int port = Main.getPort();
    private FileHandler fileHandler;
    private AuthStore authStore;
    private MsgTransporter transporter = Main.getMsgTransporter();
    private JSONObject response;

    public JSONObject getResponse()
    {
        return response;
    }

    public MsgParser() throws IOException
    {
        transporter = Main.getMsgTransporter();
        authStore = Main.getAuthStore();
    }

    public void parseMsg(JSONObject msg) throws IOException
    {
        String requestType = msg.getString("request");

        switch (requestType)
        {
            case "login" ->
            {
                String username = msg.getString("username");
                String password = msg.getString("password");

                Authenticator auth = new Authenticator();

                String sessionId = auth.authenticate(username, password);

                response = new JSONObject();
                response.put("response", "login");

                if (sessionId.equals("-1"))
                {
                    response.put("status", "failed");
                    transporter.sendToClient(response.toString());
                }
                else
                {
                    response.put("status", "success");
                    response.put("sessionId", sessionId);

                    authStore.storeUser(username, sessionId);
                    transporter.sendToClient(response.toString());

                    fileHandler = new FileHandler(username);
                }
            }

            case "logout" ->
            {
                String username = msg.getString("username");
                String sessionId = msg.getString("sessionId");

                System.out.println("User " + username + " logged out");
            }

            case "cd" ->
            {
                String newDir = msg.getString("newDir");

                response = new JSONObject();

                boolean result = fileHandler.handleDirChange(newDir);

                if (!result)
                {
                    response.put("status", "failed");
                }
                else
                {
                    response.put("status", "success");
                }

                transporter.sendToClient(response.toString());
            }

            case "download" ->
            {
                String fileName = msg.getString("filename");

                response = new JSONObject();
                response.put("response", "upload");
                response.put("status", "ready");

                fileHandler.handleFileDownload(fileName);

                transporter.sendToClient("Success");
            }

            case "upload" ->
            {
                String fileName = msg.getString("filename");

                response = new JSONObject();
                response.put("response", "upload");
                response.put("status", "ready");

                transporter.sendToClient(response.toString());

                fileHandler.handleFileUpload(fileName);

                transporter.sendToClient("Success");
            }

            case "list" ->
            {
                String result = fileHandler.handleDirList(fileHandler.getCurrentDir());

                response = new JSONObject();

                response.put("response", "list");
                response.put("result", result);

                transporter.sendToClient(response.toString());
            }
            case "delete" ->
            {
                String fileName = msg.getString("filename");

                fileHandler.handleFileDelete(fileName);

                transporter.sendToClient("Success");
            }

            default ->
            {
                response = new JSONObject();
                response.put("response", "invalid");

                transporter.sendToClient(response.toString());
            }
        }
    }
}
