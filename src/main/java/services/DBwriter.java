package services;

import config.MySQLqueries;
import model.UserLoginInfo;
import repositories.DBmanager;
import repositories.UserRepo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class DBwriter {

    private DBmanager dBmanager = new DBmanager();
    private PreparedStatement preparedStatement;
    private Scanner sc = new Scanner(System.in);
    private UserRepo userRepo = new UserRepo();

    public void writeNewUserLoginToDB() throws IOException, SQLException {
        boolean loop=true;
        preparedStatement = dBmanager.getConnectionn().prepareStatement(
                MySQLqueries.USER_REGISTER_LOGIN
        );
        while (loop) {
            System.out.print("Enter username: ");
            String username = sc.next();
            if (userRepo.getAllUserLoginData().containsKey(username)) {
                System.out.println("ERROR: this username is already taken");
            } else {
                preparedStatement.setString(1, username);
                System.out.print("Enter password: ");
                String password = sc.next();
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                loop=false;
            }
        }
    }

    public void writeNewUserInfoToDB() throws IOException, SQLException {
        preparedStatement = dBmanager.getConnectionn().prepareStatement(
                MySQLqueries.USER_REGISTER_INFO
        );
        String firstname = sc.next();
        preparedStatement.setString(1, firstname);
        String lastname = sc.next();
        preparedStatement.setString(2, lastname);
        String address = sc.next();
        preparedStatement.setString(3, address);
        long phoneNumber = sc.nextLong();
        preparedStatement.setLong(4, phoneNumber);
        String email = sc.next();
        preparedStatement.setString(5, email);
        preparedStatement.close();
    }
}
