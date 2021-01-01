package model;

 // Class used to call weather API and create objects with weather information

import javafx.scene.control.Alert;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static model.CurrentWeatherAPI.callCurrentWeatherAPI;
import static model.OneCallAPI.callOneCallAPI;

public class Location {

    // combined URL string e.g. http://api.openweathermap.org/data/2.5/weather?zip=93744&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2

    public static final String openWeatherURL = "http://api.openweathermap.org/data/2.5";
    public static final String apiKey = "98edb87e72911500a7f165a998c7fcf2";
    public static final String currentWeatherApiPath = "/weather";
    public static final String cityQuery = "?q=";
    public static final String zipQuery = "?zip=";
    public static final String idQuery = "?id=";
    public static final String latQuery = "?lat=";
    public static final String lonQuery = "&lon=";
    public static final String units = "imperial";
    public static final String oneCallApiPath = "/onecall";

    private String name;
    private String country;
    private double lat;
    private double lon;

    private String timezone;
    private long timezone_offset;
    private Current current;
    private Minutely[] minutely;
    private Hourly[] hourly;
    private Daily[] daily;
    private Alerts[] alerts;

    public enum TimeFormat {hour, hourMin}

    // Default Constructor
    public Location() {
    }

    public Location(String name, String country, double lat, double lon) throws IOException {
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        OneCallAPI oneCallAPI = callOneCallAPI(lat, lon);
        this.timezone = oneCallAPI.getTimezone();
        this.timezone_offset = oneCallAPI.getTimezone_offset();
        this.current = oneCallAPI.getCurrent();
        this.minutely = oneCallAPI.getMinutely();
        this.hourly = oneCallAPI.getHourly();
        this.daily = oneCallAPI.getDaily();
        this.alerts = oneCallAPI.getAlerts();
    }

    public Location(CurrentWeatherAPI currentWeatherAPI) throws IOException {
        this(currentWeatherAPI.getName(),
                currentWeatherAPI.getSys().get("country"),
                currentWeatherAPI.getCoord().get("lat"),
                currentWeatherAPI.getCoord().get("lon"));
    }

    // Returns most likely city/village/neighborhood (as determined by Open Weather API)
    public Location(String city) throws IOException {
        this(callCurrentWeatherAPI(city));
    }

    // Can put in a US state instead of country
    public Location(String city, String country) throws IOException {
        this(callCurrentWeatherAPI(city, country));
    }

    public Location(String city, String state, String country) throws IOException {
        this(callCurrentWeatherAPI(city, state, country));
    }

    // Assumes US Zip Code first, and if not found tries City ID
    public Location(int cityIdOrZip) throws IOException {
        this(callCurrentWeatherAPI(cityIdOrZip));
    }

    public Location(double lat, double lon) throws IOException {
        this(callCurrentWeatherAPI(lat, lon));
    }

    public Location(int postal, String country) throws IOException {
        this(callCurrentWeatherAPI(postal, country));
    }

    // Getters and Setters

    public static StringBuilder openWeatherURL() {
        return new StringBuilder(openWeatherURL);
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

    //

    public OffsetTime getTime(long epochSecond) {
        Instant instant = Instant.ofEpochSecond(epochSecond);
        ZoneId zoneId = ZoneId.of(this.getTimezone());
        return OffsetTime.ofInstant(instant,zoneId);
    }

    public String getFormattedTime(long epochSecond, Location.TimeFormat timeFormat) {
        if(timeFormat.equals(Location.TimeFormat.hour)) return getTime(epochSecond).format(DateTimeFormatter.ofPattern("h a"));
        else return getTime(epochSecond).format(DateTimeFormatter.ofPattern("h:mm a"));
    }

    public void alertBox() {
        if (alerts != null) {
            for (Alerts alerts : this.getAlerts()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, alerts.getDescription());
                alert.setTitle(alerts.getEvent());
                alert.setHeaderText(alerts.getEvent());
                alert.showAndWait();
            }
        }
    }
}
