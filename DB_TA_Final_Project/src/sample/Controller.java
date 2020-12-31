package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Controller {
    public TextField tfUsername;
    public PasswordField pfPassword;
    public Button btnSignIn, btnSignUp;

    public void signIn() throws Exception {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if(username.isEmpty() || password.isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("All data must be filled!");
            a.show();
        } else {
            String databaseURL = "jdbc:mysql://remotemysql.com:3306/TI0enjmMEx";
            String connectionUsername = "TI0enjmMEx";
            String connectionPassword = "ntxt2lAnne";

            Connection connection = DriverManager.getConnection(databaseURL, connectionUsername, connectionPassword);

            String query = "SELECT username, password FROM Users";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("username").equals(username) && resultSet.getString("password").equals(password)) {
                    connection.close();

                    Stage primaryStage;
                    Parent root;

                    primaryStage = (Stage) btnSignIn.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("sample2.fxml"));

                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();

                    return;
                }
            }

            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Warning");
            a.setContentText("Invalid username and/or password!");
            a.show();
        }
    }

    public void signUp() throws Exception {
        Stage primaryStage;
        Parent root;

        primaryStage = (Stage) btnSignUp.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("sample1.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
