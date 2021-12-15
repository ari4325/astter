package com.astter.in;

import android.content.Context;
import android.content.SharedPreferences;

public class Account {
    static String user_id;
    static String username;
    static String address;
    static boolean isLoggedIn;
    static String img_path;
    static Context mContext;
    static SharedPreferences sharedPreferences;

    static void setContext(Context mContext){
        Account.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences("user_login", Context.MODE_PRIVATE);
        load();
    }

    static void load(){
        username = sharedPreferences.getString("username", "");
        address = sharedPreferences.getString("address", "");
        isLoggedIn = sharedPreferences.getBoolean("login", false);
        img_path = sharedPreferences.getString("img_path", "");
        user_id = sharedPreferences.getString("user_id", "");
    }

    static void createUser(String name, String address, String img_path, String user_id){
        Account.username = name;
        Account.address = address;
        Account.img_path = img_path;
        Account.isLoggedIn = true;
        Account.user_id = user_id;
        save();
    }

    static void save(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("login", isLoggedIn);
        editor.putString("username", Account.username);
        editor.putString("address", Account.address);
        editor.putString("img_path", Account.img_path);
        editor.putString("user_id", user_id);
        editor.apply();
    }
}
