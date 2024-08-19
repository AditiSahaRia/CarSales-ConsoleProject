package credentials;

import java.util.Scanner;
import java.util.regex.Pattern;

public class CheckPassword {
    Scanner scanner = new Scanner(System.in);

    public String checkPassword(String pass) {
        while (true) {
            boolean result = isPasswordValid(pass);
            if (result) return pass;
            System.out.println("Password must contain at least one uppercase " +
                    "letter, one lowercase letter, one number, and one special character.");
            pass = scanner.nextLine();
        }
    }

    public boolean isPasswordValid(String password) {
        boolean length = true;
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        if (password.length()<8) length = false;
        return length && hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
