package API;

import CenterBank.ResponseExchangeRates;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpExchangeRates {
    private final String GET_DATA = "https://www.cbr-xml-daily.ru/daily_json.js";
    private HttpURLConnection httpURLConnection;

    public Rates getRates() {
        try {
            URL url = new URL(GET_DATA);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            byte[] result = httpURLConnection.getInputStream().readAllBytes();
            String response = new String(result);
            Gson gson = new Gson();
            ResponseExchangeRates responseExchangeRates = gson.fromJson(response,ResponseExchangeRates.class);
            return new Rates(responseExchangeRates.getValute().getUSD().getValue(),responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert httpURLConnection!=null;
            httpURLConnection.disconnect();
        }
        return null;
    }

    public Rates getRates(ProgressBar progressBar) {
        try {
            URL url = new URL(GET_DATA);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            progressBar.setValue(20.0f);

            byte[] result = httpURLConnection.getInputStream().readAllBytes();
            String response = new String(result);
            progressBar.setValue(40.0f);

            Gson gson = new Gson();
            ResponseExchangeRates responseExchangeRates = gson.fromJson(response,ResponseExchangeRates.class);
            progressBar.setValue(80.0f);

            return new Rates(responseExchangeRates.getValute().getUSD().getValue(),responseExchangeRates.getValute().getEUR().getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert httpURLConnection!=null;
            httpURLConnection.disconnect();
        }
        return null;
    }

}
