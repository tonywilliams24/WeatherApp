package db;

import model.Location;

public class Favorite implements Comparable<Favorite> {

    private String name;
    private String country;
    private double lat;
    private double lon;

    public Favorite() {
    }

    public Favorite(String name, String country, double lat, double lon) {
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    public Favorite(Location location) {
        this.name = location.getName();
        this.country = location.getCountry();
        this.lat = location.getLat();
        this.lon = location.getLon();
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

    @Override
    public String toString() {
        return "Favorite{" +
                "cityName='" + name + '\'' +
                ", countryCode='" + country + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    @Override
    public int compareTo(Favorite o) {
        double latDiff = (this.lat - o.lat);
        if(latDiff != 0) return (int)latDiff;
        else return (int) (this.lon - o.lon);
    }
}
