package API.YandexWeather;

//class made with Documentation
//https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/
public class Fact {
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
    private String dayTime;
    private boolean polar;
    private String season;
    private int prec_type;
    private double prec_strength;
    private double cloudness;
    private int obs_time;

    public Fact(int temp, int feels_like, String icon, String condition, double wind_speed, double wind_gust,
                String wind_dir, int pressure_mm, int pressure_pa, int humidity, String dayTime, boolean polar,
                String season, int prec_type, double prec_strength, double cloudness, int obs_time) {
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
        this.dayTime = dayTime;
        this.polar = polar;
        this.season = season;
        this.prec_type = prec_type;
        this.prec_strength = prec_strength;
        this.cloudness = cloudness;
        this.obs_time = obs_time;
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

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public boolean isPolar() {
        return polar;
    }

    public void setPolar(boolean polar) {
        this.polar = polar;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
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

    public int getObs_time() {
        return obs_time;
    }

    public void setObs_time(int obs_time) {
        this.obs_time = obs_time;
    }
}
