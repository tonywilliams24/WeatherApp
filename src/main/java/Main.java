import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.*;

public class Main extends Application {

    @FXML
    private TextField inputLocationField;

    @FXML
    private Button submit;

    @FXML
    private TextField weatherMainField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField sysCountryField;

    @FXML
    private TextField mainTempField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    void submitHandler(ActionEvent event) throws IOException {
        String[] location = inputLocationField.getText().trim().split("\\s*(\\s|,)\\s*");
        ArrayList<Object> paramList = new ArrayList<>();
        StringBuilder paramSB = new StringBuilder();
        Weather weather = null;
        System.out.println(Arrays.toString(location));
        for(String l : location) {
            try{
                double num = Double.parseDouble(l);
                if(num == Math.floor(num)) {
                    paramList.add((int)num);
                    paramSB.append("i");
                }
                else {
                    paramList.add(num);
                    paramSB.append("d");
                }
            }
            catch (Exception e) {
                paramList.add(l);
                paramSB.append("s");
            }
        }
        System.out.println(paramSB);
        if(paramList.size()==1) {
            if(paramSB.toString().equals("s")) weather = new Weather(String.valueOf(paramList.get(0)));
            else if(paramSB.toString().equals("i")) weather = new Weather((int)paramList.get(0));
            else System.out.println("Location Not Found");
        }
        else if(paramList.size()==2) {
            if(paramSB.toString().equals("ss")) weather = new Weather((String)paramList.get(0),(String)paramList.get(1));
            else if(paramSB.toString().equals("is")) weather = new Weather((int)paramList.get(0),(String)paramList.get(1));
            else if(paramSB.toString().equals("dd")) weather = new Weather((double)paramList.get(0),(double)paramList.get(1));
            else if(paramSB.toString().equals("ii")) weather = new Weather(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
            else if(paramSB.toString().equals("id")) weather = new Weather(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
            else if(paramSB.toString().equals("di")) weather = new Weather(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
            else System.out.println("Location Not Found");
        }
        else {
            weather = new Weather((String)paramList.get(0),(String)paramList.get(1),(String)paramList.get(2));
        }
        if(weather!=null) {
            nameField.setText(weather.getName());
            sysCountryField.setText(weather.getCountryCode());
            mainTempField.setText(String.valueOf(weather.getTemp()));
            weatherMainField.setText(weather.getWeatherMain());
            descriptionArea.setText(weather.getWeatherDescription());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root, 400, 600);
//        scene.getStylesheets().add("src/main/resources/CSS.css");
        primaryStage.setTitle("Weather App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        Weather bremerton = new Weather("Bremerton", "US-WA");
        Weather bremertonZip = new Weather(98311, "US");
        Weather bremertonID = new Weather(5788054);
        Weather bremertonLatLon = new Weather(47.567322,-122.632637);
        Weather miami = new Weather("Miami", "US-FL");
        Weather minneapolis = new Weather("Minneapolis","MN", "US");
        Weather paris = new Weather("paris");
        Weather parisZip = new Weather(75000,"FR"); // Paris using Postal Code
        launch(args);
    }
}
