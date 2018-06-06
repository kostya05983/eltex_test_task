package API.YandexWeather;

import java.util.List;

//class made with Documentation
//https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/
public class ResponseWeather {
    private int now;
    private String now_dt;
    private Info info;
    private GeoObject geo_object;
    private Fact fact;
    private List<ForeCast> forecasts;

    public ResponseWeather(int now, String now_dt, Info info, GeoObject geo_object, Fact fact, List<ForeCast> forecasts) {
        this.now = now;
        this.now_dt = now_dt;
        this.info = info;
        this.geo_object = geo_object;
        this.fact = fact;
        this.forecasts = forecasts;
    }

    public int getNow() {
        return now;
    }

    public void setNow(int now) {
        this.now = now;
    }

    public String getNow_dt() {
        return now_dt;
    }

    public void setNow_dt(String now_dt) {
        this.now_dt = now_dt;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public GeoObject getGeo_object() {
        return geo_object;
    }

    public void setGeo_object(GeoObject geo_object) {
        this.geo_object = geo_object;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public List<ForeCast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForeCast> forecasts) {
        this.forecasts = forecasts;
    }
}
