package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Movie {
    private SimpleStringProperty movieTitle;
    private SimpleDoubleProperty ratings;

    public Movie(String movieTitle, Double ratings) {
        this.movieTitle = new SimpleStringProperty(movieTitle) ;
        this.ratings = new SimpleDoubleProperty(ratings);
    }

    public String getMovieTitle() {
        return movieTitle.get();
    }

    public SimpleStringProperty movieTitleProperty() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle.set(movieTitle);
    }

    public double getRatings() {
        return ratings.get();
    }

    public SimpleDoubleProperty ratingsProperty() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings.set(ratings);
    }
}
