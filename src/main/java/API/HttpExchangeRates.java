package API;

import javafx.scene.control.ProgressBar;

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
            System.out.println(response);
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
            byte[] result = httpURLConnection.getInputStream().readAllBytes();
            String response = new String(result);
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert httpURLConnection!=null;
            httpURLConnection.disconnect();

        }
        return null;
    }

}
