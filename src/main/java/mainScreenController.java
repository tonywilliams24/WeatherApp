import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class mainScreenController {

    @FXML
    private TextField inputLocationField;

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

    ArrayList<Weather> locations = new ArrayList<>();

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
            if(paramSB.toString().equals("s")) {
                weather = new Weather(String.valueOf(paramList.get(0)));
                locations.add(weather);
            }
            else if(paramSB.toString().equals("i")) {
                weather = new Weather((int)paramList.get(0));
                locations.add(weather);
            }
            else System.out.println("Location Not Found");
        }
        else if(paramList.size()==2) {
            if(paramSB.toString().equals("ss")) {
                weather = new Weather((String)paramList.get(0),(String)paramList.get(1));
                locations.add(weather);
            }
            else if(paramSB.toString().equals("is")) {
                weather = new Weather((int)paramList.get(0),(String)paramList.get(1));
                locations.add(weather);
            }
            else if(paramSB.toString().equals("dd")) {
                weather = new Weather((double)paramList.get(0),(double)paramList.get(1));
                locations.add(weather);
            }
            else if(paramSB.toString().equals("ii")) {
                weather = new Weather(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
                locations.add(weather);
            }
            else if(paramSB.toString().equals("id")) {
                weather = new Weather(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
                locations.add(weather);
            }
            else if(paramSB.toString().equals("di")) {
                weather = new Weather(((Integer)paramList.get(0)).doubleValue(),((Integer)paramList.get(1)).doubleValue());
                locations.add(weather);
            }
            else System.out.println("Location Not Found");
        }
        else {
            {
                weather = new Weather((String)paramList.get(0),(String)paramList.get(1),(String)paramList.get(2));
                locations.add(weather);
            }
        }
        if(weather!=null) {

            /*
            nameField.setText(weather.getName());
            sysCountryField.setText(weather.getCountryCode());
            mainTempField.setText(String.valueOf(weather.getTemp()));
            weatherMainField.setText(weather.getWeatherMain());
            descriptionArea.setText(weather.getWeatherDescription());
            */

            System.out.println(Arrays.toString(locations.toArray()));

            pagination.setPageCount(locations.size());
            pagination.setMaxPageIndicatorCount(10);
            pagination.setPageFactory((pageIndex) -> {
                Label label = new Label(locations.get(pageIndex).toString());
                nameField.setText(locations.get(pageIndex).getName());
                sysCountryField.setText(locations.get(pageIndex).getCountryCode());
                mainTempField.setText(String.valueOf(locations.get(pageIndex).getTemp()));
                weatherMainField.setText(locations.get(pageIndex).getWeatherMain());
                descriptionArea.setText(locations.get(pageIndex).getWeatherDescription());
                return new VBox(cityLabel, nameField, countryLabel, sysCountryField, temperatureLabel, mainTempField, conditionLabel, weatherMainField, descriptionLabel, descriptionArea);
            });
            pagination.setCurrentPageIndex(locations.size()-1);

        }
    }

    public VBox createPage(int pageIndex) {
        VBox pageBox = new VBox();
        Label pageLabel = new Label("Test " + pageIndex+1);
        pageBox.getChildren().add(pageLabel);
        return pageBox;
    }

    @FXML
    public void initialize(){
        pagination.setPageCount(1);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageFactory((pageIndex) -> {
            Label label = new Label("");
            return new VBox(label);
        });
    }

}
