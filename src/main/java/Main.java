import model.Location;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static model.Location.apiCalls;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add("CSS.css");
        primaryStage.setTitle("Model.Weather App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        Location bremerton = apiCalls("Bremerton", "US-WA");
        Location bremertonZip = apiCalls(98311, "US");
        Location bremertonID = apiCalls(5788054);
        Location bremertonLatLon = apiCalls(47.567322,-122.632637);
        Location miami = apiCalls("Miami", "US-FL");
        Location minneapolis = apiCalls("Minneapolis","MN", "US");
        Location paris = apiCalls("paris");
        Location parisZip = apiCalls(75000,"FR"); // Paris using Postal Code
        launch(args);
    }
}
