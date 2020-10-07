package config;

public class MySQLqueries {

    public static final String USER_INFO_GET="SELECT name, lastname, address, phone_number, email FROM my_bank.user_info";

    public static final String USER_INFO_BY_LOGIN="SELECT my_bank.user_info.name, my_bank.user_info.lastname, my_bank.user_info.address, my_bank.user_info.phone_number, my_bank.user_info.email " +
            "From (( user_account " +
            "INNER JOIN " +
            "user_info ON user_account.user_Info_id = user_info.id) " +
            "INNER JOIN " +
            "user_login ON user_account.user_Login_id = user_login.id) " +
            "WHERE (user_login.username = ? and user_login.password = ?)";

    public static final String USER_GET_LOGIN="SELECT username, password FROM my_bank.user_login";

    public static final String USER_GET_LOGIN_ID="SELECT id FROM my_bank.user_login";

    public static final String USER_REGISTER_LOGIN="INSERT INTO my_bank.user_login " +
            "(username, password) VALUES (?, ?)";

    public static final String USER_REGISTER_INFO="INSERT INTO my_bank.user_info " +
            "(name, lastname, address, phone_number, email) VALUES (?, ?, ?, ?, ?)";

    public static final String USER_REGISTER_CARD_TYPE="insert into my_bank.account_type " +
            "(type) values (?);";

    public static final String BALANCE_ADD_TO_ACCOUNT="insert into my_bank.user_account " +
            "(balance, user_Login_id, user_Info_id, account_type_id) values (0, ?, ?, ?);";

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
