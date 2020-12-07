package model;

 // Class used to call weather API and create objects with weather information

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Location {

    // combined URL string e.g. http://api.openweathermap.org/data/2.5/weather?zip=93744&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2

    private static final String apiKey = "98edb87e72911500a7f165a998c7fcf2";
    private static final String openWeatherURL = "http://api.openweathermap.org/data/2.5";
    private static final String currentWeatherApiPath = "/weather";
    private static final String cityQuery = "?q=";
    private static final String zipQuery = "?zip=";
    private static final String idQuery = "?id=";
    private static final String latQuery = "?lat=";
    private static final String lonQuery = "&lon=";
    private static final String units = "imperial";
    private static final String oneCallApiPath = "/onecall";

    // One Call API Fields

    private double lat;
    private double lon;
    private String timezone;
    private long timezone_offset;
    private Current current;
    private Minutely[] minutely;
    private Hourly[] hourly;
    private Daily[] daily;
    private Alerts[] alerts;
    private String name;
    private String country;
    private String message;
    private int cod;
    public enum TimeFormat {hour, hourMin}

    // Default Constructor
    public Location() {
    }

    // Getters and Setters
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public long getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(long timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Minutely[] getMinutely() {
        return minutely;
    }

    public void setMinutely(Minutely[] minutely) {
        this.minutely = minutely;
    }

    public Hourly[] getHourly() {
        return hourly;
    }

    public void setHourly(Hourly[] hourly) {
        this.hourly = hourly;
    }

    public Daily[] getDaily() {
        return daily;
    }

    public void setDaily(Daily[] daily) {
        this.daily = daily;
    }

    public Alerts[] getAlerts() {
        return alerts;
    }

    public void setAlerts(Alerts[] alerts) {
        this.alerts = alerts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    // Static functions that will make the appropriate API Call based on input data and returns a location object
    // Two APIs are used. The first "Current Weather API" takes a location as input (city, postal code, etc.) and returns Lat / Lon location.
        // To be replaced by a proper geocoding API
    // Second API is the "One Call API", and it returns a location object with weather information set
    // Need to replace String representations of URLs to actual URI/URL objects, with path, query, and fragment parameters

    // Returns most likely city/village/neighborhood (as determined by Open Weather API)
    public static Location weatherLocation(String city) {
        String inputLocationString = (openWeatherURL + currentWeatherApiPath + cityQuery + city + "&units=" + units + "&APPID=" + apiKey)
            .replaceAll("\\s","\\%20"); // Replaces all spaces in URL with %20
        return callAPIs(inputLocationString);
    }

    // Can put in a US state instead of country
    public static Location weatherLocation(String city, String country) {
        // For international reasons, function assumes City, Country first and then City, US-State if not found
        String inputLocationString = (openWeatherURL + currentWeatherApiPath + cityQuery + city + "," + country + "&units=" + units + "&APPID=" + apiKey)
                .replaceAll("\\s","\\%20");
        Location temp = callAPIs(inputLocationString);
        if (temp!=null) return temp;
        else {
            inputLocationString = (openWeatherURL + currentWeatherApiPath + cityQuery + city + "," + country + ",US" + "&units=" + units + "&APPID=" + apiKey)
                    .replaceAll("\\s", "\\%20");
            return callAPIs(inputLocationString);
        }
    }

    public static Location weatherLocation(String city, String state, String country) {
        String inputLocationString = (openWeatherURL + currentWeatherApiPath + cityQuery + city + "," + state + "," + country + "&units=" + units + "&APPID=" + apiKey)
            .replaceAll("\\s","\\%20");
        return callAPIs(inputLocationString);
    }

    // Assumes US Zip Code first, and if not found tries City ID
    public static Location weatherLocation(int cityIdOrZip) {
        String inputLocationString = (openWeatherURL + currentWeatherApiPath + zipQuery + cityIdOrZip + "&units=" + units + "&APPID=" + apiKey)
            .replaceAll("\\s", "\\%20");
        Location temp = callAPIs(inputLocationString);
        if (temp!=null) return temp;
        else {
            inputLocationString = (openWeatherURL + currentWeatherApiPath + idQuery + cityIdOrZip + "&units=" + units + "&APPID=" + apiKey)
                    .replaceAll("\\s", "\\%20");
            return callAPIs(inputLocationString);
        }
    }
    public static Location weatherLocation(double lat, double lon) {
        String inputLocationString = (openWeatherURL + currentWeatherApiPath + latQuery + lat + lonQuery + lon + "&units=" + units + "&APPID=" + apiKey)
            .replaceAll("\\s","\\%20");
        return callAPIs(inputLocationString);
    }
    public static Location weatherLocation(int postal, String country) {
        String inputLocationString = (openWeatherURL + currentWeatherApiPath + zipQuery + postal + "," + country + "&units=" + units + "&APPID=" + apiKey)
            .replaceAll("\\s","\\%20");
        return callAPIs(inputLocationString);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    // Legacy API to be replaced by a geocoding API. Returns the full currentWeatherAPI object but is only used for Lat / Lon
    private static CurrentWeatherAPI currentWeatherAPI(String inputLocationString) throws IOException {
        URL jsonURL = new URL(inputLocationString);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonURL, CurrentWeatherAPI.class);
    }

    // Inputs currentWeatherAPI object and uses the Lat / Lon to get all weather information
    private static Location oneCallAPI(CurrentWeatherAPI currentWeatherAPI) throws IOException {
        double inputLat, inputLon;
        ObjectMapper mapper = new ObjectMapper();
        inputLat = currentWeatherAPI.getCoord().get("lat");
        inputLon = currentWeatherAPI.getCoord().get("lon");
        String oneCallApiUrl = (openWeatherURL + oneCallApiPath + latQuery + inputLat + lonQuery + inputLon + "&units=" + units + "&appid=" + apiKey);
        System.out.println(oneCallApiUrl);
        Location location = mapper.readValue(new URL(oneCallApiUrl), Location.class);
        location.setName(currentWeatherAPI.getName());
        location.setCountry(currentWeatherAPI.getSys().get("country"));
        location.setCod(currentWeatherAPI.getCod());
        location.setMessage(currentWeatherAPI.getSys().get("message"));
        return location;
    }

    public static Location callAPIs(String inputLocationString) {
        try {
            return oneCallAPI(currentWeatherAPI(inputLocationString)); // Calls both APIs and returns Location object
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public OffsetTime getTime(long epochSecond) {
        Instant instant = Instant.ofEpochSecond(epochSecond);
        ZoneId zoneId = ZoneId.of(this.getTimezone());
        return OffsetTime.ofInstant(instant,zoneId);
    }

    public String getFormattedTime(long epochSecond, TimeFormat timeFormat) {
        if(timeFormat.equals(TimeFormat.hour)) return getTime(epochSecond).format(DateTimeFormatter.ofPattern("h a"));
        else return getTime(epochSecond).format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    public void alertBox() {
        if (this != null) {
            for (Alerts alerts : this.getAlerts()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, alerts.getDescription());
                alert.setTitle(alerts.getEvent());
                alert.setHeaderText(alerts.getEvent());
                alert.showAndWait();
            }
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", timezone_offset='" + timezone_offset + '\'' +
                ", current=" + current +
                ", minutely=" + Arrays.toString(minutely) +
                ", hourly=" + Arrays.toString(hourly) +
                ", daily=" + Arrays.toString(daily) +
                ", alerts=" + Arrays.toString(alerts) +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
