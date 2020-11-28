package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class Location {

    // One Call API

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
    private String iconUrl;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public final static String iconUrlString_template = "icons/##ICON##@4x.png";

    public static String getIconUrlString_template() {
        return iconUrlString_template;
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
    // API Call originally used to gather all weather information
    // Now currently in use as it converts location input into Lat / Lon for new API
    // To be replaced with a proper geocoding API

    public Location() {
    }
    public Location(String city) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#",city);
        try {
            setMemberVars(inputLocationString);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    // Can put in a state instead of country if searching for a US city.
    public Location(String city, String country) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#",city);
        inputLocationString = inputLocationString.replaceFirst("\\#\\#COUNTRY\\#\\#",country);
        try {
            setMemberVars(inputLocationString);
        }
        catch(Exception e) {
            try {
                inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##STATE##,US&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
                inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#", city);
                inputLocationString = inputLocationString.replaceFirst("\\#\\#STATE\\#\\#", country);
                setMemberVars(inputLocationString);
            }
            catch (Exception e2) {
                System.out.println(e);
            }
        }
    }
    public Location(String city, String state, String country) throws IOException {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##STATE##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#",city);
        inputLocationString = inputLocationString.replaceFirst("\\#\\#STATE\\#\\#",state);
        inputLocationString = inputLocationString.replaceFirst("\\#\\#COUNTRY\\#\\#",country);
         try {
            setMemberVars(inputLocationString);
         }
         catch (Exception e) {
             System.out.println(e);
         }
    }
    public Location(int cityID) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?id=##CITYID##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#CITYID\\#\\#", String.valueOf(cityID));
        try {
            setMemberVars(inputLocationString);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public Location(double lat, double lon) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?lat=##LAT##&lon=##LON##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#LAT\\#\\#", String.valueOf(lat));
        inputLocationString = inputLocationString.replaceFirst("\\#\\#LON\\#\\#", String.valueOf(lon));
        try {
            setMemberVars(inputLocationString);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public Location(int postal, String country) {
        String postalString = String.valueOf(postal);
        int postalLength = postalString.length();
        if(postalLength<5){
            int zeros = 5 - postalLength;
            StringBuilder SB = new StringBuilder(5);
            for(int i=0; i<zeros; i++) SB.append("0");
            SB.append(postalString);
            postalString = SB.toString();
        }
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?zip=##POSTAL##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#POSTAL\\#\\#", postalString);
        inputLocationString = inputLocationString.replaceFirst("\\#\\#COUNTRY\\#\\#", country);
        try {
            setMemberVars(inputLocationString);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    private void setMemberVars(String inputLocationString) throws IOException {
        double inputLat, inputLon;
        String oneCallApiUrl = "http://api.openweathermap.org/data/2.5/onecall?lat=##LAT##&lon=##LON##&units=imperial&appid=98edb87e72911500a7f165a998c7fcf2";
        URL jsonURL = new URL(inputLocationString);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("\n" + jsonURL);
        CurrentWeatherAPI currentWeatherAPI = mapper.readValue(jsonURL, CurrentWeatherAPI.class);
        inputLat = currentWeatherAPI.getCoord().get("lat");
        inputLon = currentWeatherAPI.getCoord().get("lon");
        oneCallApiUrl = oneCallApiUrl.replaceFirst("\\#\\#LAT\\#\\#", String.valueOf(inputLat));
        oneCallApiUrl = oneCallApiUrl.replaceFirst("\\#\\#LON\\#\\#", String.valueOf(inputLon));
        Location location = mapper.readValue(new URL(oneCallApiUrl), Location.class);
        this.name = currentWeatherAPI.getName();
        this.country = currentWeatherAPI.getSys().get("country");
        this.lat = location.lat;
        this.lon = location.lon;
        this.timezone = location.timezone;
        this.timezone_offset = location.timezone_offset;
        this.current = location.current;
        this.minutely = location.minutely;
        this.hourly = location.hourly;
        this.daily = location.daily;
        this.alerts = location.alerts;
        this.iconUrl = this.current.iconUrl();
        this.current.setIcon(new Image(iconUrl));
        for(Hourly hourly: this.hourly) {
            hourly.setIcon(new Image(hourly.iconUrl()));
        }
        for(Daily daily: this.daily) {
            daily.setIcon(new Image(daily.iconUrl()));
        }
        System.out.println(this);
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
