import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.util.*;

public class Main extends Application {

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
