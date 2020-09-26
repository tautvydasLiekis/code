package services;

import com.mysql.cj.jdbc.PreparedStatementWrapper;
import config.MySQLqueries;
import repositories.DBmanager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class DBreader {

    DBmanager dBmanager = new DBmanager();
    //Statement statement= Objects.requireNonNull(dBmanager.getConnectionn().createStatement());
    ResultSet resultSet;
    PreparedStatement preparedStatement;

    public DBreader() throws IOException, SQLException {
    }

    public void printUserInfo(int id) throws IOException, SQLException {
        preparedStatement = dBmanager.getConnectionn().prepareStatement(
                MySQLqueries.USER_INFO_BY_ID
        );
        preparedStatement.setInt(1, id);
        preparedStatement.executeQuery();
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {

            String firstname = resultSet.getString("firstname");
            String lastname = resultSet.getString("lastname");
            String adress = resultSet.getString("address");
            long phoneNumber = resultSet.getLong("cellphone_number");
            String email = resultSet.getString("email");

            System.out.println("== USER INFO ==");
            System.out.println("Name: "+firstname + "\n" +
                    "Surname: " +lastname + "\n" +
                    "Address: " + adress + "\n" +
                    "Phone Number: " +phoneNumber + "\n" +
                    "Email address: " + email);
        }
        resultSet.close();
        preparedStatement.close();
    }
}
