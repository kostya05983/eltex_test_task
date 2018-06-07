package Base;


import com.mongodb.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;


public class Connect {
    private final Logger logger = LogManager.getLogger(Connect.class);
    private final String NAME_DATA_BASE = "VisitsDB";
    private final String COLLECTION_NAME = "visits";
    private final String KEY = "visit";
    private DB database;

    //method for getting Visits
    public int getVisists() {
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
        DBObject query = new BasicDBObject("_id", KEY);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : query with _id and" + KEY);
        DBCursor cursor = collection.find(query);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : cursor was gotten");
        DBObject dbObject = cursor.one();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : object was gotten");

        return (int) dbObject.get(KEY);
    }

    //method for adding one visit to DB
    public void writeOneVisit() {
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
        DBObject query = new BasicDBObject("_id", KEY);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : create query");
        DBCursor cursor = collection.find(query);

        DBObject dbObject = cursor.one();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : object was gotten");

        //updating visits in DB
        DBObject newObject = new BasicDBObject("_id", KEY);
        ((BasicDBObject) newObject).append(KEY, (int) dbObject.get(KEY) + 1);
        collection.update(dbObject, newObject);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : object was updated");
    }

}
