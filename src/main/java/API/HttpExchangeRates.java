package API;

import API.CenterBank.ResponseExchangeRates;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final static Logger logger = LogManager.getLogger(HttpExchangeRates.class);


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
    }

    //Method for getting Rates for test
    public Rates getRates() {
        try {
            //openConnection
            URL url = new URL(RATES_REQUEST);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");

            //getting Data
            byte[] result = new CustomInputStream(httpURLConnection.getInputStream()).readAllBytes();
            String response = new String(result);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : response was gotten");

            //parse Data
            Gson gson = new Gson();
            ResponseExchangeRates responseExchangeRates = gson.fromJson(response, ResponseExchangeRates.class);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : response was parsed");

            return new Rates(responseExchangeRates.getValute().getUSD().getValue(), responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        } finally {
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }
        return null;
    }

    //Method for getting Rates
    public Rates getRates(ProgressBar progressBar) {
        //open Connection
        try {
            URL url = new URL(RATES_REQUEST);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            progressBar.setValue(20.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");

            //get Data
            byte[] result = new CustomInputStream(httpURLConnection.getInputStream()).readAllBytes();
            String response = new String(result);
            progressBar.setValue(40.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : response was gotten");

            //parse Data
            Gson gson = new Gson();
            ResponseExchangeRates responseExchangeRates = gson.fromJson(response, ResponseExchangeRates.class);
            progressBar.setValue(80.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : response was parsed");

            return new Rates(responseExchangeRates.getValute().getUSD().getValue(), responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        } finally {
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }
        return null;
    }

}
