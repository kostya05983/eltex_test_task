package Server;



import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import java.io.*;



import java.net.ServerSocket;
import java.util.Properties;

public class Main {

    private final static Logger log ;
    //private final static String CONFIGURATION_FILE = "/server.properties";//имя файла с константами
    private final static String CONFIGURATION_FILE = "/home/kostya05983/lol/testtask/src/main/resources/server.properties";//имя файла с константами
    private static final int PORT;
    private final static String LOCATION;
    private final static String APP_BASE;

    //инициализация констант
    static {
        //Конфигурируем log4j2
        Properties systemProperties = System.getProperties();
        systemProperties.put("log4j.configurationFile","/log4j2.xml");
        System.setProperties(systemProperties);
        log = LogManager.getRootLogger();

        Properties properties = new Properties();

        //try (InputStream inputStream = ClassLoader.class.getResourceAsStream(CONFIGURATION_FILE)) {
        try (FileInputStream inputStream = new FileInputStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error(new Object() {
            }.getClass().getSimpleName()+":"+e.getMessage());
            throw new RuntimeException("Не удалось прочитать файл " + CONFIGURATION_FILE, e);
        }

        PORT = Integer.parseInt(properties.getProperty("PORT"));

        //проверка порта на занятость
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            serverSocket.close();
        } catch (IOException e) {
            log.error("Порт занят");
            System.exit(0);
        }

        LOCATION = properties.getProperty("LOCATION");
        APP_BASE = properties.getProperty("APP_BASE");
        log.debug(MarkerManager.getMarker("SERVER"),new Object() {
        }.getClass().getSimpleName()+":LOCATION="+LOCATION+"APP_BASE="+APP_BASE+"PORT="+PORT);
    }

    //запускаем Tomcat
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        //конфигурируем
        tomcat.setBaseDir(LOCATION);
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(APP_BASE);
        tomcat.addWebapp("", APP_BASE);
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":Томкат сконфигурирован успешно");

        tomcat.start();
        tomcat.getServer().await();
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":Сервер запущен");
    }
}
