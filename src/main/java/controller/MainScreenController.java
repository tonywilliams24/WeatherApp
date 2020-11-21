package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class MainScreenController {

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
    private Label descriptionDetailLabel;

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

    Stage stage;

    ArrayList<Location> locations = new ArrayList<>();

    @FXML
    void submitHandler(ActionEvent event) throws IOException {
        String[] inputLocation = inputLocationField.getText().trim().split("\\s*(,\\s)\\s*");
        ArrayList<Object> paramList = new ArrayList<>();
        StringBuilder paramSB = new StringBuilder();
        Location location = null;
        for(String l : inputLocation) {
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
        if(paramList.size()==1) {
            if(paramSB.toString().equals("s")) {
                location = new Location(String.valueOf(paramList.get(0)));
                locations.add(location);
            }
            else if(paramSB.toString().equals("i")) {
                location = new Location((int)paramList.get(0));
                locations.add(location);
            }
            else System.out.println("location Not Found");
        }
        else if(paramList.size()==2) {
            if(paramSB.toString().equals("ss")) {
                location = new Location((String)paramList.get(0),(String)paramList.get(1));
                locations.add(location);
            }
            else if(paramSB.toString().equals("is")) {
                location = new Location((int)paramList.get(0),(String)paramList.get(1));
                locations.add(location);
            }
            else if(paramSB.toString().equals("dd")) {
                location = new Location((double)paramList.get(0),(double)paramList.get(1));
                locations.add(location);
            }
            else if(paramSB.toString().equals("ii")) {
                location = new Location(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
                locations.add(location);
            }
            else if(paramSB.toString().equals("id")) {
                location = new Location(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
                locations.add(location);
            }
            else if(paramSB.toString().equals("di")) {
                location = new Location(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
                locations.add(location);
            }
            else System.out.println("location Not Found");
        }
        else {
            {
                try {
                    location = new Location((String) paramList.get(0), (String) paramList.get(1), (String) paramList.get(2));
                    locations.add(location);
                }
                catch(Exception e) {

                }
            }
        }
        if(location.getWeather()!=null) {
            pagination.getStyleClass().add("pagination");
            vbox.getStyleClass().add("vbox");
            pagination.setPageCount(locations.size());
            pagination.setMaxPageIndicatorCount(10);
            pagination.setPageFactory((pageIndex) -> {
                nameLabel.setText(locations.get(pageIndex).getName() + ", " + locations.get(pageIndex).getCountryCode());
                mainTempLabel.setText(locations.get(pageIndex).getTemp() + " ºF");
                descriptionDetailLabel.setText(locations.get(pageIndex).getWeatherDescription());
                imageView.setVisible(true);
                imageView.setImage(locations.get(pageIndex).getIcon());
                return new StackPane(imageView,vbox);
            });
            pagination.setCurrentPageIndex(locations.size()-1);
            pagination.setVisible(true);

        }
        else
        {
            locations.remove(locations.size()-1);
        }
    }

    public VBox createPage(int pageIndex) {
        VBox pageBox = new VBox();
        Label pageLabel = new Label("Test " + pageIndex+1);
        pageBox.getChildren().add(pageLabel);
        return pageBox;
    }

    @FXML
    public void detailedHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        System.out.println(getClass().getResource("/detailed.fxml"));
        loader.setLocation(getClass().getResource("/detailed.fxml"));
        loader.load();
        DetailedController dController = loader.getController();
        dController.sendLocation(locations.get(pagination.getCurrentPageIndex()));
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        scene.getStylesheets().add("/CSS.css");
        stage.setScene(new Scene(scene));
        stage.show();
    }
}