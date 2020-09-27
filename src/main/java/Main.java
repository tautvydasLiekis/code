import model.UserLoginInfo;
import repositories.UserRepo;
import services.DBreader;
import services.DBwriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, SQLException {
        DBreader dBreader=new DBreader();

        Scanner sc = new Scanner(System.in);
        String input = "";
        System.out.println("== WELCOME TO MYBANK ==");
        while (!input.equals("3")) {
            printMenuLogin();
            input = sc.next();
            switch (input) {
                case "1":
                    System.out.println("Please enter your username");
                    String username=sc.next();
                    System.out.println("Please enter your password");
                    String password=sc.next();
                    if (dBreader.checkLoginInput(new UserLoginInfo(username, password))){

                    }
                    break;
                case "2":

                    break;
                case "3":

                    break;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }
    }

    public static void printMenuLogin() {
        System.out.println(
                "[1] Login" + "\n" +
                        "[2] Register" + "\n" +
                        "[3] EXIT"
        );
    }

    public static void printMenuAfterLoginDebit() {
        System.out.println(
                "[1] Account details" + "\n" +
                        "[2] Check balance" + "\n" +
                        "[3] Transfers & Payments" + "\n" +
                        "[4] Deposit" + "\n" +
                        "[5] Withdrawal" + "\n" +
                        "[6] Save Transfers & Payments" + "\n" +
                        "[7] Log Off"
        );
    }

    public static void printMenuAfterLoginCredit() {
        System.out.println(
                "[1] Account details" + "\n" +
                        "[2] Check balance" + "\n" +
                        "[3] Transfers & Payments" + "\n" +
                        "[4] Deposit" + "\n" +
                        "[5] Withdrawal" + "\n" +
                        "[6] Save Transfers & Payments" + "\n" +
                        "[7] Log Off"
        );
    }

}
