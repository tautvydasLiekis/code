package services;

import config.MySQLqueries;
import model.User;
import repositories.DBmanager;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DBwriter {

    DBmanager dBmanager = new DBmanager();
    PreparedStatement preparedStatement;
    Scanner sc = new Scanner(System.in);

    public void writeNewUserLoginToDB() throws IOException, SQLException {
        preparedStatement = dBmanager.getConnectionn().prepareStatement(
                MySQLqueries.USER_REGISTER_LOGIN
        );
        System.out.println("Enter username: ");
        String username = sc.next();
        try {
            preparedStatement.setString(1, username);
            System.out.println("Enter password: ");
            String password = sc.next();
            preparedStatement.setString(2, password);
        } catch (SQLException e){
            System.out.println("ERROR: this username is already taken");
        }
        preparedStatement.close();
    }

    public void writeNewUserInfoToDB() throws IOException, SQLException {
        preparedStatement=dBmanager.getConnectionn().prepareStatement(
          MySQLqueries.USER_REGISTER_INFO
        );
        String firstname=sc.next();
        preparedStatement.setString(1, firstname);
        String lastname=sc.next();
        preparedStatement.setString(2, lastname);
        String address=sc.next();
        preparedStatement.setString(3, address);
        long phoneNumber=sc.nextLong();
        preparedStatement.setLong(4, phoneNumber);
        String email=sc.next();
        preparedStatement.setString(5, email);
        preparedStatement.close();
    }
}
