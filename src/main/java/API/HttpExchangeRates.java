package API;

import API.CenterBank.ResponseExchangeRates;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class HttpExchangeRates {
    private final static String RATES_REQUEST;//request
    //private final static String CONFIGURATION_FILE = "/http_exchange_rates.properties";//имя файла с константами
    private final static String CONFIGURATION_FILE = "/home/kostya05983/lol/testtask/src/main/resources/http_exchange_rates.properties";//имя файла с константами
    private HttpURLConnection httpURLConnection;
    private final static Logger logger = LogManager.getRootLogger();

    //инициализация констант
    static {
        Properties properties = new Properties();

        //try (InputStream inputStream = ClassLoader.class.getResourceAsStream(CONFIGURATION_FILE)) {
        try (FileInputStream inputStream = new FileInputStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName()+":"+e.getMessage());
            throw new RuntimeException("Failed to read file " + CONFIGURATION_FILE, e);
        }

        RATES_REQUEST = properties.getProperty("RATES_REQUEST");

        logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
        }.getClass().getEnclosingMethod().getName() + " : константы проинициализированны \nRATES_REQUEST="+RATES_REQUEST);
    }

    //Method for getting Rates for test
    public Rates getRates() {
        try {
            URL url = new URL(RATES_REQUEST);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            byte[] result = new CustomInputStream(httpURLConnection.getInputStream()).readAllBytes();
            String response = new String(result);

            //парсим
            Gson gson = new Gson();
            ResponseExchangeRates responseExchangeRates = gson.fromJson(response, ResponseExchangeRates.class);

            return new Rates(responseExchangeRates.getValute().getUSD().getValue(), responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        } finally {
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }
        return null;
    }

    //Method for getting Rates
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

            return new Rates(responseExchangeRates.getValute().getUSD().getValue(), responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        } finally {
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }
        return null;
    }

}
