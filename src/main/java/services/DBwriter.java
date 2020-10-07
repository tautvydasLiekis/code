package services;

import config.MySQLqueries;
import repositories.DBmanager;
import repositories.UserRepo;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DBwriter {

    private final DBmanager dBmanager = new DBmanager();
    private PreparedStatement preparedStatement;
    private final Scanner sc = new Scanner(System.in);
    private final UserRepo userRepo = new UserRepo();


    public void writeNewUsertoDBbatch() throws SQLException, IOException {
        try {
            PreparedStatement preparedStatementLoginData = dBmanager.mySQLconnection.prepareStatement(
                    MySQLqueries.USER_REGISTER_LOGIN
            );
            PreparedStatement preparedStatementInfoData = dBmanager.mySQLconnection.prepareStatement(
                    MySQLqueries.USER_REGISTER_INFO
            );
            PreparedStatement preparedStatementCardTypeData = dBmanager.mySQLconnection.prepareStatement(
                    MySQLqueries.USER_REGISTER_CARD_TYPE
            );

            dBmanager.mySQLconnection.setAutoCommit(false);

            //login
            userLoginRegistration(preparedStatementLoginData);

            //info
            userInfoRegistration(preparedStatementInfoData);

            //card type
            userCardTypeRegistration(preparedStatementCardTypeData);

            //end input all (exc acc)
            dBmanager.mySQLconnection.commit();

            //account
            userAccountRegistration();
            //end auto commit for acc

        } catch (SQLException e) {
            dBmanager.mySQLconnection.rollback();
        } finally {
            System.out.println("---------------------------");
            System.out.println("|  REGISTRATION COMPLETE  |");
            System.out.println("---------------------------");
        }
    }

    private void userLoginRegistration(PreparedStatement preparedStatementLoginData) throws IOException, SQLException {
        while (true) {
            System.out.print("Enter username: ");
            String username = sc.next();
            if (!userRepo.getAllUserLoginData().containsKey(username)) {
                preparedStatementLoginData.setString(1, username);
                System.out.print("Enter password: ");
                String password = sc.next();
                preparedStatementLoginData.setString(2, password);
                preparedStatementLoginData.addBatch();
                preparedStatementLoginData.executeBatch();
                break;
            } else {
                System.out.println("ERROR: this username is already taken");
            }
        }
    }

    private void userInfoRegistration(PreparedStatement preparedStatementInfoData) throws SQLException {
        System.out.print("Enter your name: ");
        String firstname = sc.next();
        preparedStatementInfoData.setString(1, firstname);
        System.out.print("Enter your lastname: ");
        String lastname = sc.next();
        preparedStatementInfoData.setString(2, lastname);
        System.out.print("Enter your address: ");
        String address = sc.next().concat(sc.nextLine());
        preparedStatementInfoData.setString(3, address);
        System.out.print("Enter your phone number: ");
        long phoneNumber = sc.nextLong();
        preparedStatementInfoData.setLong(4, phoneNumber);
        System.out.print("Enter your email address: ");
        String email = sc.next();
        preparedStatementInfoData.setString(5, email);
        preparedStatementInfoData.addBatch();
        preparedStatementInfoData.executeBatch();
    }

    private void userCardTypeRegistration(PreparedStatement preparedStatementCardTypeData) throws SQLException {
        System.out.println("[0] Debit" + "\n" + "[1] Credit");
        System.out.print("Enter which card type do you want: ");
        byte cardType = sc.nextByte();
        preparedStatementCardTypeData.setByte(1, cardType);
        preparedStatementCardTypeData.addBatch();
        preparedStatementCardTypeData.executeBatch();
    }

    private void userAccountRegistration() throws SQLException, IOException {

        PreparedStatement preparedStatementAccountData = dBmanager.mySQLconnection.prepareStatement(
                MySQLqueries.BALANCE_ADD_TO_ACCOUNT
        );
        dBmanager.mySQLconnection.setAutoCommit(false);
        while (true) {
            int setID = 0;
            if (userRepo.getAllUserLoginID().isEmpty()) {
                setID = 1;
            } else if (userRepo.getAllUserLoginID().get(0) == 1 && userRepo.getAllUserLoginID().size() == 1) {
                setID = 1;
            } else if (userRepo.getAllUserLoginID().size() > 1) {
                setID = userRepo.getAllUserLoginID().get(userRepo.getAllUserLoginID().size() - 1);
            }
            preparedStatementAccountData.setInt(1, setID);
            preparedStatementAccountData.setInt(2, setID);
            preparedStatementAccountData.setInt(3, setID);
            preparedStatementAccountData.addBatch();
            preparedStatementAccountData.executeBatch();
            break;
        }
        //end auto
        dBmanager.mySQLconnection.commit();
    }

    public void newTransaction() {

    }
}
