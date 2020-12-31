package model;

// Legacy weather API. Used Java "Map" objects as a shortcut to making new classes as this API is temporary

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static model.Location.*;

public class CurrentWeatherAPI {
    private Map<String, Double> coord;
    private List<Map<String, String>> weather;
    private String base;
    private Map<String, Double> main;
    private String visibility;
    private Map<String, Double> wind;
    private Map<String, Double> clouds;
    private Map<String, Double> rain;
    private Map<String, Double> snow;
    private long dt;
    private Map<String, String> sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;

    @Override
    public String toString() {
        return "CurrentWeatherAPI{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility='" + visibility + '\'' +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", rain=" + rain +
                ", snow=" + snow +
                ", dt=" + dt +
                ", sys=" + sys +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                ", icon=" + icon +
                ", iconUrlString='" + iconUrlString + '\'' +
                '}';
    }

    private Image icon;
    private String iconUrlString = "icons/##ICON##@4x.png";

    public CurrentWeatherAPI() {
    }

    // Returns most likely city/village/neighborhood (as determined by Open Weather API)
    public static CurrentWeatherAPI callCurrentWeatherAPI(String city) throws IOException {
        String urlString = openWeatherURL().append(currentWeatherApiPath)
                .append(cityQuery)
                .append(city)
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s","\\%20"); // Replaces all spaces in URL with %20
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(urlString), CurrentWeatherAPI.class);
    }

    // Can put in a US state instead of country
    public static CurrentWeatherAPI callCurrentWeatherAPI(String city, String country) throws IOException {
        String[] strings = new String[2];
        // For international reasons, function assumes City, Country first and then City, US-State if not found
        strings[0] = openWeatherURL().append(currentWeatherApiPath)
                .append(cityQuery)
                .append(city)
                .append(",")
                .append(country)
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s","\\%20");
        strings[1] = openWeatherURL().append(currentWeatherApiPath)
                .append(cityQuery)
                .append(city)
                .append(",")
                .append(country) // State
                .append(",US")
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s","\\%20");

        ObjectMapper mapper = new ObjectMapper();
        CurrentWeatherAPI currentWeatherAPI = mapper.readValue(new URL(strings[0]), CurrentWeatherAPI.class);
        if(currentWeatherAPI!=null) return currentWeatherAPI;
        return mapper.readValue(new URL(strings[1]), CurrentWeatherAPI.class);
    }

    public static CurrentWeatherAPI callCurrentWeatherAPI(String city, String state, String country) throws IOException {
        String urlString = openWeatherURL().append(currentWeatherApiPath)
                .append(cityQuery)
                .append(city)
                .append(",")
                .append(state)
                .append(",")
                .append(country)
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s","\\%20");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(urlString), CurrentWeatherAPI.class);
    }

    // Assumes US Zip Code first, and if not found tries City ID
    public static CurrentWeatherAPI callCurrentWeatherAPI(int cityIdOrZip) throws IOException {
        String[] strings = new String[2];
        strings[0] = openWeatherURL().append(currentWeatherApiPath)
                .append(zipQuery)
                .append(cityIdOrZip)
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s", "\\%20");
        strings[1] = openWeatherURL().append(currentWeatherApiPath)
                .append(idQuery)
                .append(cityIdOrZip)
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s", "\\%20");
        ObjectMapper mapper = new ObjectMapper();
        CurrentWeatherAPI currentWeatherAPI = mapper.readValue(new URL(strings[0]), CurrentWeatherAPI.class);
        if(currentWeatherAPI!=null) return currentWeatherAPI;
        return mapper.readValue(new URL(strings[1]), CurrentWeatherAPI.class);
    }

    public static CurrentWeatherAPI callCurrentWeatherAPI(double lat, double lon) throws IOException {
        String urlString = openWeatherURL().append(currentWeatherApiPath)
                .append(latQuery)
                .append(lat)
                .append(lonQuery)
                .append(lon)
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s","\\%20");

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(urlString), CurrentWeatherAPI.class);
    }
    public static CurrentWeatherAPI callCurrentWeatherAPI(int postal, String country) throws IOException {
        String urlString = openWeatherURL().append(currentWeatherApiPath)
                .append(zipQuery)
                .append(postal)
                .append(",")
                .append(country)
                .append("&units=")
                .append(units)
                .append("&APPID=")
                .append(apiKey)
                .toString()
                .replaceAll("\\s","\\%20");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL(urlString), CurrentWeatherAPI.class);
    }


    public Map<String, Double> getCoord() {
        return coord;
    }

    public void setCoord(Map<String, Double> coord) {
        this.coord = coord;
    }

    public List<Map<String, String>> getWeather() {
        return weather;
    }

    public void setWeather(List<Map<String, String>> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getMain() {
        return main;
    }

    public void setMain(Map<String, Double> main) {
        this.main = main;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Map<String, Double> getWind() {
        return wind;
    }

    public void setWind(Map<String, Double> wind) {
        this.wind = wind;
    }

    public Map<String, Double> getClouds() {
        return clouds;
    }

    public void setClouds(Map<String, Double> clouds) {
        this.clouds = clouds;
    }

    public Map<String, Double> getRain() {
        return rain;
    }

    public void setRain(Map<String, Double> rain) {
        this.rain = rain;
    }

    public Map<String, Double> getSnow() {
        return snow;
    }

    public void setSnow(Map<String, Double> snow) {
        this.snow = snow;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Map<String, String> getSys() {
        return sys;
    }

    public void setSys(Map<String, String> sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getIconUrlString() {
        return iconUrlString;
    }

    public void setIconUrlString(String iconUrlString) {
        this.iconUrlString = iconUrlString;
    }
}
