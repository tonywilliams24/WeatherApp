package model;

// Abstract class that Current / Daily / Houry / Minutely subclasses are derived from

import java.util.Arrays;

public abstract class Forecast {

    private long dt;
    private long sunrise;
    private long sunset;
    private double temp;
    private double feels_like;
    private int pressure;
    private int humidity;
    private double dew_point;
    private int clouds;
    private double uvi;
    private String uviColor;
    private String uviRisk;
    private double visibility; // check if double or int is needed
    private double wind_speed;
    private double wind_gust;
    private int wind_deg; // degrees
    private String wind_dir; // direction (N, NE, E, SE, S, SW, W, NW)
    private Weather[] weather;
    private double pop;
    private Rain rain;
    private Snow snow;

    public Forecast() {
    }

    public Forecast(long dt, long sunrise, long sunset, double temp, double feels_like, int pressure, int humidity, double dew_point, int clouds, double uvi, double visibility, double wind_speed, double wind_gust, int wind_deg, Weather[] weather, double pop, Rain rain, Snow snow) {
        this.dt = dt;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.temp = temp;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
        this.dew_point = dew_point;
        this.clouds = clouds;
        this.uvi = uvi;
        this.visibility = visibility;
        this.wind_speed = wind_speed;
        this.wind_gust = wind_gust;
        this.wind_deg = wind_deg;
        this.weather = weather;
        this.pop = pop;
        this.rain = rain;
        this.snow = snow;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getDew_point() {
        return dew_point;
    }

    public void setDew_point(double dew_point) {
        this.dew_point = dew_point;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getUvi() {
        return uvi;
    }

    public void setUvi(double uvi) {
        this.uvi = uvi;
        this.uviColor = uviToColor(this.uvi);
        this.uviRisk = uviToRisk(this.uvi);
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public double getWind_gust() {
        return wind_gust;
    }

    public void setWind_gust(double wind_gust) {
        this.wind_gust = wind_gust;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    // Added setting Wind Direction to Wind Degrees setter due to ObjectMapper using Setters as opposed to Constructors to set member variables and Wind Direction is NOT in API
    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
        this.wind_dir = degToDir(this.wind_deg);
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public Rain getRain() {
        return rain;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
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

    public String getUviRisk() {
        return uviRisk;
    }

    public void setUviRisk(String uviRisk) {
        this.uviRisk = uviRisk;
    }

    public String degToDir(int wind_deg) {
        // Arranged based on (my best guess of) the most common wind directions around Bremerton, WA
        // "Make common case fast"
        if (wind_deg >= 67.5 && wind_deg <= 112.5) {
            return "E";
        } else if (wind_deg > 22.5 && wind_deg < 67.5) {
            return "NE";
        } else if ((wind_deg >= 337.5 || wind_deg <= 22.5) && wind_deg >= 0 && wind_deg <= 360) {
            return "N";
        } else if (wind_deg > 112.5 && wind_deg < 157.5) {
            return "SE";
        } else if (wind_deg >= 157.5 && wind_deg <= 202.5) {
            return "S";
        } else if (wind_deg > 202.5 && wind_deg < 247.5) {
            return "SW";
        } else if (wind_deg >= 247.5 && wind_deg <= 292.5) {
            return "W";
        } else if (wind_deg > 292.5 && wind_deg < 337.5) {
            return "NW";
        }
        return null;
    }

    public String getWeatherDescription() {
        StringBuilder weatherSB = new StringBuilder();
        int weatherSBLength;

        if(weather.length!=1) {
            if(weather.length==2) return String.format("%1$s and %2$s",weather[0].getDescription(), weather[1].getDescription());
            else {
                for (Weather weatherObj : weather) {
                    weatherSB.append(weatherObj.getDescription())
                            .append(", ");
                }
                weatherSBLength = weatherSB.length() - 2;
                return weatherSB.delete(weatherSBLength, weatherSBLength+2)
                        .insert(weatherSB.lastIndexOf(", ")+1," and")
                        .toString();
            }
        }
        else return weather[0].getDescription();
    }

    public String getUviColor() {
        return uviColor;
    }

    public void setUviColor(String uviColor) {
        this.uviColor = uviColor;
    }

    public String uviToColor(double uvi) {
        if(uvi<0){
            return "black";
        }
        else if(uvi<2.5) {
            return "green";
        }
        else if(uvi<5.5) {
            return "yellow";
        }
        else if(uvi<7.5) {
            return "orange";
        }
        else if(uvi<10.5) {
            return "red";
        }
        else {
            return "violet";
        }
    }

    public String uviToRisk(double uvi) {
        if(uvi<2.5) {
            return "Low";
        }
        else if(uvi<5.5) {
            return "Moderate";
        }
        else if(uvi<7.5) {
            return "High";
        }
        else if(uvi<10.5) {
            return "Very High";
        }
        else {
            return "Extreme";
        }
    }

    public static String capitalize(String string) {
        if(string!=null) {
            String[] stringArray = string.split(" ");
            if(stringArray.length!=1) {
                StringBuilder stringBuilder = new StringBuilder(string.length());
                for (int i = 0; i < stringArray.length; i++) {
                    if(!stringArray[i].equals("and")) {
                        stringBuilder = stringBuilder.append(Character.toUpperCase(stringArray[i].charAt(0)))
                                                     .append(stringArray[i].substring(1))
                                                     .append(" ");
                    }
                    else {
                        stringBuilder = stringBuilder.append(stringArray[i])
                                                     .append(" ");
                    }
                }
                return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
            }
            else return (Character.toUpperCase(string.charAt(0)))
                    + (string.substring(1));
        }
        else return null;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "dt=" + dt +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", temp=" + temp +
                ", feels_like=" + feels_like +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dew_point=" + dew_point +
                ", clouds=" + clouds +
                ", uvi=" + uvi +
                ", visibility=" + visibility +
                ", wind_speed=" + wind_speed +
                ", wind_gust=" + wind_gust +
                ", wind_deg=" + wind_deg +
                ", weather=" + Arrays.toString(weather) +
                ", pop=" + pop +
                ", rain=" + rain +
                ", snow=" + snow +
                '}';
    }
}
