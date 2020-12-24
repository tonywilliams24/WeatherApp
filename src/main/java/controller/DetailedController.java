package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import static model.Forecast.capitalize;

public class DetailedController {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label sunrise;

    @FXML
    private Label summary;

    @FXML
    private Label pop;

    @FXML
    private Label precipitation;

    @FXML
    private Label visibility;

    @FXML
    private Label humidity;

    @FXML
    private Label sunset;

    @FXML
    private Label windSpeed;

    @FXML
    private Label windGust;

    @FXML
    private Label cityKey;

    @FXML
    private Label currentTempKey;

    @FXML
    private Label descriptionKey;

    @FXML
    private Label popKey;

    @FXML
    private Label precipitationKey;

    @FXML
    private Label visibilityKey;

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

    @FXML
    private Slider uviBar;

    @FXML
    private Label uvi;

    @FXML
    private ImageView imageView;

    ArrayList<Location> locationList;

    Stage stage;

    Scene scene;


   public void sendLocation(ArrayList<Location> locationList, Location location) {
       this.locationList = locationList;
       Daily[] daily = location.getDaily();
       Hourly[] hourly = location.getHourly();
       Current current = location.getCurrent();
       Temps dailyTemps = daily[0].getTemps();
       double dailyMaxTemp = dailyTemps.getMax();
       double dailyMinTemp = dailyTemps.getMin();
       double tempPrecip=0;
       String background = String.format("src/main/resources/backgrounds/%1$s.gif",current.getWeather()[0].getIcon());
       if(hourly[0].getRain()!=null)
           tempPrecip=(double)Math.round((hourly[0].getRain().get1h()*10))/10;
       summary.setText(String.format("It's currently %1$.0f\u00B0 with %2$s; the high today was forecast to be %3$.0f\u00B0.",current.getTemp(),current.getWeatherDescription(),dailyMaxTemp));
       sunrise.setText(location.getFormattedTime(current.getSunrise(), Location.TimeFormat.hourMin));
       sunset.setText(location.getFormattedTime(current.getSunset(), Location.TimeFormat.hourMin));
       pop.setText((int)Math.round(hourly[0].getPop()*100) + "%");
       precipitation.setText(tempPrecip + " in");
       visibility.setText((double)Math.round(current.getVisibility()*0.06213712)/100 + " mi");
       humidity.setText(Math.round(current.getHumidity()) + "%");
       windSpeed.setText(current.getWind_dir() + " " + (int) Math.round(current.getWind_speed()) + " mph");
       windGust.setText((int) Math.round(current.getWind_gust()) + " mph");
       location.alertBox();

       StackPane[] dailyStackPanes = new StackPane[daily.length];
       for(int i=0; i<dailyStackPanes.length; i++) {
           Temps temps = daily[i].getTemps();
           ImageView imageView = new ImageView(daily[i].getWeather()[0].getIconImage());
           imageView.setFitWidth(150);
           imageView.setPreserveRatio(true);
           VBox vBox = new VBox();
           Label dayOfWeekLabel = new Label();
           Label maxTempLabel = new Label();
           Label minTempLabel = new Label();

           Instant instant = Instant.ofEpochSecond(daily[i].getDt());
           ZoneId zoneId = ZoneId.of(location.getTimezone());
           LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
           OffsetTime ot = OffsetTime.ofInstant(instant, zoneId);

           vBox.getStyleClass().addAll("vbox","week");
           vBox.setAlignment(Pos.CENTER);
           dayOfWeekLabel.getStyleClass().add("dailyLabel");
           dayOfWeekLabel.setText(DayOfWeek.from(ldt).getDisplayName(TextStyle.FULL, Locale.US));
           maxTempLabel.getStyleClass().add("dailyLabel");
           maxTempLabel.setText(String.format("%1$.0f\u00B0",temps.getMax()));
           minTempLabel.getStyleClass().add("dailyLabel");
           minTempLabel.setText(String.format("%1$.0f\u00B0",temps.getMin()));
           vBox.getChildren().addAll(dayOfWeekLabel,maxTempLabel,minTempLabel);
           dailyStackPanes[i] = new StackPane(imageView,vBox);
       }

       dailyHBox.getChildren().addAll(dailyStackPanes);


       StackPane[] hourlyStackPanes = new StackPane[hourly.length];
       for(int i=0; i<hourlyStackPanes.length; i++) {
           ImageView imageView = new ImageView(hourly[i].getWeather()[0].getIconImage());
           imageView.setFitWidth(150);
           imageView.setPreserveRatio(true);
           VBox vBox = new VBox();
           Label timeLabel = new Label();
           Label tempLabel = new Label();
           Label popLabel = new Label();


           vBox.getStyleClass().addAll("vbox","week");
           vBox.setAlignment(Pos.CENTER);

           timeLabel.setText(location.getFormattedTime(hourly[i].getDt(), Location.TimeFormat.hour));
           timeLabel.getStyleClass().add("hourlyLabel");
           tempLabel.setText(String.format("%1$.0f\u00B0",hourly[i].getTemp()));
           tempLabel.getStyleClass().add("hourlyLabel");
           popLabel.setText(String.format("%1$.0f\u00B0",hourly[i].getPop() * 100));
           popLabel.getStyleClass().add("hourlyLabel");

           vBox.getChildren().addAll(timeLabel,tempLabel,popLabel);

           hourlyStackPanes[i] = new StackPane(imageView,vBox);
       }

       hourlyHBox.getChildren().addAll(hourlyStackPanes);
       uvi.setText(String.format("%1$.1f - %2$s",current.getUvi(),current.getUviRisk()));
       uvi.setStyle("thumb-color: " + current.getUviColor());
       uviBar.setValue(current.getUvi());
       uviBar.setStyle("thumb-color: " + current.getUviColor());
       System.out.println(background);
       imageView.setImage(new Image(Paths.get(background).toUri().toString()));
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
