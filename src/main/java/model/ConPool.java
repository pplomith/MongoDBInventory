package model;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;


public class ConPool {
    public static MongoClient getConnection() {
        ConnectionString connectionString = new ConnectionString("");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }


}
