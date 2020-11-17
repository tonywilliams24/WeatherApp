package Model;

public class Main {
    double temp, feels_like, temp_min, temp_max, pressure, sea_level, grnd_level;
    int humidity;

    public Main(double temp, double feels_like, double temp_min, double temp_max, double pressure, double sea_level, double grnd_level, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
    }

    public Main(double temp, double feels_like, double temp_min, double temp_max, double pressure, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public Main() {
    }
}
