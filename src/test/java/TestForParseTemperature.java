import API.HttpTemperature;
import API.Temperature;

public class TestForParseTemperature {

    public static void main(String[] args) {
        HttpTemperature httpTemperature = new HttpTemperature();
        Temperature temperature = httpTemperature.getTemperature("Новосибирск");

        temperature = httpTemperature.getTemperature("Москва");
        temperature = httpTemperature.getTemperature("Санкт-Петербург");
    }
}
