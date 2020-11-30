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
import model.*;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
    private HBox dailyHBox;

    @FXML
    private VBox dailyKeyVBox;

    @FXML
    private HBox hourlyHBox;

    @FXML
    private VBox hourlyKeyVBox;

    ArrayList<Location> locationList;



    Stage stage;

    Scene scene;


   public void sendLocation(ArrayList<Location> locationList, Location location) {
       this.locationList = locationList;
       Daily[] daily = location.getDaily();
       Hourly[] hourly = location.getHourly();
       Current current = location.getCurrent();
       city.setText(location.getName());
       currentTemp.setText((int) Math.round(current.getTemp()) + "\u00B0F");
       StringBuilder weatherSB = new StringBuilder();
       for(Weather weather: current.getWeather()) {
           weatherSB.append(weather.getDescription() + "\n");
       }
       description.setText(weatherSB.toString());
       feelsLike.setText((int) Math.round(current.getFeels_like()) + "\u00B0F");
       highTemp.setText((int) Math.round(daily[0].getTemps().getMax()) + "\u00B0F");
       lowTemp.setText((int) Math.round(daily[0].getTemps().getMin()) + "\u00B0F");
       humidity.setText(Math.round(current.getHumidity()) + "%");
       cloud.setText(Math.round(current.getClouds()) + "%");
       windSpeed.setText((int) Math.round(current.getWind_speed()) + " mph");
       windGust.setText((int) Math.round(current.getWind_gust()) + " mph");
       windDirection.setText(Double.toString(current.getWind_deg()));

       StackPane[] dailyStackPanes = new StackPane[daily.length];
       for(int i=0; i<dailyStackPanes.length; i++) {
           System.out.println(daily[i].getIcon());
           ImageView imageView = new ImageView(daily[i].getIcon());
//           imageView.setOpacity(.5);
           imageView.setFitWidth(150);
           imageView.setPreserveRatio(true);
           VBox vBox = new VBox();
           vBox.getStyleClass().addAll("vbox","week");
           vBox.setAlignment(Pos.CENTER);
           Instant instant = Instant.ofEpochSecond(daily[i].getDt());
           ZoneId zoneId = ZoneId.of(location.getTimezone());
           LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
           Label dayOfWeekLabel = new Label(DayOfWeek.from(ldt).getDisplayName(TextStyle.FULL, Locale.US));
           Label maxTempLabel = new Label(Integer.toString((int)Math.round(daily[i].getTemps().getMax())) + "\u00B0F");
           Label minTempLabel = new Label(Integer.toString((int)Math.round(daily[i].getTemps().getMin())) + "\u00B0F");
           vBox.getChildren().addAll(dayOfWeekLabel,maxTempLabel,minTempLabel);
           System.out.println(imageView);
           dailyStackPanes[i] = new StackPane(imageView,vBox);
       }

       this.dailyHBox.getChildren().addAll(dailyStackPanes);


       StackPane[] hourlyStackPanes = new StackPane[hourly.length];
       for(int i=0; i<hourlyStackPanes.length; i++) {
           System.out.println(hourly[i].getIcon());
           ImageView imageView = new ImageView(hourly[i].getIcon());
//           imageView.setOpacity(.5);
           imageView.setFitWidth(150);
           imageView.setPreserveRatio(true);
           VBox vBox = new VBox();
           vBox.getStyleClass().addAll("vbox","week");
           vBox.setAlignment(Pos.CENTER);
           Instant instant = Instant.ofEpochSecond(hourly[i].getDt());
           ZoneId zoneId = ZoneId.of(location.getTimezone());
           LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
           LocalTime lt = LocalTime.from(ldt);
           Label timeLabel = new Label(lt.format(DateTimeFormatter.ofPattern("h a")));
           Label tempLabel = new Label((int) Math.round(hourly[i].getTemp()) + "\u00B0F");
           Label popLabel = new Label((int) (hourly[i].getPop() * 100) + " %");
           vBox.getChildren().addAll(timeLabel,tempLabel,popLabel);
           System.out.println(imageView);
           hourlyStackPanes[i] = new StackPane(imageView,vBox);
       }

       this.hourlyHBox.getChildren().addAll(hourlyStackPanes);

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
