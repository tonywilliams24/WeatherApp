package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Location {

    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private String visibility;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    private long dt;
    private Sys sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;
    private Image icon;
    private String iconUrlString = "icons/##ICON##@4x.png";

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

    Location() {
    }
    public Location(String city) {
        String weatherString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        weatherString = weatherString.replaceFirst("\\#\\#CITY\\#\\#",city);
        try {
            setMemberVars(weatherString);
        }
        catch (Exception e) {
            System.out.println("Location Not Found");
        }
    }
    // Can put in a state instead of country if searching for a US city.
    public Location(String city, String country) {
        String weatherString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        weatherString = weatherString.replaceFirst("\\#\\#CITY\\#\\#",city);
        weatherString = weatherString.replaceFirst("\\#\\#COUNTRY\\#\\#",country);
        try {
            setMemberVars(weatherString);
        }
        catch(Exception e) {
            try {
                weatherString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##STATE##,US&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
                weatherString = weatherString.replaceFirst("\\#\\#CITY\\#\\#", city);
                weatherString = weatherString.replaceFirst("\\#\\#STATE\\#\\#", country);
                setMemberVars(weatherString);
            }
            catch (Exception e2) {
                System.out.println("Location Not Found");
            }
        }
    }

    public Location(String city, String state, String country) throws IOException {
        String weatherString = "http://api.openweathermap.org/data/2.5/weather?q=##CITY##,##STATE##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        weatherString = weatherString.replaceFirst("\\#\\#CITY\\#\\#",city);
        weatherString = weatherString.replaceFirst("\\#\\#STATE\\#\\#",state);
        weatherString = weatherString.replaceFirst("\\#\\#COUNTRY\\#\\#",country);
         try {
            setMemberVars(weatherString);
         }
         catch (Exception e) {
            System.out.println("Location Not Found");
         }
    }
    public Location(int cityID) {
        String weatherString = "http://api.openweathermap.org/data/2.5/weather?id=##CITYID##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        weatherString = weatherString.replaceFirst("\\#\\#CITYID\\#\\#", String.valueOf(cityID));
        try {
            setMemberVars(weatherString);
        }
        catch (Exception e) {
            System.out.println("Location Not Found");
        }
    }
    public Location(double lat, double lon) {
        String weatherString = "http://api.openweathermap.org/data/2.5/weather?lat=##LAT##&lon=##LON##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        weatherString = weatherString.replaceFirst("\\#\\#LAT\\#\\#", String.valueOf(lat));
        weatherString = weatherString.replaceFirst("\\#\\#LON\\#\\#", String.valueOf(lon));
        try {
            setMemberVars(weatherString);
        }
        catch (Exception e) {
            System.out.println("Location Not Found");
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
        String weatherString = "http://api.openweathermap.org/data/2.5/weather?zip=##POSTAL##,##COUNTRY##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        weatherString = weatherString.replaceFirst("\\#\\#POSTAL\\#\\#", postalString);
        weatherString = weatherString.replaceFirst("\\#\\#COUNTRY\\#\\#", country);
        try {
            setMemberVars(weatherString);
        }
        catch (Exception e) {
            System.out.println("Location Not Found");
        }
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    private void setMemberVars(String weatherString) throws IOException {
        URL jsonURL = new URL(weatherString);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("\n" + jsonURL);
        Location tmp = mapper.readValue(jsonURL, Location.class);
        this.coord = tmp.coord;
        this.weather = tmp.weather;
        this.base = tmp.base;
        this.main = tmp.main;
        this.visibility = tmp.visibility;
        this.wind = tmp.wind;
        this.clouds = tmp.clouds;
        this.rain = tmp.rain;
        this.snow = tmp.snow;
        this.dt = tmp.dt;
        this.sys = tmp.sys;
        this.timezone = tmp.timezone;
        this.id = tmp.id;
        this.name = tmp.name;
        this.cod = tmp.cod;
        String tmpIcon = this.weather[0].getIcon();
        iconUrlString = iconUrlString.replaceFirst("##ICON##",tmpIcon);
        this.icon = new Image(iconUrlString);
        System.out.println(this);

    }
    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
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

    public void setId(int id) {
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

    // Returns Latitude and Longitude
    public double getLon() {
        return coord.getLon();
    }
    public double getLat() {
        return coord.getLat();
    }
    // Returns weather conditions
    public String getWeatherId() {
        return getWeatherString("id");
    }
    public String getWeatherMain() {
        return getWeatherString("main");
    }
    public String getWeatherDescription() {
        return getWeatherString("description");
    }

    public String getWeatherIcon(int i) {
        return getWeatherString("icon");
    }

    private String getWeatherString(String description) {
        List<String> weatherList = new ArrayList<>();
        for (Weather w : weather) {
            weatherList.add(w.getDescription());
        }
        return weatherList.toString().trim().replaceFirst("\\[","").replaceFirst("\\]","");
    }
    // Returns Air information (temp, humidity, etc)
    public double getTemp() {
        return this.main.getTemp();
    }
    public double getFeelsLike() {
        return this.main.getFeels_like();
    }
    public double getMinTemp() {
        return this.main.getTemp_min();
    }
    public double getMaxTemp() {
        return this.main.getTemp_max();
    }
    public double getPressure() {
        return this.main.getPressure();
    }
    public double getHumidity() {
        return this.main.getHumidity();
    }
    // Returns Wind Information
    public double getWindSpeed() {
        return this.wind.getSpeed();
    }
    public double getWindDeg() {
        return this.wind.getDeg();
    }
    public double getWindGust() {
        return this.wind.getGust();
    }
    // Returns Cloudiness Percentage (in 0.00 format)
    public double getCloudPer() {
        return this.clouds.getAll();
    }
    // Returns other location information
    public String getCountryCode() {
        return this.sys.getCountry();
    }
    public long getSunrise() {
        return this.sys.getSunrise();
    }
    public long getSunset() {
        return this.sys.getSunset();
    }

    @Override
    public String toString() {
        return "Location{" +
                "coord=" + coord +
                ", weather=" + Arrays.toString(weather) +
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

/*
    Example Model.Weather JSON

    {
	"coord" : {
		"lon": -122.63,
		"lat": 47.57
	},
	"weather" : [ {
		"id" : 800,
		"main" : "Clear",
		"description": "clear sky",
		"icon" : "01d"
		} ],
	"base" : "stations",
	"main" : {
		"temp" : 284.77,
		"feels_like" : 280.52,
		"temp_min":283.71,
		"temp_max":286.48,
		"pressure":1029,
		"humidity":66
	},
	"visibility" : 10000,
	"wind" : {
		"speed" : 4.6,
		"deg" : 50
	},
	"clouds" : {
		"all" : 1
	},
	"dt" : 1604179162,
	"sys" : {
		"type" : 1,
		"id" : 5451,
		"country" : "US",
		"sunrise" : 1604156030,
		"sunset" : 1604192055
		},
	"timezone" : -25200,
	"id" : 5788054,
	"name" : "Bremerton",
	"cod" :
     */
}
