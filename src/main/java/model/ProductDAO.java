package model;
import com.google.gson.Gson;

import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;



public class ProductDAO {
    public JSONObject getDataFromDB() {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");

        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();

        for (Document doc: collection.find()) {
            String stringId = doc.get("_id").toString();
            String jsonString = gson.toJson(doc);
            JSONObject obj = new Gson().fromJson(jsonString, JSONObject.class);
            obj.put("_id", stringId);
            jsonArray.add(obj);
        }

        JSONObject rootObject = new JSONObject();
        rootObject.put("Products", jsonArray);

        client.close();
        return rootObject;
    }

    public JSONObject getResultQuery(String productName, String selectedValue, String minPrice, String maxPrice,
                                     String minWeight, String maxWeight) {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");

        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        Bson filter = null;
        if (minPrice != null && maxPrice != null
                && maxWeight != null && minWeight != null
                && productName == null && selectedValue != null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice))),
                    and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight))),
                    eq("Category", selectedValue)));

        } else if (minPrice != null && maxPrice != null
                && maxWeight != null && minWeight != null
                && productName == null && selectedValue == null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice))),
                    and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight)))));

        } else if (minPrice != null && maxPrice != null
                && maxWeight != null && minWeight != null
                && productName != null && selectedValue != null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice))),
                    and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight))),
                    eq("Category", selectedValue), text(productName)));

        } else if (minPrice != null && maxPrice != null
                && maxWeight != null && minWeight != null
                && productName != null && selectedValue == null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice))),
                    and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight))),
                    text(productName)));

        } else if (minPrice != null && maxPrice != null
                && productName == null && selectedValue != null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice))),
                    eq("Category", selectedValue)));

        } else if (minPrice != null && maxPrice != null
                && productName == null && selectedValue == null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice)))));

        } else if (minPrice != null && maxPrice != null
                && productName != null && selectedValue != null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice))),
                    eq("Category", selectedValue), text(productName)));

        } else if (minPrice != null && maxPrice != null
                && productName != null && selectedValue == null) {

            filter = and(Arrays.asList(and(gt("Selling Price", Float.parseFloat(minPrice)), lt("Selling Price", Float.parseFloat(maxPrice))),
                    text(productName)));

        } else if (maxWeight != null && minWeight != null
                && productName == null && selectedValue != null) {

            filter = and(Arrays.asList(and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight))),
                    eq("Category", selectedValue)));

        } else if (maxWeight != null && minWeight != null
                && productName == null && selectedValue == null) {

            filter = and(Arrays.asList(and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight)))));

        } else if (maxWeight != null && minWeight != null
                && productName != null && selectedValue != null) {

            filter = and(Arrays.asList(and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight))),
                    eq("Category", selectedValue), text(productName)));

        } else if (maxWeight != null && minWeight != null
                && productName != null && selectedValue == null) {

            filter = and(Arrays.asList(and(gt("Shipping Weight", Float.parseFloat(minWeight)), lt("Shipping Weight", Float.parseFloat(maxWeight))),
                    text(productName)));

        } else if (selectedValue == null && maxWeight == null
                && maxPrice == null && productName != null) {

            filter = text(productName);

        } else if (selectedValue != null && productName != null
                && maxWeight == null && maxPrice == null) {

            filter = and(eq("Category", selectedValue), text(productName));

        } else if (selectedValue != null && productName == null
                && maxWeight == null && maxPrice == null) {

            filter = eq("Category", selectedValue);

        }

        if (filter == null) {
            return getDataFromDB();
        }

        for (Document doc: collection.find(filter)) {
            String stringId = doc.get("_id").toString();
            String jsonString = gson.toJson(doc);
            JSONObject obj = new Gson().fromJson(jsonString, JSONObject.class);
            obj.put("_id", stringId);
            jsonArray.add(obj);
        }

        JSONObject rootObject = new JSONObject();
        rootObject.put("Products", jsonArray);
        client.close();
        return rootObject;
    }

    public JSONObject insertData(String pName, String category, double price, double weight, String aboutProd, String techDet,
                                 String prodUrl, String imageUrl) {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");

        Document document = new Document("Product Name", pName)
                .append("Category", category)
                .append("Selling Price", price)
                .append("Shipping Weight", weight)
                .append("Product Url", prodUrl)
                .append("About Product", aboutProd)
                .append("Technical Details", techDet);

        if (!imageUrl.equals("")) {
            document.append("Image", imageUrl);
        }

        collection.insertOne(document);

        client.close();
        return getDataFromDB();
    }

    public JSONObject getProductByID(String idProduct) {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");

        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();

        ObjectId objectId = new ObjectId(idProduct);

        Bson filter = eq("_id", objectId);

        for (Document doc: collection.find(filter)) {
            String jsonString = gson.toJson(doc);
            JSONObject obj = new Gson().fromJson(jsonString, JSONObject.class);
            obj.put("_id", idProduct);
            jsonArray.add(obj);
        }

        JSONObject rootObject = new JSONObject();
        rootObject.put("Product", jsonArray);

        client.close();
        return rootObject;
    }

    public JSONObject deleteProduct(String idProduct) {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");

        ObjectId objectId = new ObjectId(idProduct);
        Bson filter = eq("_id", objectId);
        collection.deleteOne(filter);

        client.close();
        return getDataFromDB();
    }

    public JSONObject editData (String idProduct, String pName, String category, double price, double weight, String aboutProd, String techDet,
                                 String prodUrl, String imageUrl) {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");
        ObjectId objectId = new ObjectId(idProduct);
        Bson filter = eq("_id", objectId);

        collection.updateOne(filter, Updates.combine(Updates.set("Product Name", pName), Updates.set("Category", category),
                Updates.set("Selling Price", price), Updates.set("Shipping Weight", weight), Updates.set("Product Url", prodUrl),
                Updates.set("About Product", aboutProd), Updates.set("Technical Details", techDet), Updates.set("Image", imageUrl)));

        client.close();
        return getDataFromDB();
    }


    public JSONObject avgWeightAggregate() {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");

        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$group",
                        new Document("_id", "$Category").append("Average",
                                new Document("$avg", "$Shipping Weight")))));

        for (Document doc: result) {
            String jsonString = gson.toJson(doc);
            JSONObject obj = new Gson().fromJson(jsonString, JSONObject.class);
            jsonArray.add(obj);
        }

        JSONObject rootObject = new JSONObject();
        rootObject.put("Aggregates", jsonArray);
        client.close();
        return rootObject;
    }

    public JSONObject avgPriceAggregate() {
        MongoClient client = ConPool.getConnection();
        MongoDatabase database = client.getDatabase("AmazonProducts");
        MongoCollection<Document> collection = database.getCollection("AmazonProducts");

        JSONArray jsonArray = new JSONArray();
        Gson gson = new Gson();
        AggregateIterable<Document>  result = collection.aggregate(Arrays.asList(new Document("$group",
                new Document("_id", "$Category").append("Average",
                        new Document("$avg", "$Selling Price")))));
        for (Document doc: result) {
            String jsonString = gson.toJson(doc);
            JSONObject obj = new Gson().fromJson(jsonString, JSONObject.class);
            jsonArray.add(obj);
        }

        JSONObject rootObject = new JSONObject();
        rootObject.put("Aggregates", jsonArray);

        client.close();
        return rootObject;
    }

}
