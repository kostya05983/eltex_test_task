package Base;

import com.mongodb.*;

import java.net.UnknownHostException;


public class Connect {
    private final String NAME_DATA_BASE="VisitsDB";
    private final String COLLECTION_NAME="visits";
    private DB database;

    public int getVisists() {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        database = mongoClient.getDB(NAME_DATA_BASE);
        DBCollection collection = database.getCollection(COLLECTION_NAME);

        DBObject query = new BasicDBObject("_id","visit");
        DBCursor cursor = collection.find(query);
        DBObject dbObject = cursor.one();

        return (int) dbObject.get("visit");
    }

    public void writeOneVisit() {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        database = mongoClient.getDB(NAME_DATA_BASE);
        DBCollection collection = database.getCollection(COLLECTION_NAME);

        DBObject query = new BasicDBObject("_id","visit");
        DBCursor cursor = collection.find(query);

        DBObject dbObject = cursor.one();

        DBObject newObject = new BasicDBObject("_id","visit");
        ((BasicDBObject) newObject).append("visit",(int)dbObject.get("visit")+1);

        collection.update(dbObject,newObject);
    }

    public void FirstRun() {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        database = mongoClient.getDB(NAME_DATA_BASE);
        //DBCollection dbCollection =
        DBCollection collection = database.getCollection(COLLECTION_NAME);

       DBObject dbObject = new BasicDBObject("_id","visit");
       ((BasicDBObject) dbObject).append("visit",0);
       collection.insert(dbObject);
       mongoClient.close();
    }

}
