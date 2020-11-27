package model;

public class Daily extends Forecast {
    Temps temp;
    Temps feels_like;
    double rain;
    double snow;

    public Daily() {
    }

    public double getDailyRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getDailySnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public Temps getTemps() {
        return temp;
    }

    public void setTemp(Temps temps) {
        this.temp = temps;
    }

    public Temps getFeels_likeTemps() {
        return feels_like;
    }

    public void setFeels_like(Temps feels_like) {
        this.feels_like = feels_like;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "temp=" + temp +
                ", feels_like=" + feels_like +
                ", rain=" + rain +
                ", snow=" + snow +
                "} " + super.toString();
    }
}
