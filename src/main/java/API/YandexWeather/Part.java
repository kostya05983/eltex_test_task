package API.YandexWeather;

//class made with Documentation
//https://tech.yandex.ru/weather/doc/dg/concepts/forecast-response-test-docpage/
public class Part {
    private FactForParts night;
    private FactForParts morning;
    private FactForParts day;
    private FactForParts evening;
    private FactForParts day_short;
    private FactForParts night_short;


    public Part(FactForParts night, FactForParts morning, FactForParts day, FactForParts evening, FactForParts day_short, FactForParts night_short) {
        this.night = night;
        this.morning = morning;
        this.day = day;
        this.evening = evening;
        this.day_short = day_short;
        this.night_short = night_short;
    }

    public FactForParts getNight() {
        return night;
    }

    public void setNight(FactForParts night) {
        this.night = night;
    }

    public FactForParts getMorning() {
        return morning;
    }

    public void setMorning(FactForParts morning) {
        this.morning = morning;
    }

    public FactForParts getDay() {
        return day;
    }

    public void setDay(FactForParts day) {
        this.day = day;
    }

    public FactForParts getEvening() {
        return evening;
    }

    public void setEvening(FactForParts evening) {
        this.evening = evening;
    }

    public FactForParts getDay_short() {
        return day_short;
    }

    public void setDay_short(FactForParts day_short) {
        this.day_short = day_short;
    }

    public FactForParts getNight_short() {
        return night_short;
    }

    public void setNight_short(FactForParts night_short) {
        this.night_short = night_short;
    }
}
