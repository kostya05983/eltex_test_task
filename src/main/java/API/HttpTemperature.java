package API;

import API.YandexWeather.ResponseWeather;
import Vaadin.VaadinUI;
import com.google.gson.Gson;
import com.vaadin.ui.ProgressBar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

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
    private final static Logger logger = LogManager.getRootLogger();
    private HttpURLConnection httpURLConnection;
    private VaadinUI context;


    // инициализация констант
    static {
        Properties properties = new Properties();

        try (InputStream inputStream = ClassLoader.class.getResourceAsStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(MarkerManager.getMarker("SERVER"),e.getMessage());
            throw new RuntimeException("Failed to read file " + CONFIGURATION_FILE, e);
        }

        YANDEX_KEY = properties.getProperty("YANDEX_KEY");
        GOOGLE_KEY = properties.getProperty("GOOGLE_KEY");
        REQUEST_GEOCORDINATING = properties.getProperty("REQUEST_GEOCORDINATING");
        YANDEX_WEATHER_REQUEST = properties.getProperty("YANDEX_WEATHER_REQUEST");

        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getSimpleName() + " : константы проинициализированны \nYANDEX_KEY=" + YANDEX_KEY + "\nGOOGLE_KEY=" + GOOGLE_KEY +
                "\nREQUEST_GEOCORDINATING" + REQUEST_GEOCORDINATING + "\nYANDEX_WEATHER_REQUEST" + YANDEX_WEATHER_REQUEST);
    }

    public HttpTemperature(VaadinUI context) {
        this.context = context;
    }

    /**
     * Служит для получения температуры по названию города
     *
     * @param location - название города
     * @return - возвращает Объект температуры
     */
    public Temperature getTemperature(String location) {
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : получение координат для " + location);
        //получаем координаты из строки
        Coordinate coordinate = getCoordinates(location);
        if (coordinate == null) {
            return null;
        } else {
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : координаты получены " + coordinate);
        }

        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : получение температуры для " + location);
        Temperature temperature = null;
        try {
            //получаем температуру
            URL url = new URL(YANDEX_WEATHER_REQUEST + "lat=" + coordinate.getX()
                    + "&lon=" + coordinate.getY());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("X-Yandex-API-Key", YANDEX_KEY);

            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] result = new CustomInputStream(inputStream).readAllBytes();
            String response = new String(result);

            //парсим
            Gson gson = new Gson();
            ResponseWeather responseWeather = gson.fromJson(response, ResponseWeather.class);
            if (responseWeather == null || ((responseWeather.getFact() == null || responseWeather.getFact().getTemp() == null) ||
                    (responseWeather.getForecasts() == null || responseWeather.getForecasts().get(1) == null ||
                            responseWeather.getForecasts().get(1).getParts() == null || responseWeather.getForecasts().get(1).getParts().getDay() == null ||
                            responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg() == null))) {
                if(context!=null)
                context.showNotification("Был изменен API,погода неверная, обратитесь к администратору");
                logger.error(MarkerManager.getMarker("SERVER"), new Object() {
                }.getClass().getEnclosingMethod().getName() + " : изменен API");
                return new Temperature("999", "999");
            }

            temperature = new Temperature("" + responseWeather.getFact().getTemp(),
                    "" + responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg());

            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : температура получена " + temperature);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"),e.getMessage());
        } finally {
            if (temperature != null) {
                httpURLConnection.disconnect();
                logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
                }.getClass().getEnclosingMethod().getName() + " : disconnect");
            }
        }

        return temperature;
    }

    //method for get Temperature

    /**
     * Метод для получения температуры по названию города с использованием прогресс бара
     *
     * @param location    - название города
     * @param progressBar - прогресс бар для инкрементации значения
     * @return - возвращает объект температуры
     */
    public Temperature getTemperature(String location, ProgressBar progressBar) {
        //получаем координаты из строки
        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : получение координат для " + location);
        Coordinate coordinate = getCoordinates(location);
        if (coordinate == null) {
            return null;
        } else {
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : координаты получены " + coordinate);
        }

        logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : получение температуры для " + location);
        Temperature temperature = null;
        try {
            //получаем температуру
            URL url = new URL(YANDEX_WEATHER_REQUEST + "lat=" + coordinate.getX()
                    + "&lon=" + coordinate.getY());
            progressBar.setValue(10.0f);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("X-Yandex-API-Key", YANDEX_KEY);
            progressBar.setValue(30.0f);

            InputStream inputStream = httpURLConnection.getInputStream();
            byte[] result = new CustomInputStream(inputStream).readAllBytes();
            progressBar.setValue(40.0f);
            String response = new String(result);

            Gson gson = new Gson();
            ResponseWeather responseWeather = gson.fromJson(response, ResponseWeather.class);
            progressBar.setValue(60.0f);

            if (responseWeather == null || ((responseWeather.getFact() == null || responseWeather.getFact().getTemp() == null) ||
                    (responseWeather.getForecasts() == null || responseWeather.getForecasts().get(1) == null ||
                            responseWeather.getForecasts().get(1).getParts() == null || responseWeather.getForecasts().get(1).getParts().getDay() == null ||
                            responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg() == null))) {
                if(context!=null)
                context.showNotification("Был изменен API,погода неверная, обратитесь к администратору");
                logger.error(MarkerManager.getMarker("SERVER"), new Object() {
                }.getClass().getEnclosingMethod().getName() + " : изменен API");
                return new Temperature("999", "999");
            }

            temperature = new Temperature("" + responseWeather.getFact().getTemp(),
                    "" + responseWeather.getForecasts().get(1).getParts().getDay().getTemp_avg());
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : температура получена " + temperature);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"), e.getMessage());
        } finally {
            httpURLConnection.disconnect();
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }

        return temperature;
    }


    /**
     * @param request - название локации
     * @return - возвращает объект координат города
     */
    private Coordinate getCoordinates(String request) {
        HttpsURLConnection httpsURLConnection = null;
        try {
            URL url = new URL(REQUEST_GEOCORDINATING + request + "&key=" + GOOGLE_KEY);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();

            InputStream inputStream = httpsURLConnection.getInputStream();
            byte[] result = new CustomInputStream(inputStream).readAllBytes();

            String response = new String(result);

            response = response.substring(response.indexOf("location"));
            response = response.substring(response.indexOf("{") + 1, response.indexOf("}"));
            String x = response.substring(response.indexOf(":") + 1, response.indexOf(",")).trim();
            response = response.substring(response.indexOf(",") + 1);
            String y = response.substring(response.indexOf(":") + 1).replaceAll("\n", "").trim();

            return new Coordinate(Double.parseDouble(x), Double.parseDouble(y));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"),  e.getMessage());
        } finally {
            assert httpsURLConnection != null;
            httpsURLConnection.disconnect();
            logger.debug(MarkerManager.getMarker("SERVER"), new Object() {
            }.getClass().getEnclosingMethod().getName() + " : disconnect");
        }
        return null;
    }

}
