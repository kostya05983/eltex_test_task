package API;

public class Coordinate {
    private double x;
    private double y;

    Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x
                + ",y=" + y
                + "}";
    }
}
