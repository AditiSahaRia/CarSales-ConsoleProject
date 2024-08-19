package credentials;

import java.sql.*;
import java.util.Scanner;

public class ResetPassword {
    protected String email, mobile, password;
    private boolean isReset;
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

    public ResetPassword() throws SQLException {
        try {
            System.out.println("Enter your email: ");
            email = scanner.nextLine();
            System.out.println("Enter your mobile: ");
            mobile = scanner.nextLine();

            System.out.println("Enter your new password: ");
            password = scanner.nextLine();
            password = new CheckPassword().checkPassword(password);

            String sqlQuery = "UPDATE member SET password = ? WHERE email = ? AND mobile = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, password);
            statement.setString(2, email);
            statement.setString(3, mobile);
            int count = statement.executeUpdate();
            if (count==1) {
                System.out.println("Password Reset Successful");
                isReset = true;
            } else System.out.println("Something Went Wrong! Try Again!!!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public boolean isReset() {
        return isReset;
    }
}
