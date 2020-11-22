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
    private GridPane gridPane;

    @FXML
    private Label city;

    @FXML
    private Label currentTemp;

    @FXML
    private Label description;

    @FXML
    private Label feelsLike;

    @FXML
    private Label highTemp;

    @FXML
    private Label lowTemp;

    @FXML
    private Label humidity;

    @FXML
    private Label cloud;

    @FXML
    private Label windSpeed;

    @FXML
    private Label windGust;

    @FXML
    private Label windDirection;

    @FXML
    private Label cityKey;

    @FXML
    private Label currentTempKey;

    @FXML
    private Label descriptionKey;

    @FXML
    private Label feelsLikeKey;

    @FXML
    private Label highTempKey;

    @FXML
    private Label lowTempKey;

    @FXML
    private Label humidityKey;

    @FXML
    private Label cloudKey;

    @FXML
    private Label windSpeedKey;

    @FXML
    private Label windGustKey;

    @FXML
    private Label windDirectionKey;

    Scene scene;


   public void sendLocation(Location location) {
       gridPane.getStyleClass().add("gridPane");
       city.getStyleClass().add("value");
       currentTemp.getStyleClass().add("value");
       description.getStyleClass().add("value");
       feelsLike.getStyleClass().add("value");
       highTemp.getStyleClass().add("value");
       lowTemp.getStyleClass().add("value");
       humidity.getStyleClass().add("value");
       cloud.getStyleClass().add("value");
       windSpeed.getStyleClass().add("value");
       windGust.getStyleClass().add("value");
       windDirection.getStyleClass().add("value");
       city.setText(location.getName());
       currentTemp.setText(Double.toString(location.getTemp()));
       description.setText(location.getWeatherDescription());
       feelsLike.setText(Double.toString(location.getMain().getFeels_like()));
       highTemp.setText(Double.toString(location.getMain().getTemp_max()));
       lowTemp.setText(Double.toString(location.getMain().getTemp_min()));
       humidity.setText(Double.toString(location.getMain().getHumidity()));
       cloud.setText(Double.toString(location.getClouds().getAll()));
       windSpeed.setText(Double.toString(location.getWind().getSpeed()));
       windGust.setText(Double.toString(location.getWind().getGust()));
       windDirection.setText(Double.toString(location.getWind().getDeg()));
    }
}
