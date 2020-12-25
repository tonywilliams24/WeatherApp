package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static String dbhost = "jdbc:mysql://localhost:3306/locationsDb";
    private static String username = "root";
    private static String password = "notStrongRootPass";
    private static Connection connection;

    public static Connection createNewDbConnection() {
        try{
            connection = DriverManager.getConnection(dbhost, username, password);
            return connection;
        }
        catch (SQLException e) {
            System.out.println("Connection Error");
            return null;
        }
    }

}
