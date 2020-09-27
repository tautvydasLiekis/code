import model.UserLoginInfo;
import repositories.DBmanager;
import services.DBreader;
import services.DBwriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, SQLException {
        DBmanager dBmanager = new DBmanager();
        DBwriter dBwriter = new DBwriter();
        DBreader dBreader = new DBreader();
        Scanner sc = new Scanner(System.in);
        String input = "";
        String input2 = "";
        System.out.println("== WELCOME TO MYBANK ==");
        while (!input.equals("3")) {
            printMenuLogin();
            input = sc.next();
            switch (input) {
                case "1":
                    System.out.print("Please enter your username: ");
                    String username = sc.next();
                    System.out.print("Please enter your password: ");
                    String password = sc.next();
                    if (dBreader.checkLoginInput(new UserLoginInfo(username, password))) {
                        while (!input2.equals("7")) {
                            printMenuAfterLogin();
                            input2 = sc.next();
                            switch (input2) {
                                case "1":
                                    dBreader.printUserAfterLogin(new UserLoginInfo(username, password));
                                    break;
                                case "2":

                                    break;
                                case "3":

                                    break;
                                case "4":

                                    break;
                                case "5":

                                    break;
                                case "6":

                                    break;
                                case "7":

                                    break;
                                default:
                                    System.out.println("Wrong input");
                                    break;
                            }
                        }
                    }
                    break;
                case "2":
                    dBwriter.writeNewUserInfoToDB();
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

    public static void printMenuAfterLogin() {
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

