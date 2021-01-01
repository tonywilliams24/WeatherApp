package db;

public class Favorite {

    private String name;
    private String country;
    private double lat;
    private double lon;

    public Favorite() {
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
}
