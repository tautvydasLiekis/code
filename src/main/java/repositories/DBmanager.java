package repositories;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBmanager {

    public Connection mySQLconnection;
    {
        try {
            mySQLconnection = getConnectionn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnectionn() throws IOException {
        try (InputStream input = DBmanager.class.getClassLoader().getResourceAsStream("db.properties")) {

            Properties properties = new Properties();
            properties.load(input);
            try {
                return DriverManager.getConnection(
                        properties.getProperty("db.url"),
                        properties.getProperty("db.username"),
                        properties.getProperty("db.password"));
            } catch (SQLException e) {
                System.out.println("Connection failed" + e.getMessage());
            }
            return null;
        }
    }
}
