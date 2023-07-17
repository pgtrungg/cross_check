package hust.soict.project1.server.files;

import hust.soict.project1.server.transporter.FileTransporter;
import hust.soict.project1.server.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler
{
    private final int port = Main.getPort();
    private FileTransporter transporter = new FileTransporter();
    private String currentDir;
    private String homeDir;

    public String getCurrentDir()
    {
        return currentDir;
    }

    public String getHomeDir()
    {
        return homeDir;
    }

    public FileHandler(String username) throws IOException
    {
        homeDir = "./" + username;
        File homeDirFile = new File(homeDir);

        if (!homeDirFile.exists())
        {
            try
            {
                homeDirFile.mkdir();
            }
            catch (SecurityException e)
            {
                System.out.println("fatal: Error creating home directory for user " + username);
                System.exit(1);
            }
        }

        currentDir = homeDir;
    }

    public void handleFileUpload(String fileName)
    {
        String filePath = currentDir + "/" + fileName;

        transporter.receiveFile(filePath);
    }

    public void handleFileDownload(String fileName)
    {
        String filePath = currentDir + "/" + fileName;

        transporter.sendFile(filePath);
    }

    public void handleFileDelete(String fileName)
    {
        String filePath = currentDir + "/" + fileName;

        File file = new File(filePath);

        if (file.exists())
        {
            file.delete();
        }
    }

    public boolean handleDirChange(String newDir)
    {
        String tempDir = currentDir + "/" + newDir;
        if (isSubDir(tempDir))
        {
            currentDir = tempDir;
            return true;
        }
        return false;
    }

    public boolean isSubDir(String dirToCheck)
    {
        Path dirToCheckPath = Paths.get(dirToCheck).normalize().toAbsolutePath();
        Path homePath = Paths.get(homeDir).normalize().toAbsolutePath();

        return dirToCheckPath.startsWith(homePath);
    }

    public String handleDirList(String dir)
    {
        StringBuilder sb = new StringBuilder();

        String[] files = new File(dir).list();

        for (String item : files)
        {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }
}
