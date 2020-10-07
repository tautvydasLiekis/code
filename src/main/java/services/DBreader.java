package services;

import config.MySQLqueries;
import model.UserLoginInfo;
import repositories.DBmanager;
import repositories.UserRepo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DBreader {


    private DBmanager dBmanager = new DBmanager();
    private Statement statement = Objects.requireNonNull(dBmanager.getConnectionn().createStatement());
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private UserRepo userRepo=new UserRepo();

    public DBreader() throws IOException, SQLException {
    }

    public void printUserAfterLogin(UserLoginInfo user) throws IOException, SQLException {
        preparedStatement = dBmanager.getConnectionn().prepareStatement(
                MySQLqueries.USER_INFO_BY_LOGIN
        );
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeQuery();
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

            String firstname = resultSet.getString("name");
            String lastname = resultSet.getString("lastname");
            String adress = resultSet.getString("address");
            long phoneNumber = resultSet.getLong("phone_number");
            String email = resultSet.getString("email");

            System.out.println("== USER INFO ==");
            System.out.println(
                    "Name: " + firstname + "\n" +
                            "Surname: " + lastname + "\n" +
                            "Address: " + adress + "\n" +
                            "Phone Number: " + phoneNumber + "\n" +
                            "Email address: " + email
            );
            System.out.println("===============");
        }
        resultSet.close();
        preparedStatement.close();
    }

    public boolean checkLoginInput(UserLoginInfo user) throws IOException, SQLException {
        if (userRepo.getAllUserLoginData().containsKey(user.getUsername()) && userRepo.getAllUserLoginData().containsValue(user.getPassword())){
            System.out.println(String.format("== WELCOME %s! ==", user.getUsername().toUpperCase()));
            return true;
        } else {
            System.out.println("== WRONG USERNAME OR PASSWORD ==");
            return false;
        }
    }
}
