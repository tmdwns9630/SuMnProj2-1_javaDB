package com.starter.mongodb.config;
import com.mongodb.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    private static final String URI = "mongodb+srv://tmdwns9630:dorl1024@cluster0.oacv8q3.mongodb.net/?retryWrites=true&w=majority";
    private static final String DATABASE = "javaToDB";

    @Bean
    public MongoDatabase mongoCollection() {
        MongoClient mongoClient = MongoClients.create(URI);
        MongoDatabase database = mongoClient.getDatabase(DATABASE);
        return database;
    }
}
