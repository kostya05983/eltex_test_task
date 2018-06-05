import org.jsoup.helper.HttpConnection;
import sun.security.util.IOUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTemperature {
    private final String KEY = "51813ee8-0cc6-4c81-8252-d6a34a242639";
    private final String GET_DATA = "GET https://api.weather.yandex.ru/v1/informers?limit=2&";
    HttpURLConnection httpURLConnection;


    public HttpTemperature(){

    }

    public Temperature getTemperature(String location){
        Coordinate coordinate = getCoordinates(location);
        try {
            httpURLConnection = (HttpURLConnection)new URL(GET_DATA+"lat="+coordinate.getX()
            +"&lon="+coordinate.getY()).openConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpURLConnection.disconnect();
        }

        return null;
    }

    private Coordinate getCoordinates(String request) {
        HttpsURLConnection httpsURLConnection=null;
        try {
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + request + "&key=AIzaSyCb_BYsT7FKxYtUSHWcB_lZE-aAYrX5wfk");
             httpsURLConnection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            byte[] result = new byte[inputStream.available()];
            inputStream.read(result);

            String response = new String(result);
            response = response.substring(response.indexOf("location"));
            response = response.substring(response.indexOf("{") + 1, response.indexOf("}"));
            String x = response.substring(response.indexOf(":") + 1, response.indexOf(",")).trim();
            response = response.substring(response.indexOf(",") + 1);
            String y = response.substring(response.indexOf(":") + 1).replaceAll("\n", "").trim();
            return new Coordinate(Double.parseDouble(x), Double.parseDouble(y));

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            assert httpsURLConnection != null;
            httpsURLConnection.disconnect();
        }
        return null;
    }

}
