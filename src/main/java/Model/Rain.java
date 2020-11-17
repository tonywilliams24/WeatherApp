package Model;

public class Rain {
    int h1, h3;

    public Rain(int h1, int h3) {
        this.h1 = h1;
        this.h3 = h3;
    }

    public Rain(int h1) {
        this.h1 = h1;
    }

    public Rain() {
    }

    public int getH1() {
        return h1;
    }



    public void setH1(int h1) {
        this.h1 = h1;
    }

    public int getH3() {
        return h3;
    }

    public void setH3(int h3) {
        this.h3 = h3;
    }
}
