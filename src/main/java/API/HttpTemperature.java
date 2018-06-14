package API;

import API.YandexWeather.ResponseWeather;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class HttpTemperature {
    private final static String CONFIGURATION_FILE = "/http_temperature.properties";//имя файла с константами
    private final static String YANDEX_KEY;//key for API Yandex-WEATHER
    private final static String GOOGLE_KEY;
    private final static String REQUEST_GEOCORDINATING;
    private final static String YANDEX_WEATHER_REQUEST;//request for yandex
    private final static Logger logger = LogManager.getLogger(HttpTemperature.class);
    HttpURLConnection httpURLConnection;


    //инициализация констант
    static {
        Properties properties = new Properties();

        try (InputStream inputStream = ClassLoader.class.getResourceAsStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName()+":"+e.getMessage());
            throw new RuntimeException("Failed to read file " + CONFIGURATION_FILE, e);
        }

        YANDEX_KEY = properties.getProperty("YANDEX_KEY");
        GOOGLE_KEY = properties.getProperty("GOOGLE_KEY");
        REQUEST_GEOCORDINATING = properties.getProperty("REQUEST_GEOCORDINATING");
        YANDEX_WEATHER_REQUEST = properties.getProperty("YANDEX_WEATHER_REQUEST");


    }

    //method for get Temperature for test
    public Temperature getTemperature(String location) {
        //get coordinates from String
        Coordinate coordinate = getCoordinates(location);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : coordinate was gotten");

        Temperature temperature = null;
        try {
            //open connection
            URL url = new URL(YANDEX_WEATHER_REQUEST + "lat=" + coordinate.getX()
                    + "&lon=" + coordinate.getY());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");
            httpURLConnection.addRequestProperty("X-Yandex-API-Key", YANDEX_KEY);

            //get Data
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] result = new CustomInputStream(inputStream).readAllBytes();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : getResponse");

            //parse Data
            String response = new String(result);
            Gson gson = new Gson();
            ResponseWeather responseWeather = gson.fromJson(response, ResponseWeather.class);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + "response was parsed");

            temperature = new Temperature("" + responseWeather.getFact().getTemp(),
                    "" + responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        } finally {
            httpURLConnection.disconnect();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }

        return temperature;
    }

    //method for get Temperature
    public Temperature getTemperature(String location, ProgressBar progressBar) {
        //get coordinates from String
        Coordinate coordinate = getCoordinates(location);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : coordinate was gotten");
        Temperature temperature = null;
        try {
            //open connection
            assert coordinate != null;
            URL url = new URL(YANDEX_WEATHER_REQUEST + "lat=" + coordinate.getX()
                    + "&lon=" + coordinate.getY());
            progressBar.setValue(10.0f);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");
            httpURLConnection.addRequestProperty("X-Yandex-API-Key", YANDEX_KEY);
            progressBar.setValue(30.0f);

            //get Data
            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] result = new CustomInputStream(inputStream).readAllBytes();
            progressBar.setValue(40.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : getResponse");

            //parse Data
            String response = new String(result);
            Gson gson = new Gson();
            ResponseWeather responseWeather = gson.fromJson(response, ResponseWeather.class);
            progressBar.setValue(60.0f);
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + "response was parsed");

            temperature = new Temperature("" + responseWeather.getFact().getTemp(),
                    "" + responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        } finally {
            httpURLConnection.disconnect();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }

        return temperature;
    }

    //method for get coordinate from String
    private Coordinate getCoordinates(String request) {
        HttpsURLConnection httpsURLConnection = null;
        try {
            //open Connection
            URL url = new URL(REQUEST_GEOCORDINATING + request + "&key=" + GOOGLE_KEY);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : openConnection");

            //getData
            InputStream inputStream = httpsURLConnection.getInputStream();
            byte[] result = new CustomInputStream(inputStream).readAllBytes();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : getResponse");

            //parse Data
            String response = new String(result);
            response = response.substring(response.indexOf("location"));
            response = response.substring(response.indexOf("{") + 1, response.indexOf("}"));
            String x = response.substring(response.indexOf(":") + 1, response.indexOf(",")).trim();
            response = response.substring(response.indexOf(",") + 1);
            String y = response.substring(response.indexOf(":") + 1).replaceAll("\n", "").trim();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : response was parsed");

            return new Coordinate(Double.parseDouble(x), Double.parseDouble(y));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        } finally {
            assert httpsURLConnection != null;
            httpsURLConnection.disconnect();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }
        return null;
    }

}
