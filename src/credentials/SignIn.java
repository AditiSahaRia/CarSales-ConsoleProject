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

            String sqlQuery = "SELECT COUNT(*) FROM member WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count==1) {
                    System.out.println("sign in Successful");
                    flag = true;
                } else {
                    System.out.println("Wrong credentials!");
                    while (true) {
                        System.out.println("To try again enter 1\n" +
                                "Forgot Password? To reset enter 2\n" +
                                "Not Registered? To sign up enter 3");
                        int choice = scanner.nextInt();
                        if (choice == 1) {
                            new SignIn();
                            return;
                        } else if (choice == 2) {
                            boolean done = new ResetPassword().isReset();
                            if (done) {
                                new SignIn();
                                return;
                            }
                        } else {
                            new SignUp();
                            return;
                        }
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean getFlag() {
        return flag;
    }
}
