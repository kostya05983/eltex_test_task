package YandexWeather;

public class TzInfo {
    private int offset;
    private String name;
    private String abbr;
    private boolean dst;

    public TzInfo(int offset, String name, String abbr, boolean dst) {
        this.offset = offset;
        this.name = name;
        this.abbr = abbr;
        this.dst = dst;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public boolean isDst() {
        return dst;
    }

    public void setDst(boolean dst) {
        this.dst = dst;
    }
}
