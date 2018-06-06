package API.YandexWeather;

//class made with Documentation
//https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/
public class Hour {
    private int hour;
    private int hour_ts;
    private int temp;
    private int feels_like;
    private String icon;
    private String condition;
    private double wind_speed;
    private double wind_gust;
    private String wind_dir;
    private int pressure_mm;
    private int pressure_pa;
    private int humidity;
    private int prec_mm;
    private int prec_period;
    private int prec_type;
    private double prec_strength;
    private int cloudness;
    private boolean fallback_temp;
    private boolean fallback_prec;

    public Hour(int hour, int hour_ts, int temp, int feels_like, String icon,
                String condition, double wind_speed, double wind_gust, String wind_dir, int pressure_mm,
                int pressure_pa, int humidity, int prec_mm, int prec_period, int prec_type, double prec_strength,
                int cloudness, boolean fallback_temp, boolean fallback_prec) {
        this.hour = hour;
        this.hour_ts = hour_ts;
        this.temp = temp;
        this.feels_like = feels_like;
        this.icon = icon;
        this.condition = condition;
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
        this.fallback_temp = fallback_temp;
        this.fallback_prec = fallback_prec;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour_ts() {
        return hour_ts;
    }

    public void setHour_ts(int hour_ts) {
        this.hour_ts = hour_ts;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
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

    public int getPrec_mm() {
        return prec_mm;
    }

    public void setPrec_mm(int prec_mm) {
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

    public int getCloudness() {
        return cloudness;
    }

    public void setCloudness(int cloudness) {
        this.cloudness = cloudness;
    }

    public boolean isFallback_temp() {
        return fallback_temp;
    }

    public void setFallback_temp(boolean fallback_temp) {
        this.fallback_temp = fallback_temp;
    }

    public boolean isFallback_prec() {
        return fallback_prec;
    }

    public void setFallback_prec(boolean fallback_prec) {
        this.fallback_prec = fallback_prec;
    }
}
