package model;

public class Minutely extends Forecast {
    public Minutely() {
    }

    private double precipitation;

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }


    @Override
    public String toString() {
        return "Minutely{" +
                "precipitation=" + precipitation +
                "} " + super.toString();
    }
}
