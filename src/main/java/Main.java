import model.Location;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        Location bremerton = new Location("Bremerton", "US-WA");
        Location bremertonZip = new Location(98311, "US");
        Location bremertonID = new Location(5788054);
        Location bremertonLatLon = new Location(47.567322,-122.632637);
        Location miami = new Location("Miami", "US-FL");
        Location minneapolis = new Location("Minneapolis","MN", "US");
        Location paris = new Location("paris");
        Location parisZip = new Location(75000,"FR"); // Paris using Postal Code
        launch(args);
    }
}
