package API.YandexWeather;

//class made with Documentation
//https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/
public class Info {
    private double lat;
    private double lon;
    private int geoid;
    private String slug;
    private TzInfo tzinfo;
    private int def_pressure_mm;
    private int def_pressure_pa;
    private String url;

    public Info(double lat, double lon, int geoid, String slug, TzInfo tzinfo, int def_pressure_mm, int def_pressure_pa, String url) {
        this.lat = lat;
        this.lon = lon;
        this.geoid = geoid;
        this.slug = slug;
        this.tzinfo = tzinfo;
        this.def_pressure_mm = def_pressure_mm;
        this.def_pressure_pa = def_pressure_pa;
        this.url = url;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getGeoid() {
        return geoid;
    }

    public void setGeoid(int geoid) {
        this.geoid = geoid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public TzInfo getTzinfo() {
        return tzinfo;
    }

    public void setTzinfo(TzInfo tzinfo) {
        this.tzinfo = tzinfo;
    }

    public int getDef_pressure_mm() {
        return def_pressure_mm;
    }

    public void setDef_pressure_mm(int def_pressure_mm) {
        this.def_pressure_mm = def_pressure_mm;
    }

    public int getDef_pressure_pa() {
        return def_pressure_pa;
    }

    public void setDef_pressure_pa(int def_pressure_pa) {
        this.def_pressure_pa = def_pressure_pa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
