package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class Controller1 {
    public TextField tfFirstName, tfLastName, tfUsername;
    public PasswordField pfPassword;
    public Button btnSignIn, btnSignUp;

    public void signUp() throws SQLException {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        boolean invalidSignUp = false;

        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("All data must be filled!");
            a.show();

            invalidSignUp = true;
        } else if(username.length() < 8 || username.length() > 20) {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("Username must contain between 8-20 characters!");
            a.show();

            invalidSignUp = true;
        } else if(password.length() < 8 || password.length() > 20) {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("Password must contain between 8-20 characters!");
            a.show();

            invalidSignUp = true;
        } else if(!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).+$")) {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("Password must contain at least a lowercase letter, an uppercase letter and a digit!");
            a.show();

            invalidSignUp = true;
        } else {
            String databaseURL = "jdbc:mysql://remotemysql.com:3306/TI0enjmMEx";
            String connectionUsername = "TI0enjmMEx";
            String connectionPassword = "ntxt2lAnne";

            Connection connection = DriverManager.getConnection(databaseURL, connectionUsername, connectionPassword);

            String query = "SELECT username FROM Users";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                if(resultSet.getString("username").equals(username)) {
                    connection.close();

                    Alert a = new Alert(Alert.AlertType.WARNING);

                    a.setTitle("Warning");
                    a.setContentText("Username has been taken!");
                    a.show();

                    invalidSignUp = true;

                    break;
                }
            }
        }

        if(invalidSignUp) {
            return;
        }

        String databaseURL = "jdbc:mysql://remotemysql.com:3306/TI0enjmMEx";
        String connectionUsername = "TI0enjmMEx";
        String connectionPassword = "ntxt2lAnne";

        Connection connection = DriverManager.getConnection(databaseURL, connectionUsername, connectionPassword);

        String query = "INSERT INTO Users VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, username);
        statement.setString(2, firstName);
        statement.setString(3, lastName);
        statement.setString(4, password);

        int i = statement.executeUpdate();

        connection.close();

        Alert a = new Alert(Alert.AlertType.INFORMATION);

        a.setTitle("Information");
        a.setContentText("Sign Up Successful!");
        a.show();
    }

    public void signIn() throws Exception {
        Stage primaryStage;
        Parent root;

        primaryStage = (Stage) btnSignIn.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
