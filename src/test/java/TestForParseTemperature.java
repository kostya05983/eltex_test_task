import API.HttpTemperature;
import API.Temperature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestForParseTemperature {
    private final static Logger logger = LogManager.getLogger(TestForParseTemperature.class);

    public static void main(String[] args) {
        HttpTemperature httpTemperature = new HttpTemperature();
        Temperature temperature = httpTemperature.getTemperature("Новосибирск");
        if(temperature==null)
            logger.error(new Object() {
            }.getClass().getEnclosingConstructor().getName()+":temperature for Новосибирск was not gotten");

        temperature = httpTemperature.getTemperature("Москва");
        if(temperature==null)
            logger.error(new Object() {
            }.getClass().getEnclosingConstructor().getName()+":temperature for Москва was not gotten");
        temperature = httpTemperature.getTemperature("Санкт-Петербург");
        if(temperature==null)
            logger.error(new Object() {
            }.getClass().getEnclosingConstructor().getName()+":temperature for Санкт-Петербург was not gotten");
    }
}
