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
            if (emailCheck()) {
                System.out.println("Enter your password: \n" +
                        "Password must contain at least one uppercase " +
                        "letter, one lowercase letter, one number, one special " +
                        "character and length must be at least 8.");
                password = scanner.nextLine();
                password = new CheckPassword().checkPassword(password);
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
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private boolean emailCheck() throws SQLException {
        while (true) {
            boolean valid = checkValidEmail(email);
            if (valid) {
                boolean emailExists = checkIfEmailExists(email);
                if (!emailExists) return true;
                System.out.println("This Email already Exists. Kindly login or enter a new Email");
                System.out.println("Press 1 for SignIn. \nPress 2 for continuing registration with a new Email");
                System.out.print("Enter: ");
                int num = scanner.nextInt();
                if (num==1) {
                    System.out.println("Sign in page");
                    new SignIn();
                    return false;
                }
            }
            System.out.println("Enter a valid email: \nExample: adt@g.com\nHere: ");
            email = scanner.nextLine();
        }
    }
    private boolean checkValidEmail(String email) {
        return Pattern.compile("@").matcher(email).find();
    }

    private boolean checkIfEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM member WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count==1) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getSuccess() {
        return isSuccess;
    }
}
