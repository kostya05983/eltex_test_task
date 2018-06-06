package Base;


import com.mongodb.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;


public class Connect {
    private final Logger logger = LogManager.getLogger(Connect.class);
    private final String NAME_DATA_BASE="VisitsDB";
    private final String COLLECTION_NAME="visits";
    private DB database;

    public int getVisists() {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create mongoClient");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+e.getMessage());
        }
        database = mongoClient.getDB(NAME_DATA_BASE);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : getDB name="+NAME_DATA_BASE);

        DBCollection collection = database.getCollection(COLLECTION_NAME);
        logger.debug("get collection collectionName="+COLLECTION_NAME);

        DBObject query = new BasicDBObject("_id","visit");
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : queary with _id and visit");
        DBCursor cursor = collection.find(query);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : cursor was gotten");
        DBObject dbObject = cursor.one();
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : object was gotten");

        return (int) dbObject.get("visit");
    }

    public void writeOneVisit() {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
            logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create MongoClient");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+e.getMessage());
        }
        database = mongoClient.getDB(NAME_DATA_BASE);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : get DB dbName"+NAME_DATA_BASE);

        DBCollection collection = database.getCollection(COLLECTION_NAME);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : get collection collectionName"+COLLECTION_NAME);

        DBObject query = new BasicDBObject("_id","visit");
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create query");
        DBCursor cursor = collection.find(query);

        DBObject dbObject = cursor.one();
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : object was gotten");

        DBObject newObject = new BasicDBObject("_id","visit");
        ((BasicDBObject) newObject).append("visit",(int)dbObject.get("visit")+1);
        collection.update(dbObject,newObject);
        logger.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : object was updated");
    }

}
