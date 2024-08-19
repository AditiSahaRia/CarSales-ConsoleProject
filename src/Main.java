import credentials.SignIn;
import credentials.SignUp;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to CAR SALES.\n" +
                "Press 1 for Sign Up.\n" +
                "Press 2 to Sign in");
        int choice = scanner.nextInt();
        boolean status = false;

        if (choice==1) {
            System.out.println("Sign up page");
            SignUp signUp = new SignUp();
            status = signUp.getSuccess();
        } else if (choice==2) {
            System.out.println("Sign in page");
            SignIn signIn = new SignIn();
            status = signIn.getFlag();
        }

        if (status) {
            System.out.println("Press 3 to exit");
            choice = scanner.nextInt();
            if (choice==3) {
                System.out.println("Good Bye. See you soon!!!");
            }
        }
    }
}