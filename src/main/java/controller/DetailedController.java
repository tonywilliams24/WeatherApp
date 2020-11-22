package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Location;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailedController {

    @FXML
    private Label city;

    @FXML
    private Label temp;

    @FXML
    private Label description;

    @FXML
    private Label wind;

    @FXML
    private ColumnConstraints key;

    @FXML
    private ColumnConstraints value;

    @FXML
    private GridPane gridPane;

    Scene scene;


   public void sendLocation(Location location) {
       gridPane.getStyleClass().add("gridPane");
       city.getStyleClass().add("value");
       temp.getStyleClass().add("value");
       description.getStyleClass().add("value");
       wind.getStyleClass().add("value");
       city.setText(location.getName());
       temp.setText(Double.toString(location.getTemp()));
       description.setText(location.getWeatherDescription());
       wind.setText(Double.toString(location.getWind().getSpeed()));
    }
}
