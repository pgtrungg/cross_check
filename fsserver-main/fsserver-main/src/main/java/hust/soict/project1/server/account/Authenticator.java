package hust.soict.project1.server.account;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;


public class Authenticator
{
    public String authenticate(String username, String password) throws FileNotFoundException
    {
        File passwordFile = new File("./passwords.txt");
        File loggedInUser = new File("./auth/users.txt");
        try
        {
            Scanner fileScanner = new Scanner(passwordFile);

            while (fileScanner.hasNextLine())
            {
                String[] line = fileScanner.nextLine().split(" ");

                if (username.equals(line[0]) && password.equals(line[1]))
                {
                    System.out.println("User " + username + " logged in");
                    return generateSessionId(username);
                }
            }

            return "-1";
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return "-1";
        }
    }

    public static String bytesToHex(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String generateSessionId(String username)
    {
        long timestamp = Instant.now().getEpochSecond();

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[64];
        random.nextBytes(bytes);
        String randomString = Arrays.toString(bytes);

        String text = username + Long.toString(timestamp) + randomString;
        String sessionId = "";
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            sessionId = bytesToHex(hash);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return sessionId;
    }
}
