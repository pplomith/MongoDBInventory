package model;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;


public class TitanicDAO {
    public void getDataFromDB() {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("TitanicDisaster");
        MongoCollection<Document> collection = database.getCollection("TitanicDisaster");
        // Performing a read operation on the collection.
        FindIterable<Document> fi = collection.find();
        final JsonWriterSettings settings = JsonWriterSettings.builder( ).outputMode( JsonMode.SHELL ).build( );

        try (MongoCursor<Document> cursor = fi.iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson(settings));
            }
        }

    }
}
