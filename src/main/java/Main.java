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

        // For Testing

        Location bremerton = apiCalls("Bremerton", "US-WA");
        Location bremertonZip = apiCalls(98311, "US");
        Location cairo = apiCalls("Cairo", "EG");
        Location bremertonID = apiCalls(5788054);
        Location bremertonLatLon = apiCalls(47.567322,-122.632637);
        Location miami = apiCalls("Miami", "US-FL");
        Location minneapolis = apiCalls("Minneapolis","MN", "US");
        Location paris = apiCalls("paris");
        Location parisZip = apiCalls(75000,"FR"); // Paris using Postal Code
        Location piñonAcres = apiCalls("Piñon Acres");
        Location sedroWoolley = apiCalls("Sedro-Woolley");
        Location capeElizabethZip = apiCalls("04107, US");
        Location holtsvilleZip = apiCalls("00501, US");
        Location tokyo = apiCalls("tokyo");
        Location delhi = apiCalls("delhi");
        Location delhiLatLon = apiCalls(28.61, 77.23);
        Location shanghai = apiCalls("shanghai");
        Location shanghaiLatLon = apiCalls(31.228611, 121.474722);
        Location sãoPaulo = apiCalls("São Paulo");
        Location sãoPauloLatLon = apiCalls(-23.55, -46.633333);
        Location sydney = apiCalls("sydney");
        Location sydneyLatLon = apiCalls(-33.865, 151.209444);





        launch(args);
    }
}
