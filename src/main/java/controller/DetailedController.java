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

import static model.Forecast.capitalize;

public class DetailedController {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label city;

    @FXML
    private Label sunrise;

    @FXML
    private Label description;

    @FXML
    private Label summary;

    @FXML
    private Label currentTemp;

    @FXML
    private Label highLowTemp;

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
       if(hourly[0].getRain()!=null)
           tempPrecip=(double)Math.round((hourly[0].getRain().get1h()*10))/10;
       city.setText(location.getName());
       description.setText(capitalize(current.getWeatherDescription()));
       currentTemp.setText(String.format("%1$.0f\u00B0", current.getTemp()));
       highLowTemp.setText(String.format("H: %1$.0f\u00B0  L: %2$.0f\u00B0", dailyMaxTemp, dailyMinTemp));
       summary.setText("Currently " + current.getWeatherDescription() + ". It's " + (int) Math.round(current.getTemp()) + "\u00B0" + "; the high today was forecast to be " + (int) Math.round(dailyMaxTemp) + "\u00B0.");
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
           vBox.getStyleClass().addAll("vbox","week");
           vBox.setAlignment(Pos.CENTER);
           Instant instant = Instant.ofEpochSecond(daily[i].getDt());
           ZoneId zoneId = ZoneId.of(location.getTimezone());
           LocalDateTime ldt = LocalDateTime.ofInstant(instant, zoneId);
           OffsetTime ot = OffsetTime.ofInstant(instant, zoneId);
           Label dayOfWeekLabel = new Label(DayOfWeek.from(ldt).getDisplayName(TextStyle.FULL, Locale.US));
           Label maxTempLabel = new Label((int) Math.round(temps.getMax()) + "\u00B0");
           Label minTempLabel = new Label((int) Math.round(temps.getMin()) + "\u00B0");
           vBox.getChildren().addAll(dayOfWeekLabel,maxTempLabel,minTempLabel);
           dailyStackPanes[i] = new StackPane(imageView,vBox);
       }

       this.dailyHBox.getChildren().addAll(dailyStackPanes);


       StackPane[] hourlyStackPanes = new StackPane[hourly.length];
       for(int i=0; i<hourlyStackPanes.length; i++) {
           ImageView imageView = new ImageView(hourly[i].getWeather()[0].getIconImage());
//           imageView.setOpacity(.5);
           imageView.setFitWidth(150);
           imageView.setPreserveRatio(true);
           VBox vBox = new VBox();
           vBox.getStyleClass().addAll("vbox","week");
           vBox.setAlignment(Pos.CENTER);
           Label timeLabel = new Label(location.getFormattedTime(hourly[i].getDt(), Location.TimeFormat.hour));
           Label tempLabel = new Label((int) Math.round(hourly[i].getTemp()) + "\u00B0");
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
