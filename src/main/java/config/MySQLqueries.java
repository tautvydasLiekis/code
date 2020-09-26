package config;

public class MySQLqueries {

    public static final String USER_INFO_BY_ID="SELECT firstname, lastname, address, cellphone_number, email FROM mydb.user_info WHERE id= ?";
    //firstname, lastname, address, cellphone_number, email

    public static final String USER_LOGIN="";

    public static final String USER_REGISTER="";

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
