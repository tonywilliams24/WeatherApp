package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static model.Location.*;

public class OneCallAPI {

    private double lat;
    private double lon;
    private String timezone;
    private long timezone_offset;
    private Current current;
    private Minutely[] minutely;
    private Hourly[] hourly;
    private Daily[] daily;
    private Alerts[] alerts;

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

    // Inputs currentWeatherAPI object and uses the Lat / Lon to get all weather information
    private static OneCallAPI oneCallAPI(double lat, double lon) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String oneCallApiUrl = (openWeatherURL).append(oneCallApiPath)
                                                .append(latQuery)
                                                .append(lat)
                                                .append(lonQuery)
                                                .append(lon)
                                                .append("&units=")
                                                .append(units)
                                                .append("&appid=")
                                                .append(apiKey).toString();
        System.out.println(oneCallApiUrl);
        return mapper.readValue(new URL(oneCallApiUrl), OneCallAPI.class);
    }
}
