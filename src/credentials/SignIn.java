package credentials;

import java.sql.*;
import java.util.Scanner;

public class SignIn {
    protected String email, password;
    private boolean flag = false;
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

    public SignIn() throws SQLException {
        try {
            System.out.println("Enter your email: ");
            email = scanner.nextLine();
            System.out.println("Enter your password: ");
            password = scanner.nextLine();

            String sqlQuery = "SELECT email, password FROM member";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if (resultSet != null) {
                while (resultSet.next()) {
                    String checkEmail = resultSet.getString("email");
                    String checkPassword = resultSet.getString("password");
                    if (email.equals(checkEmail) && password.equals(checkPassword)) {
                        System.out.println("credentials.SignIn Successful");
                        flag = true;
                    }
                }
            } else System.out.println("Wrong credentials!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean getFlag() {
        return flag;
    }
}
