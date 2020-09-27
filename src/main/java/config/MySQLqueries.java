package config;

public class MySQLqueries {

    public static final String USER_INFO_GET="SELECT firstname, lastname, address, cellphone_number, email FROM mydb.user_info";

    public static final String USER_INFO_BY_LOGIN="select mydb.user_info.firstname, mydb.user_info.lastname, mydb.user_info.address, mydb.user_info.cellphone_number, mydb.user_info.email from mydb.user_info inner join mydb.user_login on mydb.user_info.id = mydb.user_login.id \n" +
            "where (mydb.user_login.username=? and mydb.user_login.password=?)";

    public static final String USER_GET_LOGIN="SELECT username, password FROM mydb.user_login";

    public static final String USER_REGISTER_LOGIN="INSERT INTO mydb.user_login (username, password) VALUES (?, ?)";

    public static final String USER_REGISTER_INFO="INSERT INTO mydb.user_info (firstname, lastname, address, cellphone_number, email) VALUES (?, ?, ?, ?, ?)";

    public static final String BALANCE_ADD_TO_ACCOUNT="INSERT INTO mydb.user_account (balance, account_type) VALUES (?, ?)";

    public static final String BALANCE_CHECK="";

    public static final String BALANCE_ADD="";

    public static final String BALANCE_WITHDRAW="";

    public static final String BALANCE_TRANSACTION_ADD="";

    public static final String BALANCE_TRANSACTION_DECREASE="";

    public static final String TRANSACTION_LOG_CHECK="";

    public static final String TRANSACTION_LOG_CHECK_PRINT="";

    public static final String TRANSACTION_LOG_ADD="";

    public static final String CREDIT_CHECK="";

    public static final String CREDIT_ADD="";

    public static final String CREDIT_INTEREST="";
}
