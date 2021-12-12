package com.astter.in;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseHandler {
    private static Context mContext;
    private MongoClient mongoClient;
    private static DatabaseHandler databaseHandler;

    private DatabaseHandler(Context mContext){
        DatabaseHandler.mContext = mContext;
        mongoClient = getMongoClient();
    }

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        return databaseHandler;
    }


    @SuppressLint("AuthLeak")
    public MongoClient getMongoClient(){
        if(mongoClient == null){
            mongoClient = new MongoClient("mongodb://127.0.0.1:27017" , 27017);
        }
        return mongoClient;
    }

    public void createUser(String name, String address){
        MongoDatabase database = getMongoClient().getDatabase("userDB");
        MongoCollection<Document> collection = database.getCollection("users");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = new Document("username", name).append("address", address).append("items", null);
                collection.insertOne(doc);
            }
        });
        thread.start();

        Toast.makeText(mContext, "User created successfully", Toast.LENGTH_SHORT).show();
    }

    public List<SocialItem> getItems(){
        List<SocialItem> items = new ArrayList<>();

        MongoDatabase database = getMongoClient().getDatabase("userDB");
        MongoCollection<Document> collection = database.getCollection("users");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FindIterable<Document> iterDoc = collection.find();
                int i = 1;
                // Getting the iterator
                for (Document document : iterDoc) {
                    //items = document.get("items");
                    i++;
                }
            }
        });
        thread.start();

        Toast.makeText(mContext, "User created successfully", Toast.LENGTH_SHORT).show();

        return items;
    }
}
