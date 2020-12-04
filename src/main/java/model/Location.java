package model;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public static Location apiCalls(String city) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#",city);
        inputLocationString = inputLocationString.replaceAll("\\s","\\%20");
        try {
            return oneCallAPI(currentWeatherAPI(inputLocationString));
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    // Can put in a state instead of country if searching for a US city.
    public static Location apiCalls(String city, String country) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#",city);
        inputLocationString = inputLocationString.replaceFirst("\\#\\#COUNTRY\\#\\#",country);
        inputLocationString = inputLocationString.replaceAll("\\s","\\%20");
        try {
            return oneCallAPI(currentWeatherAPI(inputLocationString));
        }
        catch(Exception e) {
            try {
                inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##STATE##,US&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
                inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#", city);
                inputLocationString = inputLocationString.replaceFirst("\\#\\#STATE\\#\\#", country);
                inputLocationString = inputLocationString.replaceAll("\\s","\\%20");
                return oneCallAPI(currentWeatherAPI(inputLocationString));
            }
            catch (Exception e2) {
                System.out.println(e);
                return null;
            }
        }
    }
    public static Location apiCalls(String city, String state, String country) throws IOException {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##STATE##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#CITY\\#\\#",city);
        inputLocationString = inputLocationString.replaceFirst("\\#\\#STATE\\#\\#",state);
        inputLocationString = inputLocationString.replaceFirst("\\#\\#COUNTRY\\#\\#",country);
        inputLocationString = inputLocationString.replaceAll("\\s","\\%20");
         try {
             return oneCallAPI(currentWeatherAPI(inputLocationString));
         }
         catch (Exception e) {
             System.out.println(e);
             return null;
         }
    }
    public static Location apiCalls(int cityIdOrZip) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?zip=##ZIP##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#ZIP\\#\\#", String.valueOf(cityIdOrZip));
        inputLocationString = inputLocationString.replaceAll("\\s", "\\%20");
        try {
            return oneCallAPI(currentWeatherAPI(inputLocationString));
        }
        catch (Exception e) {
            String inputLocationString2 = "http://api.openweathermap.org/data/2.5/weather?id=##CITYID##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
            inputLocationString2 = inputLocationString2.replaceFirst("\\#\\#CITYID\\#\\#", String.valueOf(cityIdOrZip));
            inputLocationString2 = inputLocationString2.replaceAll("\\s", "\\%20");
            try {
                return oneCallAPI(currentWeatherAPI(inputLocationString2));
            } catch (Exception e2) {
                System.out.println(e2);
                return null;
            }
        }
    }
    public static Location apiCalls(double lat, double lon) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?lat=##LAT##&lon=##LON##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#LAT\\#\\#", String.valueOf(lat));
        inputLocationString = inputLocationString.replaceFirst("\\#\\#LON\\#\\#", String.valueOf(lon));
        inputLocationString = inputLocationString.replaceAll("\\s","\\%20");
        try {
            return oneCallAPI(currentWeatherAPI(inputLocationString));
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public static Location apiCalls(int postal, String country) {
        String inputLocationString = "http://api.openweathermap.org/data/2.5/weather?zip=##POSTAL##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        inputLocationString = inputLocationString.replaceFirst("\\#\\#POSTAL\\#\\#", String.valueOf(postal));
        inputLocationString = inputLocationString.replaceFirst("\\#\\#COUNTRY\\#\\#", country);
        inputLocationString = inputLocationString.replaceAll("\\s","\\%20");
        try {
            return oneCallAPI(currentWeatherAPI(inputLocationString));
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static CurrentWeatherAPI currentWeatherAPI(String inputLocationString) throws IOException {
        URL jsonURL = new URL(inputLocationString);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("\n" + jsonURL);
        CurrentWeatherAPI currentWeatherAPI = mapper.readValue(jsonURL, CurrentWeatherAPI.class);
        return currentWeatherAPI;
    }

    private static Location oneCallAPI(CurrentWeatherAPI currentWeatherAPI) throws IOException {
        double inputLat, inputLon;
        String oneCallApiUrl = "http://api.openweathermap.org/data/2.5/onecall?lat=##LAT##&lon=##LON##&units=imperial&appid=98edb87e72911500a7f165a998c7fcf2";
        ObjectMapper mapper = new ObjectMapper();
        inputLat = currentWeatherAPI.getCoord().get("lat");
        inputLon = currentWeatherAPI.getCoord().get("lon");
        oneCallApiUrl = oneCallApiUrl.replaceFirst("\\#\\#LAT\\#\\#", String.valueOf(inputLat));
        oneCallApiUrl = oneCallApiUrl.replaceFirst("\\#\\#LON\\#\\#", String.valueOf(inputLon));
        Location location = mapper.readValue(new URL(oneCallApiUrl), Location.class);
        location.setName(currentWeatherAPI.getName());
        location.setCountry(currentWeatherAPI.getSys().get("country"));
        return location;
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
