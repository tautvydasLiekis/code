package config;

public class MySQLqueries {

    public static final String USER_INFO_BY_ID="SELECT firstname, lastname, address, cellphone_number, email FROM mydb.user_info WHERE id= ?";
}
//firstname, lastname, address, cellphone_number, email
