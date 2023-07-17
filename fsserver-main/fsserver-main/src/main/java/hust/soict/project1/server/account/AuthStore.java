package hust.soict.project1.server.account;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthStore
{
    private File authUsersFile;

    public AuthStore() throws IOException
    {
        authUsersFile = new File("./auth/users.txt");
        if (!authUsersFile.exists())
        {
            File parentDir = authUsersFile.getParentFile();

            if (!parentDir.exists())
            {
                parentDir.mkdirs();
            }
        }

        authUsersFile.createNewFile();
    }

    public void storeUser(String username, String sessionId)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(authUsersFile, true);
            PrintWriter filePW = new PrintWriter(fileWriter);

            filePW.write(username + " " + sessionId + "\n");
            filePW.close();
            fileWriter.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void cleanUp()
    {
        authUsersFile.deleteOnExit();
    }
}
