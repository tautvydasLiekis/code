package repositories;

import config.MySQLqueries;
import model.UserLoginInfo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepo {

    private DBmanager dBmanager=new DBmanager();
    private Statement statement;
    private ResultSet resultSet;


    public List<UserLoginInfo> getAllUserLoginData() throws IOException, SQLException {
        List<UserLoginInfo> userLoginInfoList=new ArrayList<>();
        statement=Objects.requireNonNull(dBmanager.getConnectionn().createStatement());
        resultSet=statement.executeQuery(
                MySQLqueries.USER_GET_LOGIN
        );
        while (resultSet.next()){
            String username=resultSet.getString(1);
            String password=resultSet.getString(2);
            userLoginInfoList.add(new UserLoginInfo(username,password));
        }
        resultSet.close();
        for (UserLoginInfo print: userLoginInfoList){
            System.out.println(print.toString());
        }
        return userLoginInfoList;
    }
}
