package API;

import CenterBank.ResponseExchangeRates;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpExchangeRates {
    private final String GET_DATA = "https://www.cbr-xml-daily.ru/daily_json.js";
    private HttpURLConnection httpURLConnection;
    private final Logger logger = LogManager.getLogger(HttpExchangeRates.class);

    public Rates getRates() {
        try {
            URL url = new URL(GET_DATA);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");

            byte[] result = httpURLConnection.getInputStream().readAllBytes();
            String response = new String(result);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : response was gotten");

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

    public Rates getRates(ProgressBar progressBar) {
        try {
            URL url = new URL(GET_DATA);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            progressBar.setValue(20.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");

            byte[] result = httpURLConnection.getInputStream().readAllBytes();
            String response = new String(result);
            progressBar.setValue(40.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : response was gotten");

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
