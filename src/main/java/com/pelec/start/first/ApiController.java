package com.pelec.start.first;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

@RestController
public class ApiController {
    private final DbInitializer client = FirstApplication.client;

    @PostMapping("/list_documents")
    public ResponseEntity<String> listDocuments(@RequestParam("collection") String collection) throws IOException {
        MongoCollection<Document> col = client.getDatabase("local").getCollection(collection);
        if(col.countDocuments() == 0) {
            return new ResponseEntity<>("No documents", HttpStatus.OK);
        };
        MongoCursor<Document> c = col.find().iterator();
        StringBuilder s = new StringBuilder();
        while(c.hasNext()) {
            s.append(c.next().toJson() + "\n");
        }
        return new ResponseEntity<>(s.toString(), HttpStatus.OK);
    }

    @PostMapping("/insert_document")
    public ResponseEntity<String> insertDocument(@RequestParam("name") String name, @RequestParam("age") int age, @RequestParam("collection") String collection) throws IOException {
        MongoCollection<Document> col = client.getDatabase("local").getCollection(collection);
        ObjectId id = new ObjectId();
        Document document = new Document("_id", id);
        document.append("name", name).append("age", age);
        col.insertOne(document);
        return new ResponseEntity<>("Success, id: " + id, HttpStatus.OK);
    }

    @PostMapping("/update_document")
    public ResponseEntity<String> updateDocument(@RequestParam("id") String id, @RequestParam("data") String data, @RequestParam("collection") String collection) {
        try {
            BasicDBObject json = new BasicDBObject().parse(data);
            BasicDBObject object = new BasicDBObject("$set", json);
            BasicDBObject _id = new BasicDBObject("_id", new ObjectId(id));
            MongoCollection col = client.getDatabase("local").getCollection(collection);
            try {
                col.updateOne(_id, object);
            } catch (MongoException e) {
                return new ResponseEntity<>("Failed to update document", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Document updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update document", HttpStatus.BAD_REQUEST);
        }

    };
}
