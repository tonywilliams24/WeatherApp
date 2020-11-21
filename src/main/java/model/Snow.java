package model;

public class Snow {
    double h1, h3;

    public Snow(double h1, double h3) {
        this.h1 = h1;
        this.h3 = h3;
    }

    public Snow(int h1) {
        this.h1 = h1;
    }

    public Snow() {
    }

    public double get1h() {
        return h1;
    }

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
        return "Snow{" +
                "1h=" + h1 +
                ", 3h=" + h3 +
                '}';
    }
}
