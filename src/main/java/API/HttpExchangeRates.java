package API;

import API.CenterBank.ResponseExchangeRates;
import Vaadin.VaadinUI;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class HttpExchangeRates {
    private final static String RATES_REQUEST;//request
    private final static String CONFIGURATION_FILE = "/http_exchange_rates.properties";//имя файла с константами
    private HttpURLConnection httpURLConnection;
    private final static Logger logger = LogManager.getRootLogger();
    private VaadinUI context;

    //инициализация констант
    static {
        Properties properties = new Properties();

        try (InputStream inputStream = ClassLoader.class.getResourceAsStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(MarkerManager.getMarker("SERVER"), e.getMessage());
            throw new RuntimeException("Failed to read file " + CONFIGURATION_FILE, e);
        }

        RATES_REQUEST = properties.getProperty("RATES_REQUEST");

        logger.debug(MarkerManager.getMarker("SERVER"), "Константы проинициализированны \nRATES_REQUEST=" + RATES_REQUEST);
    }

    public HttpExchangeRates(VaadinUI context) {
        this.context = context;
    }

    /**
     * Метод для получения курса валют
     *
     * @return - возвращает объект курса валют
     */
    public Rates getRates() {
        try {
            URL url = new URL(RATES_REQUEST);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            byte[] result = new CustomInputStream(httpURLConnection.getInputStream()).readAllBytes();
            String response = new String(result);

            //парсим
            Gson gson = new Gson();
            ResponseExchangeRates responseExchangeRates = gson.fromJson(response, ResponseExchangeRates.class);
            if (responseExchangeRates == null || (responseExchangeRates.getValute() == null) ||
                    (responseExchangeRates.getValute().getUSD() == null || responseExchangeRates.getValute().getEUR() == null) ||
                    (responseExchangeRates.getValute().getUSD().getValue() == null && responseExchangeRates.getValute().getEUR().getValue() == null)) {
                if (context != null)
                    context.showNotification("Был изменен API,курсы валют неверны, обратитесь к администратору");
                logger.error(MarkerManager.getMarker("SERVER"), "Изменен API");
                return new Rates(999, 999);
            }

            return new Rates(responseExchangeRates.getValute().getUSD().getValue(), responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"), e.getMessage());
        } finally {
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
            logger.debug(MarkerManager.getMarker("SERVER"), "Disconnect CenterBank");
        }
        return null;
    }

    /**
     * Метод для получения курса валют с инкриментом прогресс бара
     *
     * @param progressBar - прогресс бар
     * @return - возвращает объект курса валют
     */
    public Rates getRates(ProgressBar progressBar) {
        try {
            URL url = new URL(RATES_REQUEST);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            progressBar.setValue(20.0f);

            byte[] result = new CustomInputStream(httpURLConnection.getInputStream()).readAllBytes();
            String response = new String(result);
            progressBar.setValue(40.0f);

            //парсим
            Gson gson = new Gson();
            ResponseExchangeRates responseExchangeRates = gson.fromJson(response, ResponseExchangeRates.class);
            progressBar.setValue(80.0f);
            if (responseExchangeRates == null || (responseExchangeRates.getValute() == null) ||
                    (responseExchangeRates.getValute().getUSD() == null || responseExchangeRates.getValute().getEUR() == null) ||
                    (responseExchangeRates.getValute().getUSD().getValue() == null && responseExchangeRates.getValute().getEUR().getValue() == null)) {
                if (context != null)
                    context.showNotification("Был изменен API,курсы валют неверны, обратитесь к администратору");
                logger.error(MarkerManager.getMarker("SERVER"), "Изменен API");
                return new Rates(999, 999);
            }

            return new Rates(responseExchangeRates.getValute().getUSD().getValue(), responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"), e.getMessage());
        } finally {
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
            logger.debug(MarkerManager.getMarker("SERVER"), "Disconnect CenterBank");
        }
        return null;
    }

}
