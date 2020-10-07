package repositories;

import config.MySQLqueries;
import model.UserInfo;
import model.UserLoginInfo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserRepo {

    private DBmanager dBmanager = new DBmanager();
    private Statement statement;
    private ResultSet resultSet;


    public HashMap<String, String> getAllUserLoginData() throws IOException, SQLException {
        HashMap<String, String> userLoginList = new HashMap<>();
        statement = Objects.requireNonNull(dBmanager.getConnectionn().createStatement());
        resultSet = statement.executeQuery(
                MySQLqueries.USER_GET_LOGIN
        );
        while (resultSet.next()) {
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            userLoginList.put(username, password);
        }
        return userLoginList;
    }

    public List<UserInfo> getAllUserInfoData() throws IOException, SQLException {
        List<UserInfo> userInfoList = new ArrayList<>();
        statement = Objects.requireNonNull(dBmanager.getConnectionn().createStatement());
        resultSet = statement.executeQuery(
                MySQLqueries.USER_INFO_GET
        );
        while (resultSet.next()) {
            String firstName = resultSet.getString(1);
            String lastName = resultSet.getString(2);
            String address = resultSet.getString(3);
            long phoneNumber = resultSet.getLong(4);
            String email = resultSet.getString(5);
            userInfoList.add(new UserInfo(firstName, lastName, address, phoneNumber, email));
        }
        return userInfoList;
    }

    public List<Integer> getAllUserLoginID() throws IOException, SQLException {
        List<Integer> idList=new ArrayList<>();
        statement = Objects.requireNonNull(dBmanager.getConnectionn().createStatement());
        resultSet = statement.executeQuery(
          MySQLqueries.USER_GET_LOGIN_ID
        );
        while (resultSet.next()) {
            int id= resultSet.getInt(1);
            idList.add(id);
        }
        return idList;
    }
}
