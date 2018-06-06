package API.CenterBank;

public class ResponseExchangeRates {
    private String Date;
    private String previousDate;
    private String PreviousURL;
    private String Timestamp;
    private Valute Valute;

    public ResponseExchangeRates(String date, String previousDate, String previousURL, String timestamp, API.CenterBank.Valute valute) {
        Date = date;
        this.previousDate = previousDate;
        PreviousURL = previousURL;
        Timestamp = timestamp;
        Valute = valute;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(String previousDate) {
        this.previousDate = previousDate;
    }

    public String getPreviousURL() {
        return PreviousURL;
    }

    public void setPreviousURL(String previousURL) {
        PreviousURL = previousURL;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public API.CenterBank.Valute getValute() {
        return Valute;
    }

    public void setValute(API.CenterBank.Valute valute) {
        Valute = valute;
    }
}
