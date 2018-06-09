package API;

import API.CenterBank.ResponseExchangeRates;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class HttpExchangeRates implements CustomInputStreamInterface{
    private final String GET_DATA = "https://www.cbr-xml-daily.ru/daily_json.js";//request
    private HttpURLConnection httpURLConnection;
    private final Logger logger = LogManager.getLogger(HttpExchangeRates.class);

    //Method for getting Rates for test
    public Rates getRates() {
        try {
            //openConnection
            URL url = new URL(GET_DATA);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");

            //getting Data
            byte[] result = readAllBytes(httpURLConnection.getInputStream());
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
            URL url = new URL(GET_DATA);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            progressBar.setValue(20.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");

            //get Data
            byte[] result = readAllBytes(httpURLConnection.getInputStream());
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

    public byte[] readAllBytes(InputStream in) throws IOException {
        byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
        int capacity = buf.length;
        int nread = 0;
        int n;
        for (;;) {
            // read to EOF which may read more or less than initial buffer size
            while ((n = in.read(buf, nread, capacity - nread)) > 0)
                nread += n;

            // if the last call to read returned -1, then we're done
            if (n < 0)
                break;

            // need to allocate a larger buffer
            if (capacity <= MAX_BUFFER_SIZE - capacity) {
                capacity = capacity << 1;
            } else {
                if (capacity == MAX_BUFFER_SIZE)
                    throw new OutOfMemoryError("Required array size too large");
                capacity = MAX_BUFFER_SIZE;
            }
            buf = Arrays.copyOf(buf, capacity);
        }
        return (capacity == nread) ? buf : Arrays.copyOf(buf, nread);
    }



}
