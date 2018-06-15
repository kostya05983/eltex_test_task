package API.YandexWeather;

//class made with Documentation
//https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/
public class FactForParts {
    private int temp_min;
    private int temp_max;
    private Integer temp_avg;
    private int feels_like;
    private String icon;
    private String condition;
    private String daytime;
    private boolean polar;
    private double wind_speed;
    private double wind_gust;
    private String wind_dir;
    private int pressure_mm;
    private int pressure_pa;
    private int humidity;
    private double prec_mm;
    private int prec_period;
    private int prec_type;
    private double prec_strength;
    private double cloudness;

    public FactForParts(int temp_min, int temp_max, int temp_avg, int feels_like, String icon, String condition,
                        String daytime, boolean polar, double wind_speed, double wind_gust, String wind_dir, int pressure_mm,
                        int pressure_pa, int humidity, double prec_mm, int prec_period, int prec_type, double prec_strength, double cloudness) {
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.temp_avg = temp_avg;
        this.feels_like = feels_like;
        this.icon = icon;
        this.condition = condition;
        this.daytime = daytime;
        this.polar = polar;
        this.wind_speed = wind_speed;
        this.wind_gust = wind_gust;
        this.wind_dir = wind_dir;
        this.pressure_mm = pressure_mm;
        this.pressure_pa = pressure_pa;
        this.humidity = humidity;
        this.prec_mm = prec_mm;
        this.prec_period = prec_period;
        this.prec_type = prec_type;
        this.prec_strength = prec_strength;
        this.cloudness = cloudness;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public Integer getTemp_avg() {
        return temp_avg;
    }

    public void setTemp_avg(int temp_avg) {
        this.temp_avg = temp_avg;
    }

    public int getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(int feels_like) {
        this.feels_like = feels_like;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDaytime() {
        return daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public boolean isPolar() {
        return polar;
    }

    public void setPolar(boolean polar) {
        this.polar = polar;
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

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public int getPressure_mm() {
        return pressure_mm;
    }

    public void setPressure_mm(int pressure_mm) {
        this.pressure_mm = pressure_mm;
    }

    public int getPressure_pa() {
        return pressure_pa;
    }

    public void setPressure_pa(int pressure_pa) {
        this.pressure_pa = pressure_pa;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPrec_mm() {
        return prec_mm;
    }

    public void setPrec_mm(double prec_mm) {
        this.prec_mm = prec_mm;
    }

    public int getPrec_period() {
        return prec_period;
    }

    public void setPrec_period(int prec_period) {
        this.prec_period = prec_period;
    }

    public int getPrec_type() {
        return prec_type;
    }

    public void setPrec_type(int prec_type) {
        this.prec_type = prec_type;
    }

    public double getPrec_strength() {
        return prec_strength;
    }

    public void setPrec_strength(double prec_strength) {
        this.prec_strength = prec_strength;
    }

    public double getCloudness() {
        return cloudness;
    }

    public void setCloudness(double cloudness) {
        this.cloudness = cloudness;
    }
}
