package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller3 implements Initializable {
    public TableView userRatingsTableView;
    public TextField reviewTextField, ratingTextField;
    public Button saveButton, btnBack;
    public Text movieTitleText, movieRatingText;
    public Controller userNameLogged = new Controller();
    public Controller2 selectedMovieInfo = new Controller2();

    ObservableList<Review> userReviews = FXCollections.observableArrayList();

    public void populateUserRatingsTableView(String movieNameSelected) {
        String databaseURL = "jdbc:mysql://remotemysql.com:3306/TI0enjmMEx";
        String connectionUsername = "TI0enjmMEx";
        String connectionPassword = "ntxt2lAnne";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseURL, connectionUsername, connectionPassword);
            String query = "SELECT username, description, rating FROM Reviews where movie_title = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movieNameSelected);

            ResultSet resultSetReviews = statement.executeQuery();


            while (resultSetReviews.next())
            {
                userReviews.add(new Review(resultSetReviews.getString("username"), resultSetReviews.getString("description"), resultSetReviews.getDouble("rating")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        movieTitleText.setText(movieNameSelected);

        try {
            String selectNewRating = "SELECT rating FROM Movies WHERE movie_title = ? ";
            PreparedStatement statement2 = connection.prepareStatement(selectNewRating);

            statement2.setString(1, movieTitleText.getText());

            ResultSet rsNewRating = statement2.executeQuery();
            Double newRating = 0.0;

            while (rsNewRating.next()) {
                newRating = rsNewRating.getDouble("rating");
            }

            movieRatingText.setText(String.valueOf(newRating));
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void saveButtonClicked() throws Exception {
        if(reviewTextField.getText().isEmpty() && ratingTextField.getText().isEmpty())
        {
            Alert a =  new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning!");
            a.setContentText("Please Fill Review and Rating field");
            a.show();
        }
        else if(reviewTextField.getText().isEmpty())
        {
            Alert a =  new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning!");
            a.setContentText("Please Fill Review field!");
            a.show();

        }
        else if(ratingTextField.getText().isEmpty())
        {
            Alert a =  new Alert(Alert.AlertType.WARNING);
            a.setTitle("Warning!");
            a.setContentText("Please Fill Rating field!");
            a.show();

        }
        else
        {
            if(!isDouble(ratingTextField)){
                ratingTextField.clear();
                Alert a = new Alert(Alert.AlertType.WARNING);

                a.setTitle("Warning!");
                a.setContentText("Please Fill Numbers Only in Rating field!");
                a.show();

            }
            else if(reviewTextField.getText().length() > 500)
            {
                reviewTextField.clear();
                Alert a = new Alert(Alert.AlertType.WARNING);

                a.setTitle("Warning!");
                a.setContentText("Review Cannot be More Than 500 Characters!");
                a.show();
            }
            else
            {
                try {
                    String databaseURL = "jdbc:mysql://remotemysql.com:3306/TI0enjmMEx";
                    String connectionUsername = "TI0enjmMEx";
                    String connectionPassword = "ntxt2lAnne";

                    Connection connection = DriverManager.getConnection(databaseURL, connectionUsername, connectionPassword);

                    String insertQuery = "INSERT into Reviews (username, description, rating, movie_title) values (?,?,?,?)";

                    PreparedStatement statement = connection.prepareStatement(insertQuery);
                    statement.setString(1, Controller.userLogged);
                    statement.setString(2, reviewTextField.getText());
                    statement.setString(3, ratingTextField.getText());
                    statement.setString(4, movieTitleText.getText());

                    Integer saveStatus = statement.executeUpdate();
                    if (saveStatus > 0) {
                        reviewTextField.clear();
                        ratingTextField.clear();
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Success Save!");
                        a.setContentText("Success save review and rating!");
                        a.show();
                    }

                    String updateQuery = "UPDATE Movies SET rating = (SELECT AVG(rating) FROM Reviews WHERE movie_title = ?) WHERE movie_title = ?";
                    PreparedStatement statement1 = connection.prepareStatement(updateQuery);

                    statement1.setString(1, movieTitleText.getText());
                    statement1.setString(2, movieTitleText.getText());

                    Integer updateRatingStatus = statement1.executeUpdate();
                    if (updateRatingStatus > 0) {
                        System.out.println("Success update rating");

                        Stage primaryStage;
                        Parent root;


                        primaryStage = (Stage) saveButton.getScene().getWindow();
                        root = FXMLLoader.load(getClass().getResource("sample3.fxml"));

                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();
                    }
                    connection.close();
                }catch (SQLException e)
                {
                    System.out.println(e);
                }
            }
        }
    }

    public boolean isDouble(TextField input) {
        //Using Exception handling try catch to validate the input of a text field
        try {
            Double.parseDouble(input.getText());
            Integer.parseInt(input.getText());
            //return true if the parse process is success
            return true;

        } catch (NumberFormatException exception) {
            //return false if there is an error while parsing
            return false;
        }
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {


        this.populateUserRatingsTableView(selectedMovieInfo.movieSelected.get(0).getMovieTitle());

        TableColumn userName = new TableColumn("Username");
        userName.setMinWidth(200);
        userName.setCellValueFactory(
                new PropertyValueFactory<Review, String>("userName"));

        TableColumn description = new TableColumn("Description");
        description.setMinWidth(500);
        description.setCellValueFactory(
                new PropertyValueFactory<Review, String>("description"));
        TableColumn rating = new TableColumn("Rating");
        rating.setMinWidth(200);
        rating.setCellValueFactory(
                new PropertyValueFactory<Review, String>("ratings"));

        userRatingsTableView.setItems(userReviews);
        userRatingsTableView.getColumns().addAll(userName, description, rating);

    }

    public void back() throws Exception
    {
        Stage primaryStage;
        Parent root;

        primaryStage = (Stage) btnBack.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("sample2.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
