package com.pelec.start.first;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DbInitializer {
    public static MongoClient client;

    public DbInitializer(String host, String port) {
        ConnectionString connString = new ConnectionString(
                "mongodb://" + host + ":" + port
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        this.client = com.mongodb.client.MongoClients.create(settings);

    }

    public MongoDatabase getDatabase(String name) {
        return this.client.getDatabase(name);
    }

    public static void main(String[] args) {

    }
}
