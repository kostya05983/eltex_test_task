package Server;



import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


import java.io.InputStream;
import java.util.Properties;

public class Main {

    private final static Logger log = LogManager.getLogger(Main.class);
    private final static String CONFIGURATION_FILE = "/server.properties";//имя файла с константами
    private static final int PORT;
    private final static String LOCATION;
    private final static String APP_BASE;

    //инициализация констант
    static {
        Properties properties = new Properties();

        try (InputStream inputStream = ClassLoader.class.getResourceAsStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(new Object() {
            }.getClass().getEnclosingMethod().getName()+":"+e.getMessage());
            throw new RuntimeException("Failed to read file " + CONFIGURATION_FILE, e);
        }

        PORT = Integer.parseInt(properties.getProperty("PORT"));
        LOCATION = properties.getProperty("LOCATION");
        APP_BASE = properties.getProperty("APP_BASE");
    }

    //запускаем Tomcat
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":created Tomcat");

        //конфигурируем
        tomcat.setBaseDir(LOCATION);
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(APP_BASE);
        tomcat.addWebapp("", APP_BASE);
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":location was established");

        tomcat.start();
        tomcat.getServer().await();
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":server was started");
    }


}
