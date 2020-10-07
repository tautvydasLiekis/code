package services;

import config.MySQLqueries;
import repositories.DBmanager;
import repositories.UserRepo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DBwriter {

    private DBmanager dBmanager = new DBmanager();
    private PreparedStatement preparedStatement;
    private Scanner sc = new Scanner(System.in);
    private UserRepo userRepo = new UserRepo();


    public void writeNewUserToDB() throws SQLException {
        try {
            dBmanager.mySQLconnection.setAutoCommit(false);
            writeNewUserLoginToDB();
            writeNewUserInfoToDB();
            writeNewUserCardType();
        } catch (SQLException e){
            dBmanager.mySQLconnection.rollback();
        }
    }

    private void writeNewUserLoginToDB() throws SQLException {
        try {
            preparedStatement = dBmanager.mySQLconnection.prepareStatement(
                    MySQLqueries.USER_REGISTER_LOGIN
            );
//            dBmanager.mySQLconnection.setAutoCommit(false);
            System.out.print("Enter username: ");
            String username = sc.next();
            if (!userRepo.getAllUserLoginData().containsKey(username)) {
                preparedStatement.setString(1, username);
                System.out.print("Enter password: ");
                String password = sc.next();
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
//                dBmanager.mySQLconnection.commit();
                preparedStatement.close();
            } else {
                System.out.println("ERROR: this username is already taken");
            }
        } catch (SQLException | IOException e) {
//            dBmanager.mySQLconnection.rollback();
        }
    }

    private void writeNewUserInfoToDB() throws SQLException {
        try {
            preparedStatement = dBmanager.mySQLconnection.prepareStatement(
                    MySQLqueries.USER_REGISTER_INFO
            );
//            dBmanager.mySQLconnection.setAutoCommit(false);
            System.out.print("Enter your name: ");
            String firstname = sc.next();
            preparedStatement.setString(1, firstname);
            System.out.print("Enter your lastname: ");
            String lastname = sc.next();
            preparedStatement.setString(2, lastname);
            System.out.print("Enter your address: ");
            String address = sc.next().concat(sc.nextLine());
            preparedStatement.setString(3, address);
            System.out.print("Enter your phone number: ");
            long phoneNumber = sc.nextLong();
            preparedStatement.setLong(4, phoneNumber);
            System.out.print("Enter your email address: ");
            String email = sc.next();
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
//            dBmanager.mySQLconnection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
//            dBmanager.mySQLconnection.rollback();
        }
    }

    private void writeNewUserCardType() throws SQLException {
        try {
            preparedStatement = dBmanager.mySQLconnection.prepareStatement(
                    MySQLqueries.USER_REGISTER_CARD_TYPE
            );
//            dBmanager.mySQLconnection.setAutoCommit(false);
            System.out.println("[0] Debit" + "\n" + "[1] Credit");
            System.out.print("Enter which card type do you want: ");
            byte cardType = sc.nextByte();
            preparedStatement.setByte(1, cardType);
//            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
//            dBmanager.mySQLconnection.rollback();
        }
    }

    public void writeNewUserBalance() throws SQLException {
        try {
            preparedStatement = dBmanager.mySQLconnection.prepareStatement(
                    MySQLqueries.BALANCE_ADD_TO_ACCOUNT
            );
            dBmanager.mySQLconnection.setAutoCommit(false);
            preparedStatement.setInt(1, 20);
            preparedStatement.executeUpdate();
            dBmanager.mySQLconnection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            dBmanager.mySQLconnection.rollback();
        }
    }

    public void newTransaction() {

    }
}
