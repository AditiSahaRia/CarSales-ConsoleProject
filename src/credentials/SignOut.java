package credentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class SignOut extends SignIn {
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

    public SignOut() throws SQLException {
        super();
        if (!getFlag()) System.out.println("Your are not signed in!");
        else {
            email = "";
            password = "";
            System.out.println("Sing out successful");
        }
    }
}
