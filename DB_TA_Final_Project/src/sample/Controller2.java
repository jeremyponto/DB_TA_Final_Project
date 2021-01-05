package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller2 implements Initializable {

    public TableView movieListTableView;
    public Text userNameLoggedInText;
    public Button btnSignOut;

    static ObservableList<Movie> movies = FXCollections.observableArrayList();
    static ObservableList<Movie> movieSelected = FXCollections.observableArrayList();
    public Controller userNameLogged = new Controller();


    public void populateMovieList()
    {
        String databaseURL = "jdbc:mysql://remotemysql.com:3306/TI0enjmMEx";
        String connectionUsername = "TI0enjmMEx";
        String connectionPassword = "ntxt2lAnne";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseURL, connectionUsername, connectionPassword);
            String query = "SELECT * FROM Movies";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSetMovies = statement.executeQuery();

            while (resultSetMovies.next())
            {
                movies.add(new Movie(resultSetMovies.getString("movie_title"), resultSetMovies.getDouble("rating")));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        userNameLoggedInText.setText(userNameLogged.userLogged);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        this.populateMovieList();

        TableColumn movieName = new TableColumn("Movies");
        movieName.setMinWidth(400);
        movieName.setCellValueFactory(
                new PropertyValueFactory<Movie, String>("movieTitle"));

        TableColumn movieRating = new TableColumn("Ratings");
        movieRating.setMinWidth(200);
        movieRating.setCellValueFactory(
                new PropertyValueFactory<Movie, Double>("ratings"));


        movieListTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        movieListTableView.setItems(movies);

        movieListTableView.getColumns().addAll(movieName, movieRating);
    }

    public void clickMovieListTableView() throws Exception
    {
        movieListTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2)
                {
                    movieSelected =  movieListTableView.getSelectionModel().getSelectedItems();
                    Stage primaryStage;
                    Parent root;

                    primaryStage = (Stage) movieListTableView.getScene().getWindow();
                    try {
                        root = FXMLLoader.load(getClass().getResource("sample3.fxml"));

                        primaryStage.setScene(new Scene(root));
                        primaryStage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void signOut() throws Exception
    {
        Stage primaryStage;
        Parent root;

        primaryStage = (Stage) btnSignOut.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
