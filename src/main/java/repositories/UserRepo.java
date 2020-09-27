package repositories;

import config.MySQLqueries;
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

    private DBmanager dBmanager=new DBmanager();
    private Statement statement;
    private ResultSet resultSet;


    public HashMap<String, String> getAllUserLoginData() throws IOException, SQLException {
        HashMap<String,String> userLoginInfoList=new HashMap<>();
        statement=Objects.requireNonNull(dBmanager.getConnectionn().createStatement());
        resultSet=statement.executeQuery(
                MySQLqueries.USER_GET_LOGIN
        );
        while (resultSet.next()){
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            userLoginInfoList.put(username, password);

        }
        return userLoginInfoList;
    }
}
