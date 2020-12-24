package controller;

// Controller for the starting screen of the app
// XML located at src/main/resources/main.fxml

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static model.Forecast.capitalize;
import static model.Location.weatherLocation;

public class MainScreenController {

    private static boolean started;


    // Members who's properties can be found in the main.XML file

    @FXML
    private TextField inputLocationField;

    @FXML
    private Label weatherMainLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label sysCountryLabel;

    @FXML
    private Label mainTempLabel;

    @FXML
    private Label mainLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label temperatureLabel;

    @FXML
    private Label conditionLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button submit;

    @FXML
    private Pagination pagination;

    @FXML
    private VBox vbox;

    @FXML
    private ImageView imageView;

    @FXML
    private Label city;

    @FXML
    private Label highLowTemp;

    @FXML
    private Label currentTemp;

    @FXML
    private Label description;

    Stage stage;

    // List to hold location objects once they are created
    ArrayList<Location> locationList = new ArrayList<>();

    public MainScreenController() {
        // For testing only. Initialize method should be entirely removed if not testing

    }

    @FXML
    public void initialize() throws IOException {
        startPagination(locationList);
        started = true;
    }


    // Handler for the submit button
    // Parses user input to determine if any ints or doubles are included, and passes the parameters to the appropriate function (overloaded)
    // Creates a Location object (which holds all weather information) and adds it to a list of Location Objects
    // Starts pagination using list of Location objects

    @FXML
    void submitHandler(ActionEvent event) throws IOException {
        String inputString = inputLocationField.getText().trim(); // User input
        String[] inputLocation = inputString.split("(\\s*(,\\s)\\s*)|,"); // input is split at ", " or "," with any number of whitespace before or after
        StringBuilder paramSB = new StringBuilder(); // StringBuilder to append "i" for potential int, "d" for potential double, or "s" if only string
        Location location = null;
        String paramString;
        for (String l : inputLocation) { // Checks each string in array to see if it is a potential double or integer
            try {
                double num = Double.parseDouble(l);
                if (num == Math.floor(num)) {
                    paramSB.append("i");
                } else {
                    paramSB.append("d");
                }
            } catch (Exception e) {
                paramSB.append("s");
            }
        }
        paramString = paramSB.toString();
        if (inputLocation.length == 1) {
            if (paramString.equals("s")) { // assumes city name was input
                location = weatherLocation(inputString);
                locationList.add(location);
            } else if (paramString.equals("i")) { // assumes zip code or city ID
                location = weatherLocation(Integer.parseInt(inputLocation[0]));
                locationList.add(location);
            } else System.out.println("location Not Found");
        } else if (inputLocation.length == 2) {
            if (paramString.equals("ss")) { // assumes city, country or city, us-state
                location = weatherLocation(inputLocation[0], inputLocation[1]);
                locationList.add(location);
            } else if (paramString.equals("is")) { // assumes zip, country
                location = weatherLocation(Integer.parseInt(inputLocation[0]), inputLocation[1]);
                locationList.add(location);
            } else if (paramString.equals("dd")) { // assumes Lat, Lon
                location = weatherLocation(Double.parseDouble(inputLocation[0]), Double.parseDouble(inputLocation[1]));
                locationList.add(location);
            } else if (paramString.equals("ii")) { // assumes Lat, Lon
                location = weatherLocation(Double.parseDouble(inputLocation[0]), Double.parseDouble(inputLocation[1]));
                locationList.add(location);
            } else if (paramString.equals("id")) { // assumes Lat, Lon
                location = weatherLocation(Double.parseDouble(inputLocation[0]), Double.parseDouble(inputLocation[1]));
                locationList.add(location);
            } else if (paramString.equals("di")) { // assumes Lat, Lon
                location = weatherLocation(Double.parseDouble(inputLocation[0]), Double.parseDouble(inputLocation[1]));
                locationList.add(location);
            } else System.out.println("location Not Found");
        } else { // assumes all remaining cases are a string
            {
                try {
                    location = weatherLocation(inputString);
                    locationList.add(location);
                } catch (Exception e) {
                    System.out.println("Input Not Recognized");
                }
            }
        }
        if (location != null) {
            System.out.println(location);
            startPagination(locationList);
        } else {
            locationList.remove(locationList.size() - 1);
        }
    }

    public VBox createPage(int pageIndex) {
        VBox pageBox = new VBox();
        Label pageLabel = new Label("Test " + pageIndex + 1);
        pageBox.getChildren().add(pageLabel);
        return pageBox;
    }

    @FXML
    public void detailedHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/detailed.fxml"));
        loader.load();
        DetailedController dController = loader.getController();
        dController.sendLocation(locationList, locationList.get(pagination.getCurrentPageIndex()));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        scene.getStylesheets().add("/CSS.css");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Starts Pagination :D
    public void startPagination(ArrayList<Location> locationList) {
        this.locationList = locationList;

        if (!started) { // For testing purposes only
            Location bremertonWA = weatherLocation("Bremerton", "WA");
            Location bremertonZip = weatherLocation(98311, "US");
            Location bremertonID = weatherLocation(5788054);
            Location bremertonLatLon = weatherLocation(47.567322, -122.632637);
            Location paris = weatherLocation("paris");
            Location parisZip = weatherLocation(75000, "FR"); // Paris using Postal Code
            Location piñonAcres = weatherLocation("Piñon Acres");
            Location shanghai = weatherLocation("shanghai");
            Location shanghaiLatLon = weatherLocation(31.228611, 121.474722);
            Location sãoPauloLatLon = weatherLocation(-23.55, -46.633333);
            Location sydneyLatLon = weatherLocation(-33.865, 151.209444);
            Location cairo = weatherLocation("Cairo", "EG");
            Location miami = weatherLocation("Miami", "US-FL");
            Location minneapolis = weatherLocation("Minneapolis", "MN", "US");
            Location sedroWoolley = weatherLocation("Sedro-Woolley");
            Location capeElizabethZip = weatherLocation("04107, US");
            Location holtsvilleZip = weatherLocation("00501, US");
            Location tokyo = weatherLocation("tokyo");
            Location delhi = weatherLocation("delhi");
            Location delhiLatLon = weatherLocation(28.61, 77.23);
            Location sãoPaulo = weatherLocation("São Paulo");
            Location naples = weatherLocation("Naples", "FL");
            Location sydney = weatherLocation("sydney");
            Location nome = weatherLocation("Nome", "US-AK");
            Location bremerton = weatherLocation("Bremerton", "US-WA");

            locationList.add(bremertonWA);
            locationList.add(bremertonZip);
            locationList.add(bremertonID);
            locationList.add(bremertonLatLon);
            locationList.add(paris);
            locationList.add(parisZip);
            locationList.add(piñonAcres);
            locationList.add(shanghai);
            locationList.add(shanghaiLatLon);
            locationList.add(sãoPauloLatLon);
            locationList.add(sydneyLatLon);
            locationList.add(minneapolis);
            locationList.add(cairo);
            locationList.add(sedroWoolley);
            locationList.add(capeElizabethZip);
            locationList.add(holtsvilleZip);
            locationList.add(tokyo);
            locationList.add(delhi);
            locationList.add(delhiLatLon);
            locationList.add(sãoPaulo);
            locationList.add(miami);
            locationList.add(naples);
            locationList.add(nome);
            locationList.add(sydney);
            locationList.add(bremerton);

        }

            pagination.setPageCount(locationList.size());
            pagination.setMaxPageIndicatorCount(10);
            pagination.setPageFactory((pageIndex) -> {
                Location location = locationList.get(pageIndex);
                Current current = location.getCurrent();
                Daily[] daily = location.getDaily();
                Temps dailyTemps = daily[0].getTemps();
                double dailyMaxTemp = dailyTemps.getMax();
                double dailyMinTemp = dailyTemps.getMin();
                city.setText(location.getName() + ", " + location.getCountry());
                description.setText(capitalize(current.getWeatherDescription()));
                currentTemp.setText(String.format("%1$.0f\u00B0", current.getTemp()));
                highLowTemp.setText(String.format("H: %1$.0f\u00B0  L: %2$.0f\u00B0", dailyMaxTemp, dailyMinTemp));
                imageView.setVisible(true);
                imageView.setImage(location.getCurrent().getWeather()[0].getIconImage());
                return new StackPane(imageView, vbox);
            });
            pagination.setCurrentPageIndex(locationList.size() - 1);
            pagination.setVisible(true);
        }
    }
