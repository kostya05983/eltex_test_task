package API;

import Vaadin.MainHorizontalLayout;
import YandexWeather.ResponseWeather;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTemperature {
    private final String KEY = "51813ee8-0cc6-4c81-8252-d6a34a242639";
    private final String GET_DATA = "https://api.weather.yandex.ru/v1/forecast?limit=2" + "&extra=true&";
    private final Logger logger = LogManager.getLogger(HttpTemperature.class);
    HttpURLConnection httpURLConnection;


    public Temperature getTemperature(String location) {
        Coordinate coordinate = getCoordinates(location);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : coordinate was gotten");

        Temperature temperature = null;
        try {
            URL url = new URL(GET_DATA + "lat=" + coordinate.getX()
                    + "&lon=" + coordinate.getY());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : openConnection");
            httpURLConnection.addRequestProperty("X-Yandex-API-Key", KEY);

            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] result = inputStream.readAllBytes();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : getResponse");


            String response = new String(result);
            Gson gson = new Gson();
            ResponseWeather responseWeather = gson.fromJson(response, ResponseWeather.class);
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+"response was parsed");

            temperature = new Temperature("" + responseWeather.getFact().getTemp(),
                    "" + responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+e.getMessage());
        } finally {
            httpURLConnection.disconnect();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : disconnect");
        }

        return temperature;
    }

    public Temperature getTemperature(String location, ProgressBar progressBar) {
        Coordinate coordinate = getCoordinates(location);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : coordinate was gotten");
        Temperature temperature = null;
        try {
            URL url = new URL(GET_DATA + "lat=" + coordinate.getX()
                    + "&lon=" + coordinate.getY());
            progressBar.setValue(10.0f);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : openConnection");
            httpURLConnection.addRequestProperty("X-Yandex-API-Key", KEY);
            progressBar.setValue(30.0f);

            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] result = inputStream.readAllBytes();
            progressBar.setValue(40.0f);
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : getResponse");

            String response = new String(result);
            Gson gson = new Gson();
            ResponseWeather responseWeather = gson.fromJson(response, ResponseWeather.class);
            progressBar.setValue(60.0f);
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+"response was parsed");

            temperature = new Temperature("" + responseWeather.getFact().getTemp(),
                    "" + responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+e.getMessage());
        } finally {
            httpURLConnection.disconnect();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : disconnect");
        }

        return temperature;
    }

    private Coordinate getCoordinates(String request) {
        HttpsURLConnection httpsURLConnection = null;
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + request + "&key=AIzaSyCb_BYsT7FKxYtUSHWcB_lZE-aAYrX5wfk");
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : openConnection");

            InputStream inputStream = httpsURLConnection.getInputStream();
            byte[] result = inputStream.readAllBytes();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : getResponse");

            String response = new String(result);
            response = response.substring(response.indexOf("location"));
            response = response.substring(response.indexOf("{") + 1, response.indexOf("}"));
            String x = response.substring(response.indexOf(":") + 1, response.indexOf(",")).trim();
            response = response.substring(response.indexOf(",") + 1);
            String y = response.substring(response.indexOf(":") + 1).replaceAll("\n", "").trim();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : response was parsed");
            return new Coordinate(Double.parseDouble(x), Double.parseDouble(y));

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+e.getMessage());
        } finally {
            assert httpsURLConnection != null;
            httpsURLConnection.disconnect();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : disconnect");
        }
        return null;
    }


}
