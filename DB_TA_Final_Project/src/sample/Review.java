package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Review {

    private SimpleStringProperty userName;
    private SimpleStringProperty description;
    private SimpleDoubleProperty ratings;

    public Review(String userName, String description, Double ratings) {
        this.userName = new SimpleStringProperty(userName);
        this.description = new SimpleStringProperty(description);
        this.ratings = new SimpleDoubleProperty(ratings);
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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
