package Base;


import com.mongodb.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;


public class Connect {
    private final static Logger logger = LogManager.getRootLogger();
    private final static String CONFIGURATION_FILE = "/data_base.properties";//имя файла с константами для базы данных
    private final static String NAME_DATA_BASE; //имя базы данных
    private final static String COLLECTION_NAME; //имя коллекции в этой базе
    private final static String FIELD_KEY; //имя столбца
    private DB database;

    //инициализация констант
    static {
        Properties properties = new Properties();

        try (InputStream inputStream = ClassLoader.class.getResourceAsStream(CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"), e.getMessage());
            throw new RuntimeException("Failed to read file " + CONFIGURATION_FILE, e);
        }

        NAME_DATA_BASE = properties.getProperty("NAME_DATA_BASE");
        COLLECTION_NAME = properties.getProperty("COLLECTION_NAME");
        FIELD_KEY = properties.getProperty("FIELD_KEY");

        logger.debug(MarkerManager.getMarker("SERVER"), "Константы проинициализированны\nNAME_DATA_BASE=" + NAME_DATA_BASE + "\nCOLLECTION_NAME=" + COLLECTION_NAME + "\nFIELD_KEY=" + FIELD_KEY);
    }

    /**
     * метод для получения количества посетителей
     *
     * @return - возвращает количество посетителей
     */
    public synchronized int getVisits() {
        MongoClient mongoClient = null;
        //соединение MongoClient
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"), e.getMessage());
        }
        assert mongoClient != null;
        database = mongoClient.getDB(NAME_DATA_BASE);

        DBCollection collection = database.getCollection(COLLECTION_NAME);

        //Получаем объект из бд
        DBObject query = new BasicDBObject("_id", FIELD_KEY);
        DBCursor cursor = collection.find(query);
        DBObject dbObject = cursor.one();

        return (int) dbObject.get(FIELD_KEY);
    }


    /**
     * Метод для добавления одного посетителя в бд
     */
    public synchronized void writeOneVisit() {
        MongoClient mongoClient = null;
        //соединение с MongoClient
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(MarkerManager.getMarker("SERVER"), e.getMessage());
        }
        assert mongoClient != null;
        database = mongoClient.getDB(NAME_DATA_BASE);

        DBCollection collection = database.getCollection(COLLECTION_NAME);

        //получаем количество посещений
        DBObject query = new BasicDBObject("_id", FIELD_KEY);
        DBCursor cursor = collection.find(query);
        DBObject dbObject = cursor.one();

        ///обновляем количество посещений
        DBObject newObject = new BasicDBObject("_id", FIELD_KEY);
        ((BasicDBObject) newObject).append(FIELD_KEY, (int) dbObject.get(FIELD_KEY) + 1);
        collection.update(dbObject, newObject);
    }

}
