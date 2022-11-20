package com.starter.mongodb.web;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class MongoRestController {
    
    private static final String COLLECTION = "person";

    private final MongoDatabase mongoDatabase;

    public MongoRestController(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @GetMapping("/insert")
    public void insertOne() {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        Document document = new Document();

        document.append("name", "Tom");
        document.append("age", "25");

        InsertOneResult result = collection.insertOne(document);
        System.out.println("==> InsertOneResult : " + result.getInsertedId());
    }


    @GetMapping("/insert-many")
    public void insertMany() {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        List<Document> insertList = new ArrayList<>();

        Document document1 = new Document();
        Document document2 = new Document();

        document1.append("name", "Marin");
        document1.append("age", "56");

        document2.append("name", "Jenny");
        document2.append("age", "35");

        insertList.add(document1);
        insertList.add(document2);

        InsertManyResult result = collection.insertMany(insertList);
        System.out.println("==> InsertManyResult : " + result.getInsertedIds());
    }

    @GetMapping("/find")
    public void find() {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        FindIterable<Document> doc = collection.find();

        Iterator itr = doc.iterator();

        while (itr.hasNext()) {
            System.out.println("==> findResultRow : "+itr.next());
        }
    }

    @GetMapping("/find-id")
    public void findById(@RequestParam String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        System.out.println("==> findByIdResult : " + doc);
    }

    @GetMapping("/update")
    public void updateOne(@RequestParam String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        Bson query = eq("_id", new ObjectId(id));

        Bson updates = Updates.combine(
                Updates.set("name", "modify name"),
                Updates.currentTimestamp("lastUpdated"));

        UpdateResult result = collection.updateOne(query, updates);
        System.out.println("==> UpdateResult : " + result.getModifiedCount());
    }

    @GetMapping("/update-many")
    public void updateMany(@RequestParam String age) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        Bson query = gt("age", age);

        Bson updates = Updates.combine(
                Updates.set("name", "modify name"),
                Updates.currentTimestamp("lastUpdated"));

        UpdateResult result = collection.updateMany(query, updates);
        System.out.println("==> UpdateManyResult : " + result.getModifiedCount());
    }

    @GetMapping("/delete")
    public void deleteOne(@RequestParam String id) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        Bson query = eq("_id", new ObjectId(id));

        DeleteResult result = collection.deleteOne(query);
        System.out.println("==> DeleteResult : " + result.getDeletedCount());
    }

    @GetMapping("/delete-many")
    public void deleteMany(@RequestParam String age) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(COLLECTION);

        Bson query = gt("age", age);

        DeleteResult result = collection.deleteMany(query);
        System.out.println("==> DeleteManyResult : " + result.getDeletedCount());
    }
}
