package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Current;
import model.Daily;
import model.Location;
import model.Weather;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

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

    @FXML
    private HBox hBox;

    @FXML
    private VBox vBoxKey;

    ArrayList<Location> locationList;



    Stage stage;

    Scene scene;


   public void sendLocation(ArrayList<Location> locationList, Location location) {
       this.locationList = locationList;
       Daily[] daily = location.getDaily();
       Current current = location.getCurrent();
       gridPane.getStyleClass().add("gridPane");
       pane.setStyle("-fx-background-color: #AAAAAA; -fx-opacity: .2;");
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
       currentTemp.setText(Double.toString(current.getTemp()));
       StringBuilder weatherSB = new StringBuilder();
       for(Weather weather: current.getWeather()) {
           weatherSB.append(weather.getDescription() + "\n");
       }
       description.setText(weatherSB.toString());
       feelsLike.setText(Double.toString(current.getFeels_like()));
       highTemp.setText(Double.toString(daily[0].getTemps().getMax()));
       lowTemp.setText(Double.toString(daily[0].getTemps().getMin()));
       humidity.setText(Double.toString(current.getHumidity()));
       cloud.setText(Double.toString(current.getClouds()));
       windSpeed.setText(Double.toString(current.getWind_speed()));
       windGust.setText(Double.toString(current.getWind_gust()));
       windDirection.setText(Double.toString(current.getWind_deg()));

       StackPane[] stackPanes = new StackPane[daily.length];


       for(int i=0; i<stackPanes.length; i++) {
           System.out.println(daily[i].getIcon());
           ImageView imageView = new ImageView(daily[i].getIcon());
           imageView.setOpacity(.5);
           imageView.setFitWidth(150);
           imageView.setPreserveRatio(true);
           VBox vBox = new VBox();
           vBox.getStyleClass().addAll("vbox","week");
           vBox.setAlignment(Pos.CENTER);
           Instant instant = Instant.ofEpochSecond(daily[i].getDt());
           ZoneId zoneId = ZoneId.of(location.getTimezone());
           LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
           Label dayOfWeekLabel = new Label(DayOfWeek.from(ldt).getDisplayName(TextStyle.FULL, Locale.US));
           Label maxTempLabel = new Label(Double.toString(daily[i].getTemps().getMax()));
           Label minTempLabel = new Label(Double.toString(daily[i].getTemps().getMin()));
           vBox.getChildren().addAll(dayOfWeekLabel,maxTempLabel,minTempLabel);
           System.out.println(imageView);
           vBoxKey.getStyleClass().addAll("vbox","week");
           stackPanes[i] = new StackPane(imageView,vBox);
       }

       hBox.getChildren().addAll(stackPanes);

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
