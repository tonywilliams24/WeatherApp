import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Weather {

    private Map<String, Double> coord;
    private List<Map<String, String>> weather;
    private String base;
    private Map<String, Double> main;
    private String visibility;
    private Map<String, Double> wind;
    private Map<String, Double> clouds;
    private Map<String, Double> rain;
    private Map<String, Double> snow;
    private int dt;
    private Map<String, String> sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

    Weather() {
    }
    Weather(String city) {
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
    Weather(String city, String country) {
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

    Weather(String city, String state, String country) {
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
    Weather(int cityID) {
        String weatherString = "http://api.openweathermap.org/data/2.5/weather?id=##CITYID##&units=imperial&APPID=98edb87e72911500a7f165a998c7fcf2";
        weatherString = weatherString.replaceFirst("\\#\\#CITYID\\#\\#", String.valueOf(cityID));
        try {
            setMemberVars(weatherString);
        }
        catch (Exception e) {
            System.out.println("Location Not Found");
        }
    }
    Weather(double lat, double lon) {
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
    Weather(int postal, String country) {
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

    private void setMemberVars(String weatherString) throws IOException {
        URL jsonURL = new URL(weatherString);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(jsonURL);
        Weather tmp = mapper.readValue(jsonURL, Weather.class);
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
        System.out.println(this.main);
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

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
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

    public int getId() {
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

    @Override
    public String toString() {
        return "Weather{" +
                "coord=" + coord +
                ", weather=" + Arrays.toString(weather.toArray()) +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility='" + visibility + '\'' +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", timezone=" + timezone +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }

    // ((k−273.15)*(9/5)+32);

    public double kToF(double k) {
        return (k-273.15)*(9/5)+32;
    }
    // Returns Latitude and Longitude
    public double getLon() {
        return coord.get("lon");
    }
    public double getLat() {
        return coord.get("lat");
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
        for (Map m : weather) {
            weatherList.add((String)m.get(description));
        }
        return weatherList.toString().trim().replaceFirst("\\[","").replaceFirst("\\]","");
    }
    // Returns Air information (temp, humidity, etc)
    public double getTemp() {
        return this.main.get("temp");
    }
    public double getFeelsLike() {
        return this.main.get("feels_like");
    }
    public double getMinTemp() {
        return this.main.get("temp_min");
    }
    public double getMaxTemp() {
        return this.main.get("temp_max");
    }
    public double getPressure() {
        return this.main.get("pressure");
    }
    public double getHumidity() {
        return this.main.get("humidity");
    }
    // Returns Wind Information
    public double getWindSpeed() {
        return this.wind.get("speed");
    }
    public int getWindDeg() {
        return this.wind.get("deg").intValue();
    }
    public double getWindGust() {
        return this.wind.get("gust");
    }
    // Returns Cloudiness Percentage (in 0.00 format)
    public double getCloudPer() {
        return this.clouds.get("all");
    }
    // Returns other location information
    public String getCountryCode() {
        return this.sys.get("country");
    }
    public String getSunrise() {
        return this.sys.get("sunrise");
    }
    public String getSunSst() {
        return this.sys.get("sunset");
    }



    /*
    Example Weather JSON

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
