package hust.soict.project1.server.sql;

import java.sql.*;

public class SQLConnection
{
    private static String database;
    private static String username;
    private static String password;

    public SQLConnection(String database, String username, String password)
    {
        this.database = database;
        this.username = username;
        this.password = password;
    }

//    public static Connection connectToDatabase()
//    {
//        String connectionUrl =
//                "jdbc:sqlserver://yourserver.database.windows.net:1433;"
//                        + "database=" + database + ";"
//                        + "user=" + username + ";"
//                        + "password=" + password + ";"
//                        + "encrypt=true;"
//                        + "trustServerCertificate=false;"
//                        + "loginTimeout=30;";
//
//        try (Connection connection = DriverManager.getConnection(connectionUrl))
//        {
//            return connection;
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//    }
}
