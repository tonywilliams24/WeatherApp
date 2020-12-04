package model;

// Rain Class used by Forecast class

public class Rain {
    double h1, h3;

    public Rain(double h1, double h3) {
        this.h1 = h1;
        this.h3 = h3;
    }

    public Rain(int h1) {
        this.h1 = h1;
    }

    public Rain() {
    }

    public double get1h() {
        return h1;
    }


    // API uses "Set 1h" but java does not allow variable names that start with numbers
    
    public void set1h(double h1) {
        this.h1 = h1;
    }

    public double get3h() {
        return h3;
    }

    public void set3h(double h3) {
        this.h3 = h3;
    }


    @Override
    public String toString() {
        return "Rain{" +
                "h1=" + h1 +
                ", h3=" + h3 +
                '}';
    }
}
