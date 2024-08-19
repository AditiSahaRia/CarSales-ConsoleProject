package credentials;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SignUp {
    private String name, email, password, photoName;
    private int mobile;
    private boolean isSuccess = false;
    Scanner scanner = new Scanner(System.in);
    Connection connection;

    {
        try {
            String url = "jdbc:mysql://localhost:3306/carsale";
            connection = DriverManager.getConnection(url, "springstudent", "springstudent");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public SignUp() throws SQLException {
        try {
            System.out.print("Enter your name: ");
            name = scanner.nextLine();
            System.out.print("Enter your email: ");
            email = scanner.nextLine();
            while (true) {
                boolean valid = checkValidEmail(email);
                if (valid) {
                    boolean emailExists = checkIfEmailExists(email);
                    if (!emailExists) break;
                    System.out.println("This Email already Exists. Kindly login or enter a new Email");
                    System.out.println("Press 1 for credentials.SignIn. \nPress 2 for continuing registration with a new Email");
                    System.out.print("Enter: ");
                    int num = scanner.nextInt();
                    if (num==1) {
                        System.out.println("Sign in page");
                        new SignIn();
                        return;
                    }
                }
                System.out.println("Enter a valid email: \nExample: adt@g.com\nHere: ");
                email = scanner.nextLine();
            }
            System.out.println("Enter your password: \n" +
                    "Password must contain at least one uppercase " +
                    "letter, one lowercase letter, one number, and one special character.");
            password = scanner.nextLine();
            while (true) {
                boolean result = checkPassword(password);
                if (result) break;
                System.out.println("Password must contain at least one uppercase " +
                        "letter, one lowercase letter, one number, and one special character.");
                password = scanner.nextLine();
            }
            System.out.print("Enter your photo name: ");
            photoName = scanner.nextLine();
            System.out.print("Enter your mobile: ");
            mobile = scanner.nextInt();

            String sqlQuery = "INSERT INTO member(name, email, password, photo, mobile) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, photoName);
            preparedStatement.setInt(5, mobile);

            preparedStatement.execute();
            System.out.println("Registration Successful");
            isSuccess = true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private boolean checkValidEmail(String email) {
        return Pattern.compile("@").matcher(email).find();
    }

    private boolean checkIfEmailExists(String email) {
        String query = "SELECT email FROM member";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet != null) {
                while (resultSet.next()) {
                    String existingEmail = resultSet.getString("email");
                    if (email.equals(existingEmail)) return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkPassword(String password) {
        boolean length = true;
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(password).find();
        boolean hasLower = Pattern.compile("[a-z]").matcher(password).find();
        boolean hasDigit = Pattern.compile("[0-9]").matcher(password).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
        if (password.length()<8) length = false;
        return length && hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public boolean getSuccess() {
        return isSuccess;
    }
}
