package API;

public class Temperature {
    private String current;
    private String tomorrow;

    Temperature(String current, String tomorrow) {
        this.current = current;
        this.tomorrow = tomorrow;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(String tomorrow) {
        this.tomorrow = tomorrow;
    }

    @Override
    public String toString() {
        return "Temperature{"+
                "current="+current
                +", tomorrow="+tomorrow
                +"}";
    }
}
