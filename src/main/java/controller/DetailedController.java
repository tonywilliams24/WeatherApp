package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Location;
import model.Weather;

import java.io.IOException;
import java.util.ArrayList;

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

    @FXML
    private Button backButton;

    @FXML
    private Pane pane;

    ArrayList<Location> locationList;



    Stage stage;

    Scene scene;


   public void sendLocation(ArrayList<Location> locationList, Location location) {
       this.locationList = locationList;
       gridPane.getStyleClass().add("gridPane");
       pane.setStyle("-fx-background-image: url('"+location.getIconUrlString()+"'); -fx-background-color: #AAAAAA; -fx-opacity: .2;");
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
       currentTemp.setText(Double.toString(location.getCurrent().getTemp()));
       StringBuilder weatherSB = new StringBuilder();
       for(Weather weather: location.getCurrent().getWeather()) {
           weatherSB.append(weather.getDescription() + "\n");
       }
       description.setText(weatherSB.toString());
       feelsLike.setText(Double.toString(location.getCurrent().getFeels_like()));
       highTemp.setText(Double.toString(location.getDaily()[0].getTemps().getMax()));
       lowTemp.setText(Double.toString(location.getDaily()[0].getTemps().getMin()));
       humidity.setText(Double.toString(location.getCurrent().getHumidity()));
       cloud.setText(Double.toString(location.getCurrent().getClouds()));
       windSpeed.setText(Double.toString(location.getCurrent().getWind_speed()));
       windGust.setText(Double.toString(location.getCurrent().getWind_gust()));
       windDirection.setText(Double.toString(location.getCurrent().getWind_deg()));
    }

    @FXML
    public void backHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main.fxml"));
        loader.load();
        MainScreenController mSController = loader.getController();
        mSController.startPagination(locationList);
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        scene.getStylesheets().add("/CSS.css");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
