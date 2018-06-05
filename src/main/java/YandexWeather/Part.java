package YandexWeather;

public class Part {
    private Fact night;
    private Fact morning;
    private Fact day;
    private Fact evening;
    private Fact day_short;
    private Fact night_short;

    public Part(Fact night, Fact morning, Fact day, Fact evening, Fact day_short, Fact night_short) {
        this.night = night;
        this.morning = morning;
        this.day = day;
        this.evening = evening;
        this.day_short = day_short;
        this.night_short = night_short;
    }

    public Fact getNight() {
        return night;
    }

    public void setNight(Fact night) {
        this.night = night;
    }

    public Fact getMorning() {
        return morning;
    }

    public void setMorning(Fact morning) {
        this.morning = morning;
    }

    public Fact getDay() {
        return day;
    }

    public void setDay(Fact day) {
        this.day = day;
    }

    public Fact getEvening() {
        return evening;
    }

    public void setEvening(Fact evening) {
        this.evening = evening;
    }

    public Fact getDay_short() {
        return day_short;
    }

    public void setDay_short(Fact day_short) {
        this.day_short = day_short;
    }

    public Fact getNight_short() {
        return night_short;
    }

    public void setNight_short(Fact night_short) {
        this.night_short = night_short;
    }
}
