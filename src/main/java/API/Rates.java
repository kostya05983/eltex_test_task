package API;

public class Rates {
    private double USD;
    private double EUR;

    Rates(double USD, double EUR) {
        this.USD = USD;
        this.EUR = EUR;
    }

    public double getUSD() {
        return USD;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public double getEUR() {
        return EUR;
    }

    public void setEUR(double EUR) {
        this.EUR = EUR;
    }
}
