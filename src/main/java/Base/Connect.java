package Base;


import com.mongodb.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;


public class Connect {
    private final static  Logger logger = LogManager.getLogger(Connect.class);
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
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName()+":"+e.getMessage());
            throw new RuntimeException("Failed to read file " + CONFIGURATION_FILE, e);
        }

        NAME_DATA_BASE = properties.getProperty("NAME_DATA_BASE");
        COLLECTION_NAME = properties.getProperty("COLLECTION_NAME");
        FIELD_KEY = properties.getProperty("FIELD_KEY");
    }

    //method for getting Visits
    public synchronized int getVisists() {
        MongoClient mongoClient = null;
        //connect MongoClient
        try {
            mongoClient = new MongoClient();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : create mongoClient");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        }
        assert mongoClient != null;
        database = mongoClient.getDB(NAME_DATA_BASE);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : getDB name=" + NAME_DATA_BASE);

        DBCollection collection = database.getCollection(COLLECTION_NAME);
        logger.debug("get collection collectionName=" + COLLECTION_NAME);

        //get an object from db
        DBObject query = new BasicDBObject("_id", FIELD_KEY);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : query with _id and" + FIELD_KEY);
        DBCursor cursor = collection.find(query);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : cursor was gotten");
        DBObject dbObject = cursor.one();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : object was gotten");

        return (int) dbObject.get(FIELD_KEY);
    }

    //method for adding one visit to DB
    public synchronized void writeOneVisit() {
        MongoClient mongoClient = null;
        //connect MongoClient
        try {
            mongoClient = new MongoClient();
            logger.debug(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : create MongoClient");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
        }
        assert mongoClient != null;
        database = mongoClient.getDB(NAME_DATA_BASE);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : get DB dbName" + NAME_DATA_BASE);

        DBCollection collection = database.getCollection(COLLECTION_NAME);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : get collection collectionName" + COLLECTION_NAME);

        //get visits from DB
        DBObject query = new BasicDBObject("_id", FIELD_KEY);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : create query");
        DBCursor cursor = collection.find(query);

        DBObject dbObject = cursor.one();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : object was gotten");

        //updating visits in DB
        DBObject newObject = new BasicDBObject("_id", FIELD_KEY);
        ((BasicDBObject) newObject).append(FIELD_KEY, (int) dbObject.get(FIELD_KEY) + 1);
        collection.update(dbObject, newObject);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : object was updated");
    }

}
