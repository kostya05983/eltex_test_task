import YandexWeather.ResponseWeather;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTemperature {
    private final String KEY = "51813ee8-0cc6-4c81-8252-d6a34a242639";
    private final String GET_DATA = "https://api.weather.yandex.ru/v1/forecast?limit=2" + "&extra=true&";
    HttpURLConnection httpURLConnection;


    public Temperature getTemperature(String location) {
        Coordinate coordinate = getCoordinates(location);
        Temperature temperature = null;
        try {
            URL url = new URL(GET_DATA + "lat=" + coordinate.getX()
                    + "&lon=" + coordinate.getY());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("X-Yandex-API-Key", KEY);

            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] result = inputStream.readAllBytes();


            String response = new String(result);
            Gson gson = new Gson();
            ResponseWeather responseWeather = gson.fromJson(response, ResponseWeather.class);

            temperature = new Temperature("" + responseWeather.getFact().getTemp(),
                    "" + responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return temperature;
    }

    private Coordinate getCoordinates(String request) {
        HttpsURLConnection httpsURLConnection = null;
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + request + "&key=AIzaSyCb_BYsT7FKxYtUSHWcB_lZE-aAYrX5wfk");
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            byte[] result = inputStream.readAllBytes();

            String response = new String(result);
            response = response.substring(response.indexOf("location"));
            response = response.substring(response.indexOf("{") + 1, response.indexOf("}"));
            String x = response.substring(response.indexOf(":") + 1, response.indexOf(",")).trim();
            response = response.substring(response.indexOf(",") + 1);
            String y = response.substring(response.indexOf(":") + 1).replaceAll("\n", "").trim();
            return new Coordinate(Double.parseDouble(x), Double.parseDouble(y));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert httpsURLConnection != null;
            httpsURLConnection.disconnect();
        }
        return null;
    }


}
