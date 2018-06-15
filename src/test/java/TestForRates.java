import API.HttpExchangeRates;
import API.Rates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestForRates {
    private final static Logger logger = LogManager.getLogger(TestForRates.class);

    public static void main(String[] args) {
        HttpExchangeRates httpExchangeRates = new HttpExchangeRates(null);

        Rates rates = httpExchangeRates.getRates();

        if(rates==null)
            logger.error(new Object() {
            }.getClass().getEnclosingConstructor().getName()+":rates was not gotten");

    }
}
